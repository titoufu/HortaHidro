package com.malob.hortafire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText edit_email, edit_senha;
    private Button bt_entrar;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        IniciarComponetes();
        bt_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edit_email.getText().toString();
                String senha = edit_senha.getText().toString();
                if (email.isEmpty() || senha.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Campos de Email ou senha vazios !", Toast.LENGTH_SHORT).show();
                } else {
                    AutenticarUsuario();
                }
            }
        });
    }

    private void AutenticarUsuario() {
        String email = edit_email.getText().toString();
        String senha = edit_senha.getText().toString();
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Sucesso ao entrar !", Toast.LENGTH_SHORT).show();
                    TelaPrincipal() ;
                } else {
                    Toast.makeText(getApplicationContext(), "Erro ao acessar !", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    private void TelaPrincipal() {
        finish();
    }

    private void IniciarComponetes() {
        edit_email = findViewById(R.id.id_editTxt_email);
        edit_senha = findViewById(R.id.id_editTxt_senha);
        bt_entrar = findViewById(R.id.id_bt_entrar);
    }
}