package com.proyectogrupo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.proyectogrupo.modelos.Nivel;

public class DificultadActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_dificultad);
    }

    public void onFacil(View view) {
        dificultadSeleccionada(Dificultad.FACIL);
    }

    public void onMedio(View view) {
        dificultadSeleccionada(Dificultad.MEDIO);
    }

    public void onDificil(View view) {
        dificultadSeleccionada(Dificultad.DIFICIL);
    }

    public void onExtremo(View view) {
        dificultadSeleccionada(Dificultad.EXTREMO);
    }

    private void dificultadSeleccionada(Dificultad dificultad) {
        Nivel.dificultad = dificultad;
        startActivity(new Intent(this, NivelActivity.class));
    }
}
