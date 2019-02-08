package ta.syifaul.akadoma;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import ta.syifaul.akadoma.util.AlarmReceiver;
import ta.syifaul.akadoma.util.Config;
import ta.syifaul.akadoma.util.RangeTimePickerDialog;
import ta.syifaul.akadoma.util.Request;
import ta.syifaul.akadoma.util.SessionManager;


public class EditAgendaActivity extends AppCompatActivity {

    EditText edtJudul, edtKeterangan;
    Button btnTgl, btnWaktu, btnSimpan;

    private ProgressDialog pDialog;

    SessionManager session;
    private static String url = Config.HOST+"edit_agenda.php";

    private Calendar kalender;
    private int hari, bulan, tahun;
    private int jam, menit;
    static final int DATE_DIALOG = 1;
    static final int TIME_DIALOG = 2;
    private String tanggal, waktu;

    private PendingIntent pendingIntent;
    private static final int ALARM_REQUEST_CODE = 133;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_agenda);

        session = new SessionManager(EditAgendaActivity.this);

        getSupportActionBar().setTitle("Edit Agenda");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final String id_agenda = getIntent().getStringExtra("key_id_agenda");
        String judul = getIntent().getStringExtra("key_judul");
        tanggal = getIntent().getStringExtra("key_tgl");
        waktu = getIntent().getStringExtra("key_wkt");
        String keterangan = getIntent().getStringExtra("key_ket");

        //ambil data user
        HashMap<String, String> user = session.getUserDetails();
        final String id_user = user.get(SessionManager.KEY_ID_USER);

        // kalender
        kalender    = Calendar.getInstance();
        hari        = kalender.get(Calendar.DAY_OF_MONTH);
        bulan       = kalender.get(Calendar.MONTH);
        tahun       = kalender.get(Calendar.YEAR);

        jam         = kalender.get(Calendar.HOUR_OF_DAY);
        menit       = kalender.get(Calendar.MINUTE);

        edtJudul = (EditText) findViewById(R.id.edt_jdl);
        edtKeterangan = (EditText) findViewById(R.id.edt_ket);

        btnWaktu = (Button) findViewById(R.id.btn_wkt);
        btnTgl = (Button) findViewById(R.id.btn_tgl);
        btnSimpan = (Button) findViewById(R.id.btn_update);

        btnTgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DATE_DIALOG);
            }
        });

        btnWaktu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(TIME_DIALOG);
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtJudul.getText().toString().length() > 0 && edtKeterangan.getText().toString().length() > 0){
                    if(tanggal == null){
                        Toast.makeText(EditAgendaActivity.this, "Tanggal harus ditentukan", Toast.LENGTH_SHORT).show();
                    }
                    else if(waktu == null){
                        Toast.makeText(EditAgendaActivity.this, "Waktu harus ditentukan", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        String judul = edtJudul.getText().toString();
                        String ket = edtKeterangan.getText().toString();

                        new prosesUpdate(id_agenda, id_user, judul, ket, tanggal, waktu).execute();
                    }
                }
                else{
                    Toast.makeText(EditAgendaActivity.this, "Field tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }

            }
        });

        edtJudul.setText(judul);
        btnTgl.setText(tanggal);
        btnWaktu.setText(waktu);
        edtKeterangan.setText(keterangan);

    }

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG:
                DatePickerDialog pdp = new DatePickerDialog(this, datePickerListener, tahun, bulan, hari);
                pdp.getDatePicker().setMinDate(System.currentTimeMillis());
                return pdp;
                //return new DatePickerDialog(this, datePickerListener, tahun, bulan, hari);

            case TIME_DIALOG:
                RangeTimePickerDialog rtdp = new RangeTimePickerDialog(this, timePickerListener, jam, menit, true);
                rtdp.setMin(jam, menit);
                return rtdp;
                //return new TimePickerDialog(this, timePickerListener, jam, menit, false);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            NumberFormat f = new DecimalFormat("00");
            tanggal = selectedYear + "-" + (f.format(selectedMonth + 1)) + "-" + f.format(selectedDay);
            btnTgl.setText(tanggal);
        }
    };

    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int h, int m) {
            view.setIs24HourView(true);
            NumberFormat f = new DecimalFormat("00");
            waktu = f.format(h)+":"+f.format(m);
            btnWaktu.setText(waktu);
        }
    };

    private class prosesUpdate extends AsyncTask<Void,Void,String> {

        //variabel untuk tangkap data
        private int scs = 0;
        private String psn;
        private String id_agenda, id_user, judul, ket, tgl, waktu;

        public prosesUpdate(String id_agenda, String id_user, String judul, String ket, String tgl, String waktu){
            this.id_agenda = id_agenda;
            this.id_user = id_user;
            this.judul = judul;
            this.ket = ket;
            this.tgl = tgl;
            this.waktu = waktu;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditAgendaActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(Void... params) {

            try{
                //susun parameter
                HashMap<String,String> detail = new HashMap<>();
                detail.put("id_agenda", id_agenda);
                detail.put("id_user", id_user);
                detail.put("judul", judul);
                detail.put("keterangan", ket);
                detail.put("tanggal", tgl);
                detail.put("waktu", waktu);

                try {
                    //convert this HashMap to encodedUrl to send to php file
                    String dataToSend = hashMapToUrl(detail);
                    //make a Http request and send data to php file
                    String response = Request.post(url, dataToSend);

                    //dapatkan respon
                    Log.e("Respon", response);

                    JSONObject ob = new JSONObject(response);
                    scs = ob.getInt("success");

                    if (scs == 1) {
                        psn = ob.getString("message");
                    } else {
                        // no data found
                        psn = ob.getString("message");
                    }

                } catch (JSONException e){
                    e.printStackTrace();
                }

            } catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            pDialog.dismiss();
            if(scs == 1){
                Toast.makeText(EditAgendaActivity.this, psn, Toast.LENGTH_SHORT).show();
                finish();
                triggerAlarmManager();
            }
            else{
                Toast.makeText(EditAgendaActivity.this, psn, Toast.LENGTH_SHORT).show();
            }
        }

    }

    private String hashMapToUrl(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id_menu = item.getItemId();
        if(id_menu == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void triggerAlarmManager(){

        Intent alarmIntent = new Intent(EditAgendaActivity.this, AlarmReceiver.class);
        alarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        pendingIntent = PendingIntent.getBroadcast(EditAgendaActivity.this, ALARM_REQUEST_CODE, alarmIntent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        //simpan parameter ke memory
        SharedPreferences sharedPreferences = getSharedPreferences("key_alarm_agenda", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("judul", edtJudul.getText().toString());
        editor.putString("ket", edtKeterangan.getText().toString());

        /////parsing tanggal dan waktu////
        String tanggal2 = tanggal.replace("-","");
        String tahun = tanggal2.substring(0, 4);
        String bulan = tanggal2.substring(4, 6);

        Log.d("Berhasil baca data", bulan+"");
        String bulanfix ="";
        if(bulan.startsWith("0")) {
            bulanfix = bulan.substring(1,2);
        }
        else {
            bulanfix = bulan;
        }
        Log.d("Berhasil baca data", bulanfix+"");
        String tanggal3 = tanggal2.substring(6, 8);
        String tglfix = "";
        if(tanggal3.startsWith("0")) {
            tglfix = tanggal3.substring(1,2);
        }
        else {tglfix = tanggal3;}
        String jam = waktu.substring(0,2);
        String menit = waktu.substring(3,5);
        String menitfix ="";
        if(menit.startsWith("0")) {
            menitfix= menit.substring(1,2);
        }
        else {
            menitfix = menit;
        }
        String jamfix = "";
        if(jam.startsWith("0")) {
            jamfix = jam.substring(1,2);
        }
        else {
            jamfix = jam;
        }

        Log.d("Berhasil baca data", jamfix+""+menitfix);
        int year = Integer.parseInt(tahun);
        int monthfix = Integer.parseInt(bulanfix);
        int day = Integer.parseInt(tglfix);
        int hour = Integer.parseInt(jamfix);
        int minute = Integer.parseInt(menitfix);
        int month = monthfix -1;

        Log.w("Waktu", "Tanggal: "+day+"-"+month+"-"+year+" Jam: "+hour+":"+minute);
        /////end parsing///

        //GregorianCalendar calendar = new GregorianCalendar(year, month, day, hour, minute);
        GregorianCalendar calendar = new GregorianCalendar(year, month, day, hour - 1, minute); //1 jam sebelum kegiatan
        long wkt_alarm = calendar.getTimeInMillis();

        GregorianCalendar calendar1 = new GregorianCalendar();
        long wkt_saat_ini = calendar1.getTimeInMillis();
        ///////////////


        //matikan alarm kalau udah lewat dari jadwal
        if(wkt_alarm > wkt_saat_ini) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, wkt_alarm, pendingIntent);
            editor.apply();
        }
        else{
            alarmManager.cancel(pendingIntent);
        }

        Log.w("Waktu in millis", "waktu_alarm: " + wkt_alarm);

        Toast.makeText(this, "Agenda telah diperbaharui", Toast.LENGTH_SHORT).show();
    }
}
