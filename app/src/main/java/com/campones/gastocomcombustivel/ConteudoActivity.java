package com.campones.gastocomcombustivel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.campones.gastocomcombustivel.config.ConfiguracaoFirebase;
import com.google.firebase.auth.FirebaseAuth;

public class ConteudoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conteudo);
    }
}