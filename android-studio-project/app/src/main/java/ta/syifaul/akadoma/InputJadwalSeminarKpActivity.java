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
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import ta.syifaul.akadoma.util.AlarmReceiver;
import ta.syifaul.akadoma.util.Config;
import ta.syifaul.akadoma.util.RangeTimePickerDialog;
import ta.syifaul.akadoma.util.Request;
import ta.syifaul.akadoma.util.SessionManager;


public class InputJadwalSeminarKpActivity extends AppCompatActivity {

    private PendingIntent pendingIntent;
    private static final int ALARM_REQUEST_CODE = 133;

    EditText edtKelas, edtJudul, edt_Ruangan;
    AutoCompleteTextView edtNama, edt_Pembimbing;
    Button btnTgl, btnWaktu, btnSimpan;

    private ProgressDialog pDialog;
    private ArrayAdapter<String> adapter;
    private List<String> list;
    private ArrayAdapter<String> adapterMhs;
    private List<String> list_mhs;

    SessionManager session;
    private static String url = Config.HOST+"input_jdw_sm_kp.php";
    private static String url_dt_dosen_koor = Config.HOST+"data_dosen.php";
    private static String url_dt_mhs = Config.HOST+"data_mhs.php";

    private Calendar kalender;
    private int hari, bulan, tahun;
    private int jam, menit;
    static final int DATE_DIALOG = 1;
    static final int TIME_DIALOG = 2;
    private String tanggal, waktu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_jadwal_sm_kp);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        getSupportActionBar().setTitle("Input Data Seminar KP");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // kalender
        kalender    = Calendar.getInstance();
        hari        = kalender.get(Calendar.DAY_OF_MONTH);
        bulan       = kalender.get(Calendar.MONTH);
        tahun       = kalender.get(Calendar.YEAR);

        jam         = kalender.get(Calendar.HOUR_OF_DAY);
        menit       = kalender.get(Calendar.MINUTE);

        ///
        edtNama = (AutoCompleteTextView) findViewById(R.id.edt_nama);
        edtKelas = (EditText) findViewById(R.id.edt_kelas);
        edtJudul = (EditText) findViewById(R.id.edt_jdl);
        edt_Pembimbing = (AutoCompleteTextView) findViewById(R.id.edt_pembimbing);
        edt_Ruangan = (EditText) findViewById(R.id.edt_ruangan);
        ///

        btnWaktu = (Button) findViewById(R.id.btn_wkt);
        btnTgl = (Button) findViewById(R.id.btn_tgl);
        btnSimpan = (Button) findViewById(R.id.btn_simpan);

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
                if(tanggal == null){
                    Toast.makeText(InputJadwalSeminarKpActivity.this, "Tanggal harus ditentukan", Toast.LENGTH_SHORT).show();
                }
                else if(waktu == null){
                    Toast.makeText(InputJadwalSeminarKpActivity.this, "Waktu harus ditentukan", Toast.LENGTH_SHORT).show();
                }else{
                    String nama = edtNama.getText().toString();
                    String kelas = edtKelas.getText().toString();
                    String judul = edtJudul.getText().toString();
                    String pbb = edt_Pembimbing.getText().toString();
                    String ruangan = edt_Ruangan.getText().toString();

                    new prosesSimpan(nama, kelas, judul, pbb, ruangan, tanggal, waktu).execute();
                }
            }
        });

        list = new ArrayList<String>();
        list_mhs = new ArrayList<String>();
        new getDataDsnKoor().execute();
        new getDataMhs().execute();
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
//                RangeTimePickerDialog rtdp = new RangeTimePickerDialog(this, timePickerListener, jam, menit, true);
//                rtdp.setMin(jam, menit);
//                return rtdp;
                return new TimePickerDialog(this, timePickerListener, jam, menit, true);
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

    private class prosesSimpan extends AsyncTask<Void,Void,String> {

        //variabel untuk tangkap data
        private int scs = 0;
        private String psn;
        private String nama, kelas, judul, pembimbing, ruangan, tgl, waktu;

        public prosesSimpan(
                String nama,
                String kelas,
                String judul,
                String pembimbing,
                String ruangan,
                String tgl,
                String waktu
        ){
            this.nama = nama;
            this.kelas = kelas;
            this.judul = judul;
            this.pembimbing = pembimbing;
            this.ruangan = ruangan;
            this.tgl = tgl;
            this.waktu = waktu;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(InputJadwalSeminarKpActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(Void... params) {

            try{
                //susun parameter
                HashMap<String,String> detail = new HashMap<>();
                detail.put("nama", nama);
                detail.put("kelas", kelas);
                detail.put("judul", judul);
                detail.put("pembimbing", pembimbing);
                detail.put("ruangan", ruangan);
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
                Toast.makeText(InputJadwalSeminarKpActivity.this, psn, Toast.LENGTH_SHORT).show();
                finish();
                Intent intent = new Intent(InputJadwalSeminarKpActivity.this, JadwalSeminarTaActivity.class);
                startActivity(intent);
                //set alarm
                triggerAlarmManager();
                //buat notifikasi
                buatNotifikasi(judul);
            }
            else{
                Toast.makeText(InputJadwalSeminarKpActivity.this, psn, Toast.LENGTH_SHORT).show();
            }
        }

    }

    private class getDataMhs extends AsyncTask<Void,Void,String> {

        //variabel untuk tangkap data
        private int scs = 0;
        private String psn, nama_user;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            pDialog = new ProgressDialog(InputJadwalSeminarTaActivity.this);
//            pDialog.setMessage("Memuat data...");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(false);
//            pDialog.show();
        }

        protected String doInBackground(Void... params) {

            try{
                //susun parameter
                HashMap<String,String> detail = new HashMap<>();

                try {
                    //convert this HashMap to encodedUrl to send to php file
                    String dataToSend = hashMapToUrl(detail);
                    //make a Http request and send data to php file
                    String response = Request.post(url_dt_mhs,dataToSend);

                    //dapatkan respon
                    Log.e("Respon", response);

                    JSONObject ob = new JSONObject(response);
                    scs = ob.getInt("success");

                    if (scs == 1) {
                        JSONArray products = ob.getJSONArray("field");

                        for (int i = 0; i < products.length(); i++) {
                            JSONObject c = products.getJSONObject(i);

                            // Storing each json item in variable
                            nama_user = c.getString("nama");
                            list_mhs.add(nama_user);
                        }

                    } else {
                        // no data found
                        //psn = ob.getString("message");
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
//            pDialog.dismiss();
            if (scs == 1) {
                String[] data_user = list_mhs.toArray(new String[list_mhs.size()]);

                adapterMhs = new ArrayAdapter<String>
                        (InputJadwalSeminarKpActivity.this, android.R.layout.select_dialog_item, data_user);

                edtNama.setThreshold(1);
                edtNama.setAdapter(adapterMhs);
            } else {
                Toast.makeText(InputJadwalSeminarKpActivity.this, "Terjadi kesalahan saat memuat data dosen", Toast.LENGTH_SHORT).show();
            }

        }

    }

    private class getDataDsnKoor extends AsyncTask<Void,Void,String> {

        //variabel untuk tangkap data
        private int scs = 0;
        private String psn, nama_user;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(InputJadwalSeminarKpActivity.this);
            pDialog.setMessage("Memuat data...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(Void... params) {

            try{
                //susun parameter
                HashMap<String,String> detail = new HashMap<>();

                try {
                    //convert this HashMap to encodedUrl to send to php file
                    String dataToSend = hashMapToUrl(detail);
                    //make a Http request and send data to php file
                    String response = Request.post(url_dt_dosen_koor,dataToSend);

                    //dapatkan respon
                    Log.e("Respon", response);

                    JSONObject ob = new JSONObject(response);
                    scs = ob.getInt("success");

                    if (scs == 1) {
                        JSONArray products = ob.getJSONArray("field");

                        for (int i = 0; i < products.length(); i++) {
                            JSONObject c = products.getJSONObject(i);

                            // Storing each json item in variable
                            nama_user = c.getString("nama");
                            list.add(nama_user);
                        }

                    } else {
                        // no data found
                        //psn = ob.getString("message");
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
            if (scs == 1) {
                String[] data_user = list.toArray(new String[list.size()]);

                adapter = new ArrayAdapter<String>
                        (InputJadwalSeminarKpActivity.this, android.R.layout.select_dialog_item, data_user);

                edt_Pembimbing.setThreshold(1);
                edt_Pembimbing.setAdapter(adapter);

            } else {
                Toast.makeText(InputJadwalSeminarKpActivity.this, "Terjadi kesalahan saat memuat data dosen", Toast.LENGTH_SHORT).show();
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

    private void buatNotifikasi(String judul){
        try {
            String jsonResponse;

            URL url = new URL("https://onesignal.com/api/v1/notifications");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Authorization", "Basic ZDQ5NGZkNTAtZTQ1Mi00MDkzLThhYTItNjE1YzE4ZDUzNmRk");
            con.setRequestMethod("POST");

            String strJsonBody = "{"
                    +   "\"app_id\": \"b7f49fad-d107-4484-b905-f11d02f670a8\","
                    +   "\"included_segments\": [\"All\"],"
                    +   "\"data\": {\"foo\": \"bar\"},"
                    +   "\"contents\": {\"en\": \"Jadwal Seminar: "+judul+"\"}"
                    + "}";


            System.out.println("strJsonBody:\n" + strJsonBody);

            byte[] sendBytes = strJsonBody.getBytes("UTF-8");
            con.setFixedLengthStreamingMode(sendBytes.length);

            OutputStream outputStream = con.getOutputStream();
            outputStream.write(sendBytes);

            int httpResponse = con.getResponseCode();
            System.out.println("httpResponse: " + httpResponse);

            if (  httpResponse >= HttpURLConnection.HTTP_OK
                    && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                scanner.close();
            }
            else {
                Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                scanner.close();
            }
            System.out.println("jsonResponse:\n" + jsonResponse);

        } catch(Throwable t) {
            t.printStackTrace();
        }
    }

    private void triggerAlarmManager(){

        Intent alarmIntent = new Intent(InputJadwalSeminarKpActivity.this, AlarmReceiver.class);
        alarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        pendingIntent = PendingIntent.getBroadcast(InputJadwalSeminarKpActivity.this, ALARM_REQUEST_CODE, alarmIntent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        //simpan parameter ke memory
        SharedPreferences sharedPreferences = getSharedPreferences("key_alarm_agenda", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("judul", "Jadwal Seminar TA: "+edtNama.getText().toString());
        editor.putString("ket", "Judul: "+edtJudul.getText().toString()+", "+tanggal + " " + waktu + " WIB");

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
        else {
            tglfix = tanggal3;
        }
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
        GregorianCalendar calendar = new GregorianCalendar(year, month, day - 1, hour, minute); //1 hari sebelum jadwal

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
    }
}
