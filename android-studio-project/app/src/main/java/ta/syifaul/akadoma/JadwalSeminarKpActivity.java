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

import ta.syifaul.akadoma.adapter.JadwalSeminarKpAdapter;
import ta.syifaul.akadoma.adapter.JadwalSeminarTaAdapter;
import ta.syifaul.akadoma.model.ItemJadwalSeminarKp;
import ta.syifaul.akadoma.model.ItemJadwalSeminarTa;
import ta.syifaul.akadoma.util.Config;
import ta.syifaul.akadoma.util.Request;

public class JadwalSeminarKpActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView txtNotif;
    private JadwalSeminarKpAdapter adapter;
    LinearLayoutManager layoutManager;
    List<ItemJadwalSeminarKp> items;
    private ProgressDialog pDialog;

    private static String url = Config.HOST+"list_jdw_sm_kp.php";
    private static String url_hps = Config.HOST + "hapus_jdw_sm_kp.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_seminar_kp);

        getSupportActionBar().setTitle("Jadwal Seminar KP");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtNotif = (TextView) findViewById(R.id.txt_notif);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(JadwalSeminarKpActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        items = new ArrayList<>();

        //new getData().execute();

        adapter = new JadwalSeminarKpAdapter(JadwalSeminarKpActivity.this, items, new JadwalSeminarKpAdapter.AdapterListener() {
            @Override
            public void onSelected(
                    int position,
                    String id_jadwal,
                    String tanggal,
                    String waktu,
                    String ruang,
                    String nama,
                    String kelas,
                    String judul,
                    String pembimbing
            ) {
                tampilPilihan(
                        id_jadwal,
                        tanggal,
                        waktu,
                        ruang,
                        nama,
                        kelas,
                        judul,
                        pembimbing
                );
            }
        });
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
        private String psn;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(JadwalSeminarKpActivity.this);
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
                            String id_jseminarkp = c.getString("id_jseminarkp");
                            String nama = c.getString("nama");
                            String kelas = c.getString("kelas");
                            String judul = c.getString("judul");
                            String pembimbing = c.getString("pembimbing");
                            String ruangan = c.getString("ruangan");
                            String tanggal = c.getString("tanggal");
                            String waktu = c.getString("waktu");
                            String sisa_waktu = c.getString("sisa_waktu");

                            items.add(new ItemJadwalSeminarKp(id_jseminarkp, tanggal, waktu, ruangan, nama, kelas, judul, pembimbing, sisa_waktu));
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

    private class prosesHapus extends AsyncTask<Void,Void,String> {

        //variabel untuk tangkap data
        private int scs = 0;
        private String psn;
        private String id_jseminarkp;

        public prosesHapus(String id_jseminarkp){
            this.id_jseminarkp = id_jseminarkp;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(JadwalSeminarKpActivity.this);
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(Void... params) {

            try{
                //susun parameter
                HashMap<String,String> detail = new HashMap<>();
                detail.put("id_jseminarkp", id_jseminarkp);

                try {
                    //convert this HashMap to encodedUrl to send to php file
                    String dataToSend = hashMapToUrl(detail);
                    //make a Http request and send data to php file
                    String response = Request.post(url_hps, dataToSend);

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
                Toast.makeText(JadwalSeminarKpActivity.this, psn, Toast.LENGTH_SHORT).show();
                items.clear();
                new getData().execute();
            }
            else{
                Toast.makeText(JadwalSeminarKpActivity.this, psn, Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void tampilPilihan(
            final String id_jadwal,
            final String tanggal,
            final String waktu,
            final String ruang,
            final String nama,
            final String kelas,
            final String judul,
            final String pembimbing
    ){
        final CharSequence[] options = { "Edit Jadwal", "Hapus Jadwal","Batal" };
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(JadwalSeminarKpActivity.this);
        builder.setItems(options,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                if(options[which].equals("Edit Jadwal")) {
                    Intent intent = new Intent(JadwalSeminarKpActivity.this, EditJadwalSeminarKpActivity.class);
                    intent.putExtra("key_id_jadwal", id_jadwal);
                    intent.putExtra("key_tanggal", tanggal);
                    intent.putExtra("key_waktu", waktu);
                    intent.putExtra("key_ruang", ruang);
                    intent.putExtra("key_nama", nama);
                    intent.putExtra("key_kelas", kelas);
                    intent.putExtra("key_judul", judul);
                    intent.putExtra("key_pbb", pembimbing);
                    startActivity(intent);
                }
                else if(options[which].equals("Hapus Jadwal")) {
                    new prosesHapus(id_jadwal).execute();
                }
                else if(options[which].equals("Batal")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
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
        new getData().execute();
    }
}
