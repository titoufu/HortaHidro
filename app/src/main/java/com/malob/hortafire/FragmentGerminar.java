package com.malob.hortafire;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class FragmentGerminar extends Fragment {
    String date = "??/??/????";
    private static String tipoHortalica = "Alface", tipoLote = "A";
    TextView datasemear, datagerminar, databerco, dataengorda;
    public FirebaseFirestore db;
    public HortaHidro hortalica = new HortaHidro();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_germinar, container, false);
        verificaLogin();
        db = FirebaseFirestore.getInstance();

        datasemear = view.findViewById(R.id.id_dataSemear);
        datagerminar = view.findViewById(R.id.id_dataGerminar);
        databerco = view.findViewById(R.id.id_dataBerco);
        dataengorda = view.findViewById(R.id.id_dataEngorda);
        Spinner spinnerHortalica = (Spinner) view.findViewById(R.id.id_spinnerHortalica);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireActivity(), R.array.Hortalica, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHortalica.setAdapter(adapter);
        spinnerHortalica.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                ((TextView) adapterView.getChildAt(0)).setTextSize(13);
                tipoHortalica = adapterView.getItemAtPosition(position).toString();
                recuperaDados(tipoHortalica, tipoLote);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Spinner spinnerLote = (Spinner) view.findViewById(R.id.id_spinnerLote);
        ArrayAdapter<CharSequence> adapterLote = ArrayAdapter.createFromResource(requireActivity(), R.array.Lote, android.R.layout.simple_spinner_item);
        adapterLote.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLote.setAdapter(adapterLote);
        spinnerLote.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                ((TextView) adapterView.getChildAt(0)).setTextSize(13);
                tipoLote = adapterView.getItemAtPosition(position).toString();
                recuperaDados(tipoHortalica, tipoLote);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        DatePicker datePicker = (DatePicker) view.findViewById(R.id.id_datePicker);
        Calendar calendar = Calendar.getInstance();
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        monthOfYear = monthOfYear + 1;
                        date = dayOfMonth + "/" + monthOfYear + "/" + year;
                        datagerminar.setText(date);
                        databerco.setText("DD/MM/AAAA");
                        dataengorda.setText("DD/MM/AAAA");
                    }
                });
        Button botaoSalvar = (Button) view.findViewById(R.id.id_salvar);
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarDados();
            }
        });
        return view;
    }
    private void verificaLogin() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
        }
    }
    private void salvarDados() {

        Map<String, Object> lote = new HashMap<>();
        lote.put("Data da Semeadura", datasemear.getText());
        lote.put("Data da Germinação", datagerminar.getText());
        lote.put("Data do Berçário", databerco.getText());
        lote.put("Data da Engorda", dataengorda.getText());
        db.collection(tipoHortalica).document("Lote: " + tipoLote)
                .set(lote)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), "Dados Salvos com Sucesso", Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Erro ao gravar os dados", Toast.LENGTH_SHORT).show();
                    }
                });

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
                        datasemear.setText(dataS);
                        datagerminar.setText(dataG);
                        databerco.setText(dataB);
                        dataengorda.setText(dataE);
                        Toast.makeText(getContext(), "Dados Recuperados com Sucesso", Toast.LENGTH_SHORT).show();

                        Log.d("TAG", dataS + "  " + dataG + "  " + dataB + "  " + dataE);
                    } else {
                        Toast.makeText(getContext(), "Erro na leitura (Lote não cadastrado)", Toast.LENGTH_SHORT).show();
                        datasemear.setText("DD/MM/AAAA");
                        datagerminar.setText("DD/MM/AAAA");
                        databerco.setText("DD/MM/AAAA");
                        dataengorda.setText("DD/MM/AAAA");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });
    }
}