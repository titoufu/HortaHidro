package com.malob.hortafire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class DiasNaEngorda extends AppCompatActivity {
    ArrayList<HortaHidro> hortaHidroArrayList;
    RecyclerView recyclerView;
    AdapterDiasNaEngorda relatoAdapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog;
    String hortalicaSelecionada = "Alface";
    HortaHidro hortaHidro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dias_na_engorda);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Buscando dados ...");
        progressDialog.show();

        db = FirebaseFirestore.getInstance();
        // escolhendo o item selecionado no menu

        recyclerView = findViewById(R.id.relato_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        hortaHidroArrayList = new ArrayList<HortaHidro>();
        relatoAdapter = new AdapterDiasNaEngorda(hortaHidroArrayList);
        recyclerView.setAdapter(relatoAdapter);
        EventChangeListenerx(hortalicaSelecionada);
        if (progressDialog.isShowing()) progressDialog.dismiss();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // infla o menu, adicionando itens ao toolbar
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_menu_alface:
                hortalicaSelecionada = "Alface";
                EventChangeListenerx(hortalicaSelecionada);
                break;
            case R.id.id_menu_agrião:
                hortalicaSelecionada = "Agrião";
                EventChangeListenerx(hortalicaSelecionada);
                break;
            case R.id.id_menu_salsa:
                hortalicaSelecionada = "Salsa";
                EventChangeListenerx(hortalicaSelecionada);
                break;
            case R.id.id_menu_rucula:
                hortalicaSelecionada = "Rúcula";
                EventChangeListenerx(hortalicaSelecionada);
                break;
            case R.id.id_menu_outros:
                hortalicaSelecionada = "Outra";
                EventChangeListenerx(hortalicaSelecionada);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void EventChangeListenerx(String hortalicaSelecionada) {
        hortaHidroArrayList.clear();
        db.collection(hortalicaSelecionada)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                TrataDados(hortalicaSelecionada, document.getId(), document.get("Data da Engorda").toString());
                                //Log.d("TAG", document.getId() + " => " + document.get("Data da Engorda"));
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                        recyclerView.setAdapter(relatoAdapter);
                    }

                    private void TrataDados(String tipoHortalica, String tipoLote, String dataE) {
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
                            Log.i("Erro", "FragVendasMain-linha 120");
                        }
                        if (diasNaEngorda < 0) diasNaEngorda = 0;
                        else {
                            hortalica.setHortalica(tipoHortalica);
                            hortalica.setLote(tipoLote.substring(6, 7));
                            hortalica.setDataEngorda(dataE);
                            hortalica.setTempoEngorda((int) diasNaEngorda);

                            if (hortalicaSelecionada.equals("Alface"))
                                hortalica.setImagemHortalica(R.drawable.im_alface);
                            else if (hortalicaSelecionada.equals("Agrião"))
                                hortalica.setImagemHortalica(R.drawable.im_agriao);
                            else if (hortalicaSelecionada.equals("Salsa"))
                                hortalica.setImagemHortalica(R.drawable.im_salsa);
                            else if (hortalicaSelecionada.equals("Outra"))
                                hortalica.setImagemHortalica(R.drawable.im_couve);
                            else if (hortalicaSelecionada.equals("Rúcula"))
                                hortalica.setImagemHortalica(R.drawable.im_rucula);
                            hortaHidroArrayList.add(hortalica);
                        }
                    }
                });
    }

}