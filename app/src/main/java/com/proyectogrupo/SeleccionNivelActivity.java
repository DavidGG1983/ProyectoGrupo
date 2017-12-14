package com.proyectogrupo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.proyectogrupo.modelos.Nivel;

import java.io.IOException;

public class SeleccionNivelActivity extends Activity implements NivelesAdapter.NivelClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_seleccion_nivel);

        int numNiveles = Utils.getNumNiveles(this);
        RecyclerView rvNiveles = findViewById(R.id.rvNiveles);
        rvNiveles.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvNiveles.setAdapter(new NivelesAdapter(numNiveles, this));
    }

    @Override
    public void onNivelClick(int numeroNivel) {
        Nivel.numeroNivel = numeroNivel;
        startActivity(new Intent(this, DificultadActivity.class));
    }
}
