package com.malob.hortafire;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;

public class Hortinutri extends AppCompatActivity {
    TextView hortalica, nutrTxt01, nutrTxt02, nutrTxt03;
    TextView nutri1valor, nutri2valor, nutri3valor, valormedido;
    Spinner spiHortalica, spiValorMedido, spinCap;
    String tipoHortalica, ecMedido, capacidade;
    Double capLitros;
    Button btCalc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hortinutri);
        hortalica = findViewById(R.id.id_TxtHortalica);
        nutrTxt01 = findViewById(R.id.Id_Txtnutr1);
        nutrTxt02 = findViewById(R.id.Id_Txtnutr2);
        nutrTxt03 = findViewById(R.id.Id_Txtnutr3);
        nutri1valor = findViewById(R.id.id_TxtValorNutr01);
        nutri2valor = findViewById(R.id.id_TxtValorNutr02);
        nutri3valor = findViewById(R.id.id_TxtValorNutr03);
        valormedido = findViewById(R.id.Id_TxtValorMedido);
        spiHortalica = findViewById(R.id.id_spinnerHortalica);
        spiValorMedido = findViewById(R.id.id_spinnerValorMedido);
        spinCap = findViewById(R.id.id_spinnerCapac);
        btCalc = findViewById(R.id.id_buttonCalcular);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
////
        ArrayAdapter<CharSequence> adapterCap = ArrayAdapter.createFromResource(this, R.array.Capacidade, R.layout.spinner_item);
        adapterCap.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinCap.getBackground().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
        spinCap.setAdapter(adapterCap);
        spinCap.setSelection(3);
        spinCap.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                ((TextView) adapterView.getChildAt(0)).setTextSize(13);

                capacidade = adapterView.getItemAtPosition(position).toString();
                capLitros = Double.parseDouble(capacidade);
                btCalc.setBackgroundColor(getResources().getColor(R.color.red));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Hortalica, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spiHortalica.getBackground().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
        spiHortalica.setAdapter(adapter);
        spiHortalica.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                ((TextView) adapterView.getChildAt(0)).setTextSize(13);
                tipoHortalica = adapterView.getItemAtPosition(position).toString();
                btCalc.setBackgroundColor(getResources().getColor(R.color.red));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.EcMedido, R.layout.spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spiValorMedido.getBackground().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
        spiValorMedido.setAdapter(adapter1);
        spiValorMedido.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                ((TextView) adapterView.getChildAt(0)).setTextSize(13);
                ecMedido = adapterView.getItemAtPosition(position).toString();
                btCalc.setBackgroundColor(getResources().getColor(R.color.error));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void calculaNutrientes(View view) {
        DecimalFormat df = new DecimalFormat("#.##");
        String Ref;
        double ref = 1.7, correcao = 0;
        Double alfaceVetor[] = {44.0, 32.3, 1.2, 0.6};
        Double tomateVector[] = {44.0, 32.4, 1.4, 0.6};
        Double salsaVector[] = {44.1, 35.3, 1.8, 0.9};
        String resulVector[] = {"0.00", "0.00", "0.00"};
        Double fatorAjuste = 1.0;


        if (tipoHortalica.equals("Alface")) {
            ref = 1.5;
            correcao = (ref - Double.parseDouble(ecMedido)) * 10.0;
            fatorAjuste = Double.parseDouble(df.format(correcao));
            for (int i = 0; i <= 2; i++) {
                alfaceVetor[i] = alfaceVetor[i] * fatorAjuste * capLitros / 1000.0;
                resulVector[i] = String.format("%.0f", alfaceVetor[i]);
            }
        } else if (tipoHortalica.equals("Tomate")) {
            ref = 2.5;
            correcao = (ref - Double.parseDouble(ecMedido)) * 10;
            fatorAjuste = Double.parseDouble(df.format(correcao));
            for (int i = 0; i <= 2; i++) {
                tomateVector[i] = tomateVector[i] * fatorAjuste * capLitros / 1000.0;
                resulVector[i] = String.format("%.0f", tomateVector[i]);
            }
        } else {
            ref = 1.7;
            correcao = (ref - Double.parseDouble(ecMedido)) * 10;
            fatorAjuste = Double.parseDouble(df.format(correcao));
            for (int i = 0; i <= 2; i++) {
                salsaVector[i] = salsaVector[i] * fatorAjuste * capLitros / 1000.0;
                resulVector[i] = String.format("%.0f", salsaVector[i]);
            }
        }
        nutri1valor.setText(resulVector[0]);
        nutri2valor.setText(resulVector[1]);
        nutri3valor.setText(resulVector[2]);
        btCalc.setBackgroundColor(getResources().getColor(R.color.calculado));

    }
}