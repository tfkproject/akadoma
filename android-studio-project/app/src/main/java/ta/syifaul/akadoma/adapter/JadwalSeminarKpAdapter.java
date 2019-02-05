package ta.syifaul.akadoma.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import ta.syifaul.akadoma.R;
import ta.syifaul.akadoma.model.ItemJadwalSeminarKp;
import ta.syifaul.akadoma.model.ItemJadwalSeminarTa;
import ta.syifaul.akadoma.util.SessionManager;

/**
 * Created by user on 15/08/18.
 */

public class JadwalSeminarKpAdapter extends RecyclerView.Adapter<JadwalSeminarKpAdapter.ViewHolder> {

    List<ItemJadwalSeminarKp> items;
    Context context;
    private AdapterListener listener;
    SessionManager session;

    public JadwalSeminarKpAdapter(Context context, List<ItemJadwalSeminarKp> items, AdapterListener listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    @Override
    public JadwalSeminarKpAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_seminar_kp, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //cek login
        session = new SessionManager(context);
        //ambil data user
        HashMap<String, String> user = session.getUserDetails();
        String level = user.get(SessionManager.KEY_LEVEL);

        if(level.contains("koor")){
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onSelected(
                            position,
                            items.get(position).getId_seminarkp(),
                            items.get(position).getTanggal(),
                            items.get(position).getWaktu(),
                            items.get(position).getRuang(),
                            items.get(position).getNama(),
                            items.get(position).getKelas(),
                            items.get(position).getJudul(),
                            items.get(position).getPembimbing()
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
        holder.txtPbb.setText(items.get(position).getPembimbing());
        holder.txtSisaWaktu.setText(items.get(position).getSisa_waktu());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView txtTanggal, txtWaktu, txtRuangan, txtNama, txtKelas, txtJudul, txtPbb, txtSisaWaktu;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            txtTanggal = (TextView) itemView.findViewById(R.id.txt_tgl);
            txtWaktu = (TextView) itemView.findViewById(R.id.txt_wkt);
            txtRuangan = (TextView) itemView.findViewById(R.id.txt_rgn);
            txtNama = (TextView) itemView.findViewById(R.id.txt_nma);
            txtKelas = (TextView) itemView.findViewById(R.id.txt_kls);
            txtJudul = (TextView) itemView.findViewById(R.id.txt_jdl);
            txtPbb = (TextView) itemView.findViewById(R.id.txt_pbb);
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
                String pembimbing
        );
    }
}
