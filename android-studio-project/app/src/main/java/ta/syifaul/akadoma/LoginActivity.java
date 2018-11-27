package ta.syifaul.akadoma;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ta.syifaul.akadoma.util.Config;
import ta.syifaul.akadoma.util.Request;
import ta.syifaul.akadoma.util.SessionManager;


public class LoginActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText edtId, edtPass;
    Button btnLogin;
    int posisi = 0;
    private ProgressDialog pDialog;

    SessionManager session;
    private static String url = Config.HOST+"user_login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new SessionManager(LoginActivity.this);

        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.jenis_login);

        // Spinner click listener
        spinner.setOnItemSelectedListener(LoginActivity.this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Pilih login");
        categories.add("Mahasiwa");
        categories.add("Dosen");
        categories.add("Koordinator TA");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        edtId = (EditText) findViewById(R.id.edt_id);

        edtPass = (EditText) findViewById(R.id.edt_pass);

        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = edtId.getText().toString();
                String pass = edtPass.getText().toString();
                switch(posisi) {
                    case 1 :
                        new prosesLogin(id, pass, "mhs").execute();
                        break; // optional

                    case 2 :
                        new prosesLogin(id, pass, "dsn").execute();
                        break; // optional

                    case 3 :
                        new prosesLogin(id, pass, "koor").execute();
                        break; // optional
                    default : // Optional
                        Toast.makeText(LoginActivity.this, "Pilih login anda", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        posisi = position;

        switch(posisi) {
            case 1 :
                edtId.setHint("NIM");
                break; // optional

            case 2 :
                edtId.setHint("NIP");
                break; // optional

            case 3 :
                edtId.setHint("NIP");
                break;

            default : // Optional
                edtId.setHint("- Pilih login anda -");
        }

        // Showing selected spinner item
        //Toast.makeText(parent.getContext(), "Selected: " + position, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    private class prosesLogin extends AsyncTask<Void,Void,String> {

        //variabel untuk tangkap data
        private int scs = 0;
        private String psn;
        private String no_id, password, level, level_;

        public prosesLogin(String no_id, String password, String level){
            this.no_id = no_id;
            this.password = password;
            this.level = level;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(Void... params) {

            try{
                //susun parameter
                HashMap<String,String> detail = new HashMap<>();
                detail.put("no_id", no_id);
                detail.put("pass", password);
                detail.put("level", level);

                try {
                    //convert this HashMap to encodedUrl to send to php file
                    String dataToSend = hashMapToUrl(detail);
                    //make a Http request and send data to php file
                    String response = Request.post(url, dataToSend);

                    //dapatkan respon
                    Log.e("NIM", no_id);
                    Log.e("Pass", password);
                    Log.e("Level", level);
                    Log.e("Url", url);
                    Log.e("Respon", response);

                    JSONObject ob = new JSONObject(response);
                    scs = ob.getInt("success");

                    if (scs == 1) {
                        JSONArray products = ob.getJSONArray("field");

                        for (int i = 0; i < products.length(); i++) {
                            JSONObject c = products.getJSONObject(i);

                            // Storing each json item in variable
                            String id_user = c.getString("id_user");
                            String no_identitas = c.getString("no_identitas");
                            String nama = c.getString("nama");
                            String no_tlp = c.getString("no_tlp");
                            level_ = c.getString("level");

                            //buat sesi login
                            session.createLoginSession(level_, id_user, nama, no_tlp);
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
                finish();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("level", level_); //akan di paket di fragment jadwal dan pengumuman
                startActivity(intent);
            }
            else{
                Toast.makeText(LoginActivity.this, psn, Toast.LENGTH_SHORT).show();
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
}
