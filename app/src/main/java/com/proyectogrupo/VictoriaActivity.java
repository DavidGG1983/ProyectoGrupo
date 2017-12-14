package com.proyectogrupo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.proyectogrupo.modelos.Nivel;

public class VictoriaActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_victoria);

        if (Nivel.numeroNivel + 1 >= Utils.getNumNiveles(this)) {
            findViewById(R.id.btnSiguienteNivel).setVisibility(View.GONE);
        }
    }

    public void menuClicked(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void siguienteNivelClicked(View view) {
        Nivel.numeroNivel++;
        startActivity(new Intent(this, NivelActivity.class));
    }
}
