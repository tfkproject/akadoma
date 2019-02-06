package ta.syifaul.akadoma.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ta.syifaul.akadoma.PengumumanDetailActivity;
import ta.syifaul.akadoma.R;
import ta.syifaul.akadoma.model.ItemJadwalSeminarTa;
import ta.syifaul.akadoma.model.ItemJadwalSidangTa;
import ta.syifaul.akadoma.util.Config;
import ta.syifaul.akadoma.util.Request;
import ta.syifaul.akadoma.util.SessionManager;

/**
 * Created by user on 15/08/18.
 */

public class JadwalSidangTaAdapter extends RecyclerView.Adapter<JadwalSidangTaAdapter.ViewHolder> {

    List<ItemJadwalSidangTa> items;
    Context context;
    private AdapterListener listener;
    SessionManager session;

    public JadwalSidangTaAdapter(Context context, List<ItemJadwalSidangTa> items, AdapterListener listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    @Override
    public JadwalSidangTaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sidang_ta, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //cek login
        session = new SessionManager(context);
        //ambil data user
        HashMap<String, String> user = session.getUserDetails();
        String level = user.get(SessionManager.KEY_LEVEL);

        if(level.contains("koorta")){
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onSelected(
                            position,
                            items.get(position).getId_sidangta(),
                            items.get(position).getTanggal(),
                            items.get(position).getWaktu(),
                            items.get(position).getRuang(),
                            items.get(position).getNama(),
                            items.get(position).getKelas(),
                            items.get(position).getJudul(),
                            items.get(position).getPembimbing1(),
                            items.get(position).getPembimbing2(),
                            items.get(position).getPenguji1(),
                            items.get(position).getPenguji2(),
                            items.get(position).getPenguji3()
                    );
                }
            });
        }else{
            holder.cardView.setOnClickListener(null);
        }

        holder.txtTanggal.setText(items.get(position).getTanggal());
        holder.txtWaktu.setText(items.get(position).getWaktu());
        holder.txtRuangan.setText(items.get(position).getRuang());
        holder.txtNama.setText(items.get(position).getNama());
        holder.txtKelas.setText(items.get(position).getKelas());
        holder.txtJudul.setText(items.get(position).getJudul());
        holder.txtPbb1.setText(items.get(position).getPembimbing1());
        holder.txtPbb2.setText(items.get(position).getPembimbing2());
        holder.txtPngj1.setText(items.get(position).getPenguji1());
        holder.txtPngj2.setText(items.get(position).getPenguji2());
        holder.txtPngj3.setText(items.get(position).getPenguji3());
        holder.txtSisaWaktu.setText(items.get(position).getSisa_waktu());
    }
    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView txtTanggal, txtWaktu, txtRuangan, txtNama, txtKelas, txtJudul, txtPbb1, txtPbb2, txtPngj1, txtPngj2, txtPngj3, txtSisaWaktu;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            txtTanggal = (TextView) itemView.findViewById(R.id.txt_tgl);
            txtWaktu = (TextView) itemView.findViewById(R.id.txt_wkt);
            txtRuangan = (TextView) itemView.findViewById(R.id.txt_rgn);
            txtNama = (TextView) itemView.findViewById(R.id.txt_nma);
            txtKelas = (TextView) itemView.findViewById(R.id.txt_kls);
            txtJudul = (TextView) itemView.findViewById(R.id.txt_jdl);
            txtPbb1 = (TextView) itemView.findViewById(R.id.txt_pbb1);
            txtPbb2 = (TextView) itemView.findViewById(R.id.txt_pbb2);
            txtPngj1 = (TextView) itemView.findViewById(R.id.txt_pgj1);
            txtPngj2 = (TextView) itemView.findViewById(R.id.txt_pgj2);
            txtPngj3 = (TextView) itemView.findViewById(R.id.txt_pgj3);
            txtSisaWaktu = (TextView) itemView.findViewById(R.id.txt_sisa_waktu);
        }
    }

    public interface AdapterListener {
        void onSelected(
                int position,
                String id_jadwal,
                String tanggal,
                String waktu,
                String ruang,
                String nama,
                String kelas,
                String judul,
                String pembimbing1,
                String pembimbing2,
                String penguji1,
                String penguji2,
                String penguji3
        );
    }
}
