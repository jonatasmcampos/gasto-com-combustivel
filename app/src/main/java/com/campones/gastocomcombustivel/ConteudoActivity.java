package com.campones.gastocomcombustivel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
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
    private Consumo consumo;
    private DatabaseReference database = ConfiguracaoFirebase.getFirebaseDatabase();
    private DatabaseReference consumoRef;
    private ValueEventListener valueEventListenerConsumos;

    private ImageView imageView, imageViewBackgroundRecycler;
    private TextView textView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conteudo);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Consumo");

        textView = findViewById(R.id.textView2);
        imageView = findViewById(R.id.imageView);
        imageViewBackgroundRecycler = findViewById(R.id.background_recycler_view);

        fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recyclerView);

        adapterConsumo = new AdapterConsumo(consumoList, this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter( adapterConsumo );

        swipe();
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
                if(!(snapshot.getChildrenCount() == 0)){
                    textView.setVisibility(View.GONE);
                    imageView.setVisibility(View.GONE);
                    imageViewBackgroundRecycler.setVisibility(View.VISIBLE);
                } else {
                    textView.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.VISIBLE);
                    imageViewBackgroundRecycler.setVisibility(View.GONE);
                }
                for (DataSnapshot dados : snapshot.getChildren()){
                    Consumo consumo = dados.getValue( Consumo.class );
                    consumo.setKey( dados.getKey() );
                    consumoList.add( consumo );
                }
                adapterConsumo.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void swipe(){
        ItemTouchHelper.Callback itemTouch = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.ACTION_STATE_IDLE;
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                excluirConsumo( viewHolder );
            }
        };
        new ItemTouchHelper( itemTouch ).attachToRecyclerView( recyclerView );
    }

    public void excluirConsumo(RecyclerView.ViewHolder viewHolder){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder( this );
        alertDialog.setTitle("Excluir consumo");
        alertDialog.setMessage("Deseja realmente excluir este consumo?");
        alertDialog.setCancelable(false);

        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(ConteudoActivity.this, "Cancelado", Toast.LENGTH_SHORT).show();
                adapterConsumo.notifyDataSetChanged();
            }
        });
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int position = viewHolder.getAdapterPosition();
                consumo = consumoList.get( position );

                FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
                String usuario = autenticacao.getCurrentUser().getUid();
                consumoRef = database.child("consumos")
                        .child( usuario );

                consumoRef.child( consumo.getKey() ).removeValue();
                adapterConsumo.notifyItemRemoved( position );
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.show();
    }

    public void chamarAdicionarActivity(View v){
        startActivity(new Intent(this, AdicionarConsumoActivity.class));
    }
}