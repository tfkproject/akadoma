package ta.syifaul.akadoma.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import ta.syifaul.akadoma.InputJadwalSeminarKpActivity;
import ta.syifaul.akadoma.InputJadwalSeminarTaActivity;
import ta.syifaul.akadoma.InputJadwalSidangTaActivity;
import ta.syifaul.akadoma.JadwalSeminarKpActivity;
import ta.syifaul.akadoma.JadwalSeminarTaActivity;
import ta.syifaul.akadoma.JadwalSidangTaActivity;
import ta.syifaul.akadoma.R;

/**
 * Created by taufik on 17/04/18.
 */

public class JadwalFragment extends Fragment {

    LinearLayout btnSmKp, btnSmTa, btnSdTa;

    FloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_ly_jadwal, container, false);

        final String level_ = getActivity().getIntent().getStringExtra("level");

        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        if(level_.contains("mhs")){
            fab.setVisibility(View.GONE);
        }
        if(level_.contains("dsn")){
            fab.setVisibility(View.GONE);
        }
        if(level_.contains("koorta")){
            fab.setVisibility(View.VISIBLE);
        }
        if(level_.contains("koorkp")){
            fab.setVisibility(View.VISIBLE);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(level_.contains("koorta")){
                    tampilkanOpsiTA();
                }
                if(level_.contains("koorkp")){
                    tampilkanOpsiKP();
                }
            }
        });

        btnSmKp = (LinearLayout) rootView.findViewById(R.id.btn_jdwl_smkp);
        btnSmTa = (LinearLayout) rootView.findViewById(R.id.btn_jdwl_smta);
        btnSdTa = (LinearLayout) rootView.findViewById(R.id.btn_jdwl_sdta);

        btnSmKp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), JadwalSeminarKpActivity.class);
                startActivity(intent);
            }
        });

        btnSmTa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), JadwalSeminarTaActivity.class);
                startActivity(intent);
            }
        });

        btnSdTa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), JadwalSidangTaActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    private void tampilkanOpsiTA(){
        final CharSequence[] dialogitem = {"Jadwal Seminar TA", "Jadwal Sidang TA"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Pilihan Input");
        builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch(item){
                    case 0 :
                        startActivity(new Intent(getActivity(), InputJadwalSeminarTaActivity.class));
                        break;
                    case 1 :
                        startActivity(new Intent(getActivity(), InputJadwalSidangTaActivity.class));
                        break;
                }
            }
        });
        builder.create().show();
    }

    private void tampilkanOpsiKP(){
        final CharSequence[] dialogitem = {"Jadwal Seminar KP"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Pilihan Input");
        builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                switch(item){
                    case 0 :
                        startActivity(new Intent(getActivity(), InputJadwalSeminarKpActivity.class));
                        break;
                }
            }
        });
        builder.create().show();
    }

}
