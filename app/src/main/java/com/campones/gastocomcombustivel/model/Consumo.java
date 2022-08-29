package com.campones.gastocomcombustivel.model;

import com.campones.gastocomcombustivel.config.ConfiguracaoFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

public class Consumo {

    private String combustivel;
    private double km;
    private double litros;
    private double kmLitro;
    private double custo;
    private double precoLitro;
    private String key;

    public Consumo() {
    }

    public void salvar(){
        DatabaseReference database = ConfiguracaoFirebase.getFirebaseDatabase();
        FirebaseAuth autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        String usuario = autenticacao.getCurrentUser().getUid();

        database.child("consumos")
                .child( usuario )
                .push()
                .setValue( this );
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(String combustivel) {
        this.combustivel = combustivel.toUpperCase();
    }

    public double getKm() {
        return km;
    }

    public void setKm(double km) {
        this.km = km;
    }

    public double getLitros() {
        return litros;
    }

    public void setLitros(double litros) {
        this.litros = litros;
    }

    public double getKmLitro() {
        return kmLitro;
    }

    public void setKmLitro(double kmLitro) {
        this.kmLitro = kmLitro;
    }

    public double getCusto() {
        return custo;
    }

    public void setCusto(double custo) {
        this.custo = custo;
    }

    public double getPrecoLitro() {
        return precoLitro;
    }

    public void setPrecoLitro(double precoLitro) {
        this.precoLitro = precoLitro;
    }
}
