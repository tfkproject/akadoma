package ta.syifaul.akadoma.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import ta.syifaul.akadoma.InputPengumumanActivity;
import ta.syifaul.akadoma.R;
import ta.syifaul.akadoma.adapter.PengumumanAdapter;
import ta.syifaul.akadoma.model.ItemPengumuman;
import ta.syifaul.akadoma.util.Config;
import ta.syifaul.akadoma.util.Request;

/**
 * Created by taufik on 17/04/18.
 */

public class PengumumanFragment extends Fragment {

    RecyclerView recyclerView;
    TextView txtNotif;
    private PengumumanAdapter adapter;
    LinearLayoutManager layoutManager;
    List<ItemPengumuman> items;
    private ProgressDialog pDialog;

    private static String url = Config.HOST+"list_pengumuman.php";

    FloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_ly_pengumuman, container, false);

        String level_ = getActivity().getIntent().getStringExtra("level");

        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        if(level_.contains("mhs")){
            fab.setVisibility(View.GONE);
        }
        if(level_.contains("dsn")){
            fab.setVisibility(View.VISIBLE);
        }
        if(level_.contains("koor")){
            fab.setVisibility(View.VISIBLE);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), InputPengumumanActivity.class);
                startActivity(intent);
            }
        });

        txtNotif = (TextView) rootView.findViewById(R.id.txt_notif);

        recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        items = new ArrayList<>();

        //new getData().execute();

        adapter = new PengumumanAdapter(getActivity(), items);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    private class getData extends AsyncTask<Void,Void,String> {

        //variabel untuk tangkap data
        private int scs = 0;
        private String psn;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Memuat data...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(Void... params) {

            try{
                //susun parameter
                HashMap<String,String> detail = new HashMap<>();
                /*detail.put("email", email);
                detail.put("password", password);*/

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
                            String jab = "";
                            if(level.contains("mhs")){
                                jab = "mahasiswa";
                            }
                            if(level.contains("dsn")){
                                jab = "dosen terkait";
                            }
                            if(level.contains("kp")){
                                jab = "Koordinator KP";
                            }
                            if(level.contains("ta")){
                                jab = "Koordinator TA";
                            }

                            items.add(new ItemPengumuman(id, id_user, dari, judul, tanggal, waktu, ket, jab));
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
    public void onResume() {
        super.onResume();
        items.clear();
        new getData().execute();
    }
}
