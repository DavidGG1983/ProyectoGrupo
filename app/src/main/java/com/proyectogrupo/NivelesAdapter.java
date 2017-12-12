package com.proyectogrupo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by Fer on 12/12/2017.
 */

public class NivelesAdapter extends RecyclerView.Adapter<NivelesAdapter.NivelViewHolder> {

    interface NivelClickListener {
        void onNivelClick(int numeroNivel);
    }

    private int numNiveles;
    private NivelClickListener listener;

    public NivelesAdapter(int numNiveles, NivelClickListener listener) {
        this.numNiveles = numNiveles;
        this.listener = listener;
    }

    @Override
    public NivelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.nivel_item, parent, false);
        return new NivelViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NivelViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return numNiveles;
    }

    class NivelViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Button btnNivel;

        public NivelViewHolder(View itemView) {
            super(itemView);
            btnNivel = itemView.findViewById(R.id.btnNivel);
            btnNivel.setOnClickListener(this);
        }

        public void bind(int position) {
            btnNivel.setText(String.valueOf(position + 1));
        }

        @Override
        public void onClick(View view) {
            listener.onNivelClick(getAdapterPosition());
        }
    }
}