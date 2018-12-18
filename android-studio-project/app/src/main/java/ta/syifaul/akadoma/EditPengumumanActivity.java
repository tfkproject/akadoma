package ta.syifaul.akadoma;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
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
import java.util.HashMap;
import java.util.Map;

import ta.syifaul.akadoma.util.Config;
import ta.syifaul.akadoma.util.Request;
import ta.syifaul.akadoma.util.SessionManager;


public class EditPengumumanActivity extends AppCompatActivity {

    EditText edtJudul, edtKeterangan;
    Button btnTgl, btnWaktu, btnSimpan;

    private ProgressDialog pDialog;

    SessionManager session;
    private static String url = Config.HOST+"edit_pengumuman.php";

    private Calendar kalender;
    private int hari, bulan, tahun;
    private int jam, menit;
    static final int DATE_DIALOG = 1;
    static final int TIME_DIALOG = 2;
    private String tanggal, waktu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_pengumuman);

        session = new SessionManager(EditPengumumanActivity.this);

        getSupportActionBar().setTitle("Edit Pengumuman");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final String id_pengumuman = getIntent().getStringExtra("key_id_pengumuman");
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
                        Toast.makeText(EditPengumumanActivity.this, "Tanggal harus ditentukan", Toast.LENGTH_SHORT).show();
                    }
                    else if(waktu == null){
                        Toast.makeText(EditPengumumanActivity.this, "Waktu harus ditentukan", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        String judul = edtJudul.getText().toString();
                        String ket = edtKeterangan.getText().toString();

                        new prosesUpdate(id_pengumuman, id_user, judul, ket, tanggal, waktu).execute();
                    }
                }
                else{
                    Toast.makeText(EditPengumumanActivity.this, "Field tidak boleh kosong", Toast.LENGTH_SHORT).show();
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
                return new DatePickerDialog(this, datePickerListener, tahun, bulan, hari);

            case TIME_DIALOG:
                return new TimePickerDialog(this, timePickerListener, jam, menit, false);
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
        private String id_pengumuman, id_user, judul, ket, tgl, waktu;

        public prosesUpdate(String id_pengumuman, String id_user, String judul, String ket, String tgl, String waktu){
            this.id_pengumuman = id_pengumuman;
            this.id_user = id_user;
            this.judul = judul;
            this.ket = ket;
            this.tgl = tgl;
            this.waktu = waktu;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EditPengumumanActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(Void... params) {

            try{
                //susun parameter
                HashMap<String,String> detail = new HashMap<>();
                detail.put("id_pengumuman", id_pengumuman);
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
                Toast.makeText(EditPengumumanActivity.this, psn, Toast.LENGTH_SHORT).show();
                finish();
            }
            else{
                Toast.makeText(EditPengumumanActivity.this, psn, Toast.LENGTH_SHORT).show();
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
}
