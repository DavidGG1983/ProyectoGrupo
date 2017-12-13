package com.proyectogrupo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.proyectogrupo.modelos.Nivel;

public class MainActivity extends Activity {
    GameView gameView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
    }

    public void infinitoClicked(View view) {
        Nivel.infinito = true;
        startActivity(new Intent(this, DificultadActivity.class));
    }

    public void nivelesClicked(View view) {
        Nivel.infinito = false;
        seleccionNivel();
    }

    private void seleccionNivel() {
        startActivity(new Intent(this, SeleccionNivelActivity.class));
    }
}