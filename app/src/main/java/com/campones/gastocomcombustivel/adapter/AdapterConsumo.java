package com.campones.gastocomcombustivel.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.campones.gastocomcombustivel.R;
import com.campones.gastocomcombustivel.model.Consumo;

import java.util.ArrayList;
import java.util.List;

public class AdapterConsumo extends RecyclerView.Adapter<AdapterConsumo.MyViewHolder> {

    List<Consumo> consumos;
    Context context;

    public AdapterConsumo(List<Consumo> consumos, Context context) {
        this.consumos = consumos;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_consumo, parent, false);
        return new MyViewHolder(itemLista);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Consumo consumo = consumos.get(position);
        holder.combustivel.setText(consumo.getCombustivel());
        holder.km.setText(consumo.getKm() + " km");
        holder.litros.setText(consumo.getLitros() + " litros");
        holder.kmLitro.setText(consumo.getKmLitro() + " km/l");
        holder.custo.setText( "R$ " + consumo.getCusto());
        holder.precoLitro.setText("R$ " + consumo.getPrecoLitro() + "/litro");
    }


    @Override
    public int getItemCount() {
        return consumos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView combustivel, km, litros, kmLitro, custo, precoLitro;

        public MyViewHolder(View itemView) {
            super(itemView);

            combustivel = itemView.findViewById(R.id.tvCombustivel);
            km = itemView.findViewById(R.id.tvKM);
            litros = itemView.findViewById(R.id.tvLitros);
            kmLitro = itemView.findViewById(R.id.tvKmLitro);
            custo = itemView.findViewById(R.id.tvCusto);
            precoLitro = itemView.findViewById(R.id.tvPrecoLitro);
        }

    }

}
