package com.campones.gastocomcombustivel.model;

import com.campones.gastocomcombustivel.config.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;

public class Usuario {

    private String idUsuario;

    public Usuario() {
    }

    public void salvar(){
        DatabaseReference database = ConfiguracaoFirebase.getFirebaseDatabase();
        database.child("usuarios")
                .child( this.getIdUsuario() )
                .setValue( this.getIdUsuario() );
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
}
