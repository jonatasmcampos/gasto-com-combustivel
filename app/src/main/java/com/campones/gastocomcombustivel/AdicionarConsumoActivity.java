package com.campones.gastocomcombustivel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.campones.gastocomcombustivel.model.Consumo;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DecimalFormat;

public class AdicionarConsumoActivity extends AppCompatActivity {

    private TextInputEditText combustivel, precoLitro, km, litros;
    private TextInputLayout tcombustivel, tprecoLitro, tkm, tlitros;

    private TextView textViewkmLitro, textView0, textViewIconeKmLitro;
    private RadioButton radioButtonLitro, radioButtonKmLitro;
    private RadioGroup radioGroup;
    private SeekBar seekBarKmLitro;

    private Consumo consumo;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_consumo);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Adicionar consumo");

        combustivel = findViewById(R.id.etCombustivel);
        precoLitro = findViewById(R.id.etPrecoLitro);
        km = findViewById(R.id.etKM);
        litros = findViewById(R.id.etLitros);
        tcombustivel = findViewById(R.id.editTextCombustivel);
        tprecoLitro = findViewById(R.id.editTextPrecoLitro);
        tkm = findViewById(R.id.editTextKM);
        tlitros = findViewById(R.id.editTextLitros);

        textViewkmLitro = findViewById(R.id.textViewKmLitro);
        textView0 = findViewById(R.id.textView0);
        radioButtonLitro = findViewById(R.id.radioButtonLitro);
        radioButtonKmLitro = findViewById(R.id.radioButtonKmLitro);
        seekBarKmLitro = findViewById(R.id.seekBarKmLitro);
        radioGroup = findViewById(R.id.radioGroup);

        textViewIconeKmLitro = findViewById(R.id.iconeKmLitro);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        iniciarCalculo();
        verificarOpcao();
        verificarSeekbar();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    public void iniciarCalculo(){
        radioButtonLitro.setChecked(true);
        tlitros.setVisibility(View.GONE);
    }

    public void verificarOpcao(){
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if(checkedId == R.id.radioButtonLitro){
                    tlitros.setVisibility(View.GONE);
                    seekBarKmLitro.setVisibility(View.VISIBLE);
                    textViewkmLitro.setVisibility(View.VISIBLE);
                    textView0.setVisibility(View.VISIBLE);
                    textViewIconeKmLitro.setVisibility(View.VISIBLE);
                }else if(checkedId == R.id.radioButtonKmLitro){
                    seekBarKmLitro.setVisibility(View.GONE);
                    textViewkmLitro.setVisibility(View.GONE);
                    textView0.setVisibility(View.GONE);
                    textViewIconeKmLitro.setVisibility(View.GONE);
                    tlitros.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void verificarSeekbar(){
        seekBarKmLitro.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                textViewkmLitro.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void salvarConsumo(String combustivel, double km, double litros, double kmlitros, double custo, double precolitro){
        consumo = new Consumo();
        consumo.setCombustivel(combustivel);
        consumo.setKm(km);
        consumo.setLitros(litros);
        consumo.setKmLitro(kmlitros);
        consumo.setCusto(custo);
        consumo.setPrecoLitro(precolitro);
        consumo.salvar();
    }

    public void salvarConsumo(View v){

        if(!combustivel.getText().toString().isEmpty()){
            if(!precoLitro.getText().toString().isEmpty()){
                String qcombustivel = combustivel.getText().toString();
                double qprecoLitro = Double.parseDouble(precoLitro.getText().toString());
                double qcusto = 0;
                if(!km.getText().toString().isEmpty()){

                    double qkm = Double.parseDouble(km.getText().toString());
                    double qkmLitro = 0;
                    double qlitros = 0;

                    if (radioButtonLitro.isChecked()){
                        qkmLitro = Double.parseDouble(textViewkmLitro.getText().toString());
                        qlitros = qkm/qkmLitro;
                        qcusto = (qprecoLitro * qlitros);
                        DecimalFormat df = new DecimalFormat("0.00");
                        double km = Double.parseDouble(df.format(qkm));
                        double litros = Double.parseDouble(df.format(qlitros));
                        double kmLitro = Double.parseDouble(df.format(qkmLitro));
                        double custo = Double.parseDouble(df.format(qcusto));
                        double precoLitro = Double.parseDouble(df.format(qprecoLitro));
                        salvarConsumo(qcombustivel, km, litros, kmLitro, custo, precoLitro);
                        finish();
                    }
                    if (radioButtonKmLitro.isChecked()) {
                        if(!litros.getText().toString().isEmpty()){
                            qlitros = Double.parseDouble(litros.getText().toString());
                            qkmLitro = qkm/qlitros;
                            qcusto = (qprecoLitro * qlitros);
                            DecimalFormat df = new DecimalFormat("0.00");
                            double km = Double.parseDouble(df.format(qkm));
                            double litros = Double.parseDouble(df.format(qlitros));
                            double kmLitro = Double.parseDouble(df.format(qkmLitro));
                            double custo = Double.parseDouble(df.format(qcusto));
                            double precoLitro = Double.parseDouble(df.format(qprecoLitro));
                            salvarConsumo(qcombustivel, km, litros, kmLitro, custo, precoLitro);
                            finish();
                        }else{
                            tlitros.setErrorEnabled(true);
                            tlitros.setError("Preencha este campo.");
                        }
                    }
                }else{
                    tkm.setErrorEnabled(true);
                    tkm.setError("Preencha este campo.");
                }
            }else{
                tprecoLitro.setErrorEnabled(true);
                tprecoLitro.setError("Preencha este campo.");
            }
        }else{
            tcombustivel.setErrorEnabled(true);
            tcombustivel.setError("Preencha este campo.");
        }
    }
}