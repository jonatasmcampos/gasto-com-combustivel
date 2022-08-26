package com.campones.gastocomcombustivel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.campones.gastocomcombustivel.config.ConfiguracaoFirebase;
import com.campones.gastocomcombustivel.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        verificarUsuarioLogado();
    }

    public void acessar(View v){
        autenticacao.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if ( task.isSuccessful() ) {
                    usuario = new Usuario();
                    String usuarioID = autenticacao.getCurrentUser().getUid();
                    usuario.setIdUsuario( usuarioID );
                    usuario.salvar();
                    chamarConteudoActivity();
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Erro ao acessar!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void verificarUsuarioLogado(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        if( autenticacao.getCurrentUser() != null ){
            chamarConteudoActivity();
        }
    }

    public void chamarConteudoActivity(){
        startActivity(new Intent(this, ConteudoActivity.class));
    }
}