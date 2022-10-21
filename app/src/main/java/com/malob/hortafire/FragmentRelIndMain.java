package com.malob.hortafire;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.malob.hortafire.hortalica.Dados;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class FragmentRelIndMain extends Fragment {
    ImageButton buttonGrafico;
    public static String tipoHortalica = "Alface", tipoLote = "A";
    String data_semear = "1", data_germinar = "2", data_berco = "3", data_engorda = "4";
    HortaHidro dado_lido;
    public FirebaseFirestore db;
    public long diasNaSemeadura = 2, diasNaGerminacao = 7, diasNoBerco = 9, diasNaEngorda = 6;
    public boolean flagGrafico = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rel_ind_main, container, false);

        db = FirebaseFirestore.getInstance();
        buttonGrafico = view.findViewById(R.id.id_ButtonGrafico);
        buttonGrafico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flagGrafico) buttonGrafico.setImageResource(R.drawable.ok);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainerView2, new FragmentRelIndSec()).commit();
            }
        });
        /*buttonVoltar = view.findViewById(R.id.id_ButtonVoltar);
        buttonVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

         */
        Spinner spinnerHortalica = (Spinner) view.findViewById(R.id.id_spinRelHorta);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireActivity(), R.array.Hortalica, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHortalica.setAdapter(adapter);
        buttonGrafico.setBackgroundResource(R.color.red);
        spinnerHortalica.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                ((TextView) adapterView.getChildAt(0)).setTextSize(13);
                tipoHortalica = adapterView.getItemAtPosition(position).toString();
                recuperaDados(tipoHortalica, tipoLote);
                buttonGrafico.setImageResource(R.drawable.grafico_pizza);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Spinner spinnerLote = (Spinner) view.findViewById(R.id.id_spinRelLote);
        ArrayAdapter<CharSequence> adapterLote = ArrayAdapter.createFromResource(requireActivity(), R.array.Lote, android.R.layout.simple_spinner_item);
        adapterLote.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLote.setAdapter(adapterLote);
        buttonGrafico.setBackgroundResource(R.color.red);
        spinnerLote.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                ((TextView) adapterView.getChildAt(0)).setTextSize(13);
                tipoLote = adapterView.getItemAtPosition(position).toString();
                recuperaDados(tipoHortalica, tipoLote);
                buttonGrafico.setImageResource(R.drawable.grafico_pizza);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        return view;
    }

    private void recuperaDados(String tipoHortalica, String tipoLote) {
        DocumentReference docRef = db.collection(tipoHortalica).document("Lote: " + tipoLote);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String dataS = document.getString("Data da Semeadura");
                        String dataG = document.getString("Data da Germinação");
                        String dataB = document.getString("Data do Berçário");
                        String dataE = document.getString("Data da Engorda");
                        HortaHidro hortalica = new HortaHidro(tipoHortalica, tipoLote, dataS, dataG, dataB, dataE, 0,0);
                        trataDados(hortalica);
                        flagGrafico = true;
                        Toast.makeText(getContext(), "Dados Recuperados com Sucesso", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Erro na leitura (Lote não cadastrado)", Toast.LENGTH_SHORT).show();
                        buttonGrafico.setImageResource(R.drawable.error);
                        flagGrafico = false;
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });
    }


    private void trataDados(HortaHidro hortalica) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = sdf.format(new Date());
        String ref = "DD/MM/AAAA";
        long conversao = 24 * 60 * 60 * 1000;
        String data_semeia = ref, data_germina = ref, data_berco = ref, data_engorda = ref;

        if (hortalica.getDataSemear().equals(ref)) {
            data_semeia = "00/00/0000";
        } else {
            data_semeia = hortalica.getDataSemear();
        }
        if (hortalica.getDataGerminar().equals(ref)) {
            data_germina = "00/00/0000";
        } else {
            data_germina = hortalica.getDataGerminar();
        }
        if (hortalica.getDataBerco().equals(ref)) {
            data_berco = "00/00/0000";
        } else {
            data_berco = hortalica.getDataBerco();
        }
        if (hortalica.getDataEngorda().equals(ref)) {
            data_engorda = "00/00/0000";
        } else {
            data_engorda = hortalica.getDataEngorda();
        }

        try {
            Date date0 = sdf.parse(data_semeia);
            Date date1 = sdf.parse(data_germina);
            Date date2 = sdf.parse(data_berco);
            Date date3 = sdf.parse(data_engorda);
            Date date4 = sdf.parse(currentDate);


            diasNaSemeadura = (date1.getTime() - date0.getTime()) / conversao;
            diasNaGerminacao = (date2.getTime() - date1.getTime()) / conversao;
            diasNoBerco = (date3.getTime() - date2.getTime()) / conversao;
            diasNaEngorda = (date4.getTime() - date3.getTime()) / conversao;

            if (diasNaSemeadura < 0) {
                diasNaSemeadura = (date4.getTime() - date0.getTime()) / conversao;
                diasNaEngorda = 0;
            }
            if (diasNaGerminacao < 0) {
                diasNaGerminacao = (date4.getTime() - date1.getTime()) / conversao;
                diasNaEngorda = 0;
            }
            if (diasNoBerco < 0) {
                diasNoBerco = (date4.getTime() - date2.getTime()) / conversao;
                diasNaEngorda = 0;
            }

        } catch (ParseException e) {
            Log.i("VERHORTA", "ERRO");
        }
        Log.i("GRAFICO", "Se= " + String.valueOf(diasNaSemeadura) +
                "  Ge=  " + String.valueOf(diasNaGerminacao) +
                "  Be=  " + String.valueOf(diasNoBerco) +
                "  En=  " + String.valueOf(diasNaEngorda));
        if (diasNaSemeadura < 0) diasNaSemeadura = 0;
        else if (diasNaGerminacao < 0) diasNaGerminacao = 0;
        else if (diasNoBerco < 0) diasNoBerco = 0;
        else if (diasNaEngorda < 0) diasNaEngorda = 0;
        Dados.dataS = (int) diasNaSemeadura;
        Dados.dataB = (int) diasNoBerco;
        Dados.dataE = (int) diasNaEngorda;
        Dados.dataG = (int) diasNaGerminacao;
    }
}