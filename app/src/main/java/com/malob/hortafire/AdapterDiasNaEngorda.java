package com.malob.hortafire;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterDiasNaEngorda extends RecyclerView.Adapter<AdapterDiasNaEngorda.ViewHolder> {
    // creating variables for our ArrayList and context
    private ArrayList<HortaHidro> hortaHidroArrayList;

    // creating constructor for our adapter class

    public AdapterDiasNaEngorda(ArrayList<HortaHidro> hortaHidroArrayList) {
        this.hortaHidroArrayList = hortaHidroArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.card_dias_na_engorda, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final HortaHidro hortaHidro = hortaHidroArrayList.get(position);
        holder.tipohortalica.setText(hortaHidro.getHortalica());
        holder.tipoLote.setText(hortaHidro.getLote());
        holder.tempoEngorda.setText(String.valueOf(hortaHidro.getTempoEngorda()));
        holder.imageHortalica.setImageResource(hortaHidro.getImagemHortalica());
      //  Log.i("TAGX",String.valueOf(hortaHidro.getImagemHortalica())+"  position:"+String.valueOf(position));
    }

    @Override
    public int getItemCount() {
        return hortaHidroArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tipohortalica;
        private final TextView tipoLote;
        private final TextView tempoEngorda;
        private final ImageView imageHortalica;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views.
            tipohortalica = (TextView) itemView.findViewById(R.id.id_ValorHorta);
            tipoLote = (TextView) itemView.findViewById(R.id.id_ValorLote);
            tempoEngorda = (TextView) itemView.findViewById(R.id.id_ValorDiasEngorda);
            imageHortalica=(ImageView) itemView.findViewById(R.id.id_image_dias_engorda);
        }
    }
}