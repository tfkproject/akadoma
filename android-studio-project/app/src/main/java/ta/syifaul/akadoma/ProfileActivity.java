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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
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
import ta.syifaul.akadoma.util.Request;
import ta.syifaul.akadoma.util.SessionManager;


public class ProfileActivity extends AppCompatActivity {

    TextView txtIdn, txtNama, txtTelp, txtPass, txtAkses;
    Button btnEdit;

    private ProgressDialog pDialog;

    SessionManager session;
    private static String url = Config.HOST+"user_data.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        session = new SessionManager(ProfileActivity.this);

        getSupportActionBar().setTitle("Profil Anda");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtIdn = (TextView) findViewById(R.id.txt_idn);
        txtNama = (TextView) findViewById(R.id.txt_nma);
        txtTelp = (TextView) findViewById(R.id.txt_tlp);
        txtPass = (TextView) findViewById(R.id.txt_pass);
        txtAkses = (TextView) findViewById(R.id.txt_aks);

        btnEdit = (Button) findViewById(R.id.btn_edit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                intent.putExtra("key_idn", txtIdn.getText().toString());
                intent.putExtra("key_nama", txtNama.getText().toString());
                intent.putExtra("key_telp", txtTelp.getText().toString());
                intent.putExtra("key_pass", txtPass.getText().toString());
                startActivity(intent);
            }
        });

    }


    private class getData extends AsyncTask<Void,Void,String> {

        //variabel untuk tangkap data
        private int scs = 0;
        private String psn;
        private String id_user, no_idn, nama, telpon, password, akses;

        public getData(String id_user){
            this.id_user = id_user;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ProfileActivity.this);
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

                try {
                    //convert this HashMap to encodedUrl to send to php file
                    String dataToSend = hashMapToUrl(detail);
                    //make a Http request and send data to php file
                    String response = Request.post(url, dataToSend);

                    //dapatkan respon
                    Log.e("Respon", response);

                    JSONObject ob = new JSONObject(response);
                    scs = ob.getInt("success");

                    //get data here (idn, nama, telp, akses)

                    if (scs == 1) {
                        JSONArray products = ob.getJSONArray("field");

                        for (int i = 0; i < products.length(); i++) {
                            JSONObject c = products.getJSONObject(i);

                            // Storing each json item in variable
                            id_user = c.getString("id_user");
                            no_idn = c.getString("no_identitas");
                            nama = c.getString("nama");
                            telpon = c.getString("no_tlp");
                            password = c.getString("password");
                            akses = c.getString("level");
                        }
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
                txtIdn.setText(no_idn);
                txtNama.setText(nama);
                txtTelp.setText(telpon);
                txtPass.setText(password);
                if(akses.contains("mhs")){
                    akses = "Mahasiswa";
                }
                if(akses.contains("dsn")){
                    akses = "Dosen";
                }
                if(akses.contains("koor")){
                    akses = "Koordinator";
                }
                txtAkses.setText(akses);
            }
            else{
                Toast.makeText(ProfileActivity.this, psn, Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onResume() {
        //ambil data user
        HashMap<String, String> user = session.getUserDetails();
        final String id_user = user.get(SessionManager.KEY_ID_USER);

        new getData(id_user).execute();
        super.onResume();
    }
}
