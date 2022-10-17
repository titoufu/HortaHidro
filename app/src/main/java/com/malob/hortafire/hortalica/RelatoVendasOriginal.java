package com.malob.hortafire.hortalica;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.malob.hortafire.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RelatoVendasOriginal extends AppCompatActivity {

    ImageButton buttonGrafico, buttonVoltar;
    public FirebaseFirestore db;
    public boolean flagGrafico = true;
    public int indexEngorda = 0;
    public final ArrayList<HortaHidro> relGeral = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relato_vendas);
        //
        db = FirebaseFirestore.getInstance();
        recuperaDadosy();
        int n = relGeral.size();
        Log.i("aaaaaa", String.valueOf(n));

    }

    public void recuperaDadosx() {
        HortaHidro h1 = new HortaHidro();
        HortaHidro h2 = new HortaHidro();
        h1.setHortalica("Alface");
        relGeral.add(h1);
        h1.setHortalica("Rúcula");
        relGeral.add(h1);
    }

    public void recuperaDados() {
        String tipoHortalica = "", tipoLote = "";
        String[] Horta = {"Alface", "Rúcula", "Salsa", "Agrião", "Outro"};
        String[] Lote = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        int i, j;
        for (i = 0; i < 5; i++) {
            tipoHortalica = Horta[i];
            for (j = 0; j < 10; j++) {
                tipoLote = Lote[j];
                DocumentReference docRef = db.collection(tipoHortalica).document("Lote: " + tipoLote);
                String finalTipoHortalica = tipoHortalica;
                String finalTipoLote = tipoLote;
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String dataE = document.getString("Data da Engorda");
                                TrataDados(finalTipoHortalica, finalTipoLote, dataE);
                                flagGrafico = true;
                            } else {
                                flagGrafico = false;
                            }
                        } else {
                            Log.d("TAG", "get failed with ", task.getException());
                        }
                    }
                });

            }
        }
    }

    public void recuperaDadosy() {
        String tipoHortalica = "", tipoLote = "";
        String[] Horta = {"Alface", "Rúcula", "Salsa", "Agrião", "Outro"};
        String[] Lote = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        int i, j;
        for (i = 0; i < 5; i++) {
            tipoHortalica = Horta[i];
            for (j = 0; j < 10; j++) {
                tipoLote = Lote[j];
                DocumentReference docRef = db.collection(tipoHortalica).document("Lote: " + tipoLote);
                String finalTipoHortalica = tipoHortalica;
                String finalTipoLote = tipoLote;
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            List<HortaHidro> list = new ArrayList<>();
                            HortaHidro hortalica = new HortaHidro();
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String dataE = document.getString("Data da Engorda");
                                hortalica.setHortalica(finalTipoHortalica);
                                hortalica.setLote(finalTipoLote);
                                hortalica.setDataEngorda(dataE);
                                list.add(hortalica);
                            }
                            processData(list);
                        } else {
                            Log.d("TAG", "get failed with ", task.getException());
                        }
                    }
                });

            }
        }
    }

    void processData(List<HortaHidro> data) {

        if (data.size() == 1) {
            TrataDados(data.get(0).getHortalica(), data.get(0).getLote(), data.get(0).getDataEngorda());
        }
    }

    public void TrataDados(String tipoHortalica, String tipoLote, String dataE) {
        final List<HortaHidro> list = new ArrayList<>();
        HortaHidro hortalica = new HortaHidro();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = sdf.format(new Date());
        String ref = "DD/MM/AAAA";
        long diasNaEngorda = 0;
        long conversao = 24 * 60 * 60 * 1000;
        String data_semeia = ref, data_germina = ref, data_berco = ref, data_engorda = ref;

        if (dataE.equals(ref)) {
            data_engorda = "01/01/3000";
        } else {
            data_engorda = dataE;
        }
        try {
            Date date3 = sdf.parse(data_engorda);
            Date date4 = sdf.parse(currentDate);
            diasNaEngorda = (date4.getTime() - date3.getTime()) / conversao;
        } catch (ParseException e) {
            Log.i("Erro", "FragVendasMain-linha 153");
        }
        if (diasNaEngorda < 0) diasNaEngorda = 0;
        else {
            hortalica.setHortalica(tipoHortalica);
            hortalica.setLote(tipoLote);
            hortalica.setDataEngorda(dataE);
            hortalica.setTempoEngorda((int) diasNaEngorda);
            relGeral.add(hortalica);
            list.add((hortalica));
            Log.i("VENDAS1", "hortaliça: " + hortalica.getHortalica() +
                    "    Lote: " + hortalica.getLote() +
                    "   Dias: " + String.valueOf(hortalica.getTempoEngorda()) +
                    "   Data da engorda: " + hortalica.getDataEngorda()
                    + "    index= " + String.valueOf(indexEngorda));
            int n = relGeral.size();
        }
    }
}