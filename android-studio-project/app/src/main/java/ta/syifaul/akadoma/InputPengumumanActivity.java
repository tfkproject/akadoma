package ta.syifaul.akadoma;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import ta.syifaul.akadoma.util.Config;
import ta.syifaul.akadoma.util.Request;
import ta.syifaul.akadoma.util.SessionManager;


public class InputPengumumanActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText edtJudul, edtKeterangan;
    Button btnTgl, btnWaktu, btnSimpan;
    int posisi = 0;

    private ProgressDialog pDialog;

    SessionManager session;
    private static String url = Config.HOST+"input_pengumuman.php";

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

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        session = new SessionManager(InputPengumumanActivity.this);

        getSupportActionBar().setTitle("Input Pengumuman");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.tujuan);

        // Spinner click listener
        spinner.setOnItemSelectedListener(InputPengumumanActivity.this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Pilih tujuan");
        categories.add("Mahasiwa");
        categories.add("Dosen");
        categories.add("Koordinator KP");
        categories.add("Koordinator TA");
        categories.add("Semua");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

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
                    tanggal = "0000-00-00";
                    waktu = "00:00:00";
                    if(tanggal == null){
                        Toast.makeText(InputPengumumanActivity.this, "Tanggal harus ditentukan", Toast.LENGTH_SHORT).show();
                    }
                    else if(waktu == null){
                        Toast.makeText(InputPengumumanActivity.this, "Waktu harus ditentukan", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        String judul = edtJudul.getText().toString();
                        String ket = edtKeterangan.getText().toString();

                        ///
                        switch(posisi) {
                            case 1 :
                                new prosesSimpan(id_user, judul, ket, tanggal, waktu, "mhs").execute();
                                break; // optional

                            case 2 :
                                new prosesSimpan(id_user, judul, ket, tanggal, waktu, "dsn").execute();
                                break; // optional

                            case 3 :
                                new prosesSimpan(id_user, judul, ket, tanggal, waktu, "koorkp").execute();
                                break; // optional

                            case 4 :
                                new prosesSimpan(id_user, judul, ket, tanggal, waktu, "koorta").execute();
                                break; // optional

                            case 5 :
                                new prosesSimpan(id_user, judul, ket, tanggal, waktu, "sma").execute();
                                break; // optional

                            default : // Optional
                                Toast.makeText(InputPengumumanActivity.this, "Pilih tujuan pengumuman", Toast.LENGTH_SHORT).show();
                        }
                        ///
                    }
                }
                else{
                    Toast.makeText(InputPengumumanActivity.this, "Field tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        posisi = position;

//        switch(posisi) {
//            case 1 :
//                edtId.setHint("NIM");
//                break; // optional
//
//            case 2 :
//                edtId.setHint("NIP");
//                break; // optional
//
//            case 3 :
//                edtId.setHint("NIP");
//                break;
//
//            case 4 :
//                edtId.setHint("NIP");
//                break;
//
//            case 5 :
//                edtId.setHint("NIP");
//                break;
//
//            default : // Optional
//                edtId.setHint("- Pilih login anda -");
//        }

        // Showing selected spinner item
        //Toast.makeText(parent.getContext(), "Selected: " + position, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
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

    private class prosesSimpan extends AsyncTask<Void,Void,String> {

        //variabel untuk tangkap data
        private int scs = 0;
        private String psn;
        private String id_user, judul, ket, tgl, waktu, tujuan;

        public prosesSimpan(String id_user, String judul, String ket, String tgl, String waktu, String tujuan){
            this.id_user = id_user;
            this.judul = judul;
            this.ket = ket;
            this.tgl = tgl;
            this.waktu = waktu;
            this.tujuan = tujuan;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(InputPengumumanActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(Void... params) {

            try{
                //susun parameter
                HashMap<String,String> detail = new HashMap<>();
                detail.put("id_user", id_user);
                detail.put("judul", judul);
                detail.put("keterangan", ket);
                detail.put("tanggal", tgl);
                detail.put("waktu", waktu);
                detail.put("tujuan", tujuan);

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
                Toast.makeText(InputPengumumanActivity.this, psn, Toast.LENGTH_SHORT).show();
                finish();
                //buat notifikasi
                buatNotifikasi(judul);
            }
            else{
                Toast.makeText(InputPengumumanActivity.this, psn, Toast.LENGTH_SHORT).show();
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
                    +   "\"contents\": {\"en\": \"Pengumuman: "+judul+"\"}"
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
}
