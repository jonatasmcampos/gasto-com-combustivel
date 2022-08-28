package com.campones.gastocomcombustivel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.campones.gastocomcombustivel.adapter.AdapterConsumo;
import com.campones.gastocomcombustivel.config.ConfiguracaoFirebase;
import com.campones.gastocomcombustivel.model.Consumo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ConteudoActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    private RecyclerView recyclerView;
    private AdapterConsumo adapterConsumo;
    private List<Consumo> consumoList = new ArrayList<>();
    private DatabaseReference database = ConfiguracaoFirebase.getFirebaseDatabase();
    private DatabaseReference consumoRef;
    private ValueEventListener valueEventListenerConsumos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conteudo);

        fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recyclerView);

        adapterConsumo = new AdapterConsumo(consumoList, this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter( adapterConsumo );
    }

    @Override
    protected void onStart() {
        super.onStart();
        recuperarConsumos();
    }

    @Override
    protected void onStop() {
        super.onStop();
        database.removeEventListener(valueEventListenerConsumos);
    }

    public void recuperarConsumos(){
        FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        String usuario = autenticacao.getCurrentUser().getUid();
        consumoRef = database.child("consumos")
                .child( usuario );

        valueEventListenerConsumos = consumoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                consumoList.clear();
                for (DataSnapshot dados : snapshot.getChildren()){
                    Consumo consumo = dados.getValue( Consumo.class );
                    consumoList.add( consumo );
                }
                adapterConsumo.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void chamarAdicionarActivity(View v){
        startActivity(new Intent(this, AdicionarConsumoActivity.class));
    }
}