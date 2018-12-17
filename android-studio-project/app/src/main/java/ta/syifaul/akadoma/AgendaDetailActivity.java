package ta.syifaul.akadoma;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import ta.syifaul.akadoma.util.Config;
import ta.syifaul.akadoma.util.Request;

public class AgendaDetailActivity extends AppCompatActivity {

    private TextView txtJudul, txtTanggal, txtWaktu, txtKeterangan, txtNama, txtjabatan;
    private Button btnHapus, btnEdit;
    private ProgressDialog pDialog;

    private static String url = Config.HOST+"hapus_agenda.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda_detail);

        getSupportActionBar().setTitle("Detail Agenda");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final String id_agenda = getIntent().getStringExtra("key_id_agenda");
        final String judul = getIntent().getStringExtra("key_judul");
        final String tanggal = getIntent().getStringExtra("key_tgl");
        final String waktu = getIntent().getStringExtra("key_wkt");
        final String keterangan = getIntent().getStringExtra("key_ket");

        txtJudul = (TextView) findViewById(R.id.txt_jdl);
        txtTanggal = (TextView) findViewById(R.id.txt_tgl);
        txtWaktu = (TextView) findViewById(R.id.txt_wkt);
        txtKeterangan = (TextView) findViewById(R.id.txt_ket);

        btnHapus = (Button) findViewById(R.id.btn_hapus);
        btnEdit = (Button) findViewById(R.id.btn_edit);

        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AgendaDetailActivity.this);

                builder.setTitle("Konfirmasi");
                builder.setMessage("Yakin ingin menghapus data?");

                builder.setPositiveButton("YA", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        new prosesHapus(id_agenda).execute();
                    }
                });

                builder.setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // Do nothing
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AgendaDetailActivity.this, EditAgendaActivity.class);
                intent.putExtra("key_id_agenda", id_agenda);
                intent.putExtra("key_judul", judul);
                intent.putExtra("key_tgl", tanggal);
                intent.putExtra("key_wkt", waktu);
                intent.putExtra("key_ket", keterangan);
                startActivity(intent);

                finish();
            }
        });

        txtJudul.setText(judul);
        txtTanggal.setText(tanggal);
        txtWaktu.setText(waktu);
        txtKeterangan.setText(keterangan);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id_menu = item.getItemId();
        if(id_menu == android.R.id.home){
            finish();
        }

        if(id_menu == R.id.act_refresh){
            finish();
            startActivity(getIntent());
        }
        return super.onOptionsItemSelected(item);
    }

    private class prosesHapus extends AsyncTask<Void,Void,String> {

        //variabel untuk tangkap data
        private int scs = 0;
        private String psn;
        private String id_agenda;

        public prosesHapus(String id_agenda){
            this.id_agenda = id_agenda;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AgendaDetailActivity.this);
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
                Toast.makeText(AgendaDetailActivity.this, psn, Toast.LENGTH_SHORT).show();
                finish();
            }
            else{
                Toast.makeText(AgendaDetailActivity.this, psn, Toast.LENGTH_SHORT).show();
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
