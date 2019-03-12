package ta.syifaul.akadoma;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
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

import ta.syifaul.akadoma.adapter.JadwalSidangTaAdapter;
import ta.syifaul.akadoma.adapter.PengumumanAdapter;
import ta.syifaul.akadoma.adapter.PengumumanTerkirimAdapter;
import ta.syifaul.akadoma.model.ItemJadwalSidangTa;
import ta.syifaul.akadoma.model.ItemPengumuman;
import ta.syifaul.akadoma.util.Config;
import ta.syifaul.akadoma.util.Request;
import ta.syifaul.akadoma.util.SessionManager;

public class PengumumanTerkirimActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView txtNotif;
    private PengumumanTerkirimAdapter adapter;
    LinearLayoutManager layoutManager;
    List<ItemPengumuman> items;
    private ProgressDialog pDialog;
    private SessionManager session;

    private static String url = Config.HOST+"list_pengumuman_terkirim.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengumuman_terkirim);

        session = new SessionManager(PengumumanTerkirimActivity.this);

        getSupportActionBar().setTitle("Pengumuman Terkirim");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtNotif = (TextView) findViewById(R.id.txt_notif);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(PengumumanTerkirimActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        items = new ArrayList<>();

        //new getData().execute();

        adapter = new PengumumanTerkirimAdapter(PengumumanTerkirimActivity.this, items);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id_menu = item.getItemId();
        if(id_menu == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private class getData extends AsyncTask<Void,Void,String> {

        //variabel untuk tangkap data
        private int scs = 0;
        private String psn, id_user;

        public getData(String id_user){
            this.id_user = id_user;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PengumumanTerkirimActivity.this);
            pDialog.setMessage("Memuat data...");
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
                    String response = Request.post(url,dataToSend);

                    //dapatkan respon
                    Log.e("Respon", response);

                    JSONObject ob = new JSONObject(response);
                    scs = ob.getInt("success");

                    if (scs == 1) {
                        JSONArray products = ob.getJSONArray("field");

                        for (int i = 0; i < products.length(); i++) {
                            JSONObject c = products.getJSONObject(i);

                            // Storing each json item in variable
                            String id = c.getString("id_pengumuman");
                            String id_user = c.getString("id_user");
                            String judul = c.getString("judul");
                            String tanggal = c.getString("tanggal");
                            String waktu = c.getString("waktu");
                            String dari = c.getString("nama");
                            String ket = c.getString("keterangan");
                            String level = c.getString("level");
                            String tujuan = c.getString("tujuan");
                            String jab = "";
                            if(level.contains("mhs")){
                                jab = "mahasiswa";
                            }
                            if(level.contains("dsn")){
                                jab = "dosen terkait";
                            }
                            if(level.contains("koorkp")){
                                jab = "Koordinator KP";
                            }
                            if(level.contains("koorta")){
                                jab = "Koordinator TA";
                            }

                            items.add(new ItemPengumuman(id, id_user, dari, judul, tanggal, waktu, ket, jab, tujuan));
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
            adapter.notifyDataSetChanged();
            pDialog.dismiss();
            if (scs == 1) {
                txtNotif.setVisibility(View.GONE);
            } else {
                txtNotif.setVisibility(View.VISIBLE);
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
    protected void onResume() {
        super.onResume();
        items.clear();
        HashMap<String, String> user = session.getUserDetails();
        final String id_user = user.get(SessionManager.KEY_ID_USER);
        new getData(id_user).execute();
    }
}
