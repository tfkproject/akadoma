package ta.syifaul.akadoma.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ta.syifaul.akadoma.AgendaDetailActivity;
import ta.syifaul.akadoma.PengumumanDetailActivity;
import ta.syifaul.akadoma.R;
import ta.syifaul.akadoma.model.ItemAgenda;
import ta.syifaul.akadoma.model.ItemPengumuman;

/**
 * Created by user on 15/08/18.
 */

public class AgendaAdapter extends RecyclerView.Adapter<AgendaAdapter.ViewHolder> {

    List<ItemAgenda> items;
    Context context;

    public AgendaAdapter(Context context, List<ItemAgenda> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public AgendaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_agenda, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AgendaDetailActivity.class);
                intent.putExtra("key_id_agenda", items.get(position).getId_agenda());
                intent.putExtra("key_judul", items.get(position).getJudul());
                intent.putExtra("key_tgl", items.get(position).getTanggal());
                intent.putExtra("key_wkt", items.get(position).getWaktu());
                intent.putExtra("key_ket", items.get(position).getKeterangan());
                context.startActivity(intent);
            }
        });
        holder.txtJudul.setText(items.get(position).getJudul());
        holder.txtTanggal.setText(items.get(position).getTanggal());
        holder.txtWaktu.setText(items.get(position).getWaktu());
        holder.txtSisaWaktu.setText(items.get(position).getSisa_waktu());
    }
    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView txtJudul, txtTanggal, txtWaktu, txtNama, txtSisaWaktu;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            txtJudul = (TextView) itemView.findViewById(R.id.txt_judul);
            txtTanggal = (TextView) itemView.findViewById(R.id.txt_tgl);
            txtWaktu = (TextView) itemView.findViewById(R.id.txt_waktu);
            txtSisaWaktu = (TextView) itemView.findViewById(R.id.txt_sisa_waktu);
        }
    }
}
