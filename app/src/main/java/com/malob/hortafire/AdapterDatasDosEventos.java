package com.malob.hortafire;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterDatasDosEventos extends RecyclerView.Adapter<AdapterDatasDosEventos.MyViewHolder> {

    Context context;
    ArrayList<HortaHidro> hidroArrayList;

    public AdapterDatasDosEventos(Context context, ArrayList<HortaHidro> hidroArrayList) {
        this.context = context;
        this.hidroArrayList = hidroArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.card_datas_dos_eventos, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HortaHidro horta = hidroArrayList.get(position);
        holder.tipoHorta.setText(horta.getHortalica());
        holder.tipoLote.setText(horta.getLote());
        holder.dataS.setText(horta.getDataSemear());
        holder.dataG.setText(horta.getDataGerminar());
        holder.dataB.setText(horta.getDataBerco());
        holder.dataE.setText(horta.getDataEngorda());
    }

    @Override
    public int getItemCount() {
        return hidroArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tipoHorta, tipoLote, dataS, dataG, dataB, dataE;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tipoHorta = itemView.findViewById(R.id.id_tipoH);
            tipoLote = itemView.findViewById(R.id.id_tipoL);
            dataS = itemView.findViewById(R.id.id_dataS);
            dataG = itemView.findViewById(R.id.id_dataG);
            dataB = itemView.findViewById(R.id.id_dataB);
            dataE = itemView.findViewById(R.id.id_dataE);

        }
    }
}

