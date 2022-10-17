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

import java.util.ArrayList;

public class DatasDosEventos extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<HortaHidro> hortaArrayList;
    AdapterDatasDosEventos adapterDatasDosEventos;
    FirebaseFirestore db;
    String hortalicaSelecionada = "Alface";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datas_dos_eventos);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Buscando dados ...");
        progressDialog.show();
        recyclerView = findViewById(R.id.id_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        hortaArrayList = new ArrayList<>();

        adapterDatasDosEventos = new AdapterDatasDosEventos(DatasDosEventos.this, hortaArrayList);
        recyclerView.setAdapter(adapterDatasDosEventos);

        EventChangeListenerx("Alface");
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
        hortaArrayList.clear();
        db.collection(hortalicaSelecionada)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                HortaHidro qst = new HortaHidro();
                                qst.setHortalica(hortalicaSelecionada);
                                qst.setLote(document.getId().substring(6, 7));
                                qst.setDataSemear(document.get("Data da Semeadura").toString());
                                qst.setDataGerminar(document.get("Data da Germinação").toString());
                                qst.setDataBerco(document.get("Data do Berçário").toString());
                                qst.setDataEngorda(document.get("Data da Engorda").toString());
                                //  TrataDados(hortalicaSelecionada, document.getId(), document.get("Data da Engorda").toString());
                                //   Log.d("TAG", document.getId() + " => " + qst.toString());
                                hortaArrayList.add(qst);
                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                        recyclerView.setAdapter(adapterDatasDosEventos);
                    }
                });
    }

}