package com.malob.hortafire;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.malob.hortafire.hortalica.HortaHidro;

import java.util.ArrayList;

public class RelatoAdapter extends RecyclerView.Adapter<RelatoAdapter.ViewHolder> {
    // creating variables for our ArrayList and context
    private ArrayList<HortaHidro> hortaHidroArrayList;

    // creating constructor for our adapter class

    public RelatoAdapter(ArrayList<HortaHidro> hortaHidroArrayList) {
        this.hortaHidroArrayList = hortaHidroArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.card_relato_geral, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final HortaHidro hortaHidro = hortaHidroArrayList.get(position);
        holder.tipohortalica.setText(hortaHidro.getHortalica());
        holder.tipoLote.setText(hortaHidro.getLote());
        holder.tempoEngorda.setText(String.valueOf(hortaHidro.getTempoEngorda()));
        Log.i("TAG",hortaHidro.getHortalica()+"  position:"+String.valueOf(position));
    }

    @Override
    public int getItemCount() {
        return hortaHidroArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tipohortalica;
        private final TextView tipoLote;
        private final TextView tempoEngorda;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views.
            tipohortalica = (TextView) itemView.findViewById(R.id.id_ValorHorta);
            tipoLote = (TextView) itemView.findViewById(R.id.id_ValorLote);
            tempoEngorda = (TextView) itemView.findViewById(R.id.id_ValorDiasEngorda);
        }
    }
}