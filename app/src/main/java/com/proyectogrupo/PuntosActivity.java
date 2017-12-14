package com.proyectogrupo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PuntosActivity extends Activity {

    public static final String EXTRA_PUNTOS = "extra_puntos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntos);

        int puntos = getIntent().getIntExtra(EXTRA_PUNTOS, -1);

        if (Utils.record(this, puntos)) {
            findViewById(R.id.tvRecord).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.tvRecord).setVisibility(View.INVISIBLE);
        }
    }

    public void volverJugarClicked(View view) {
        startActivity(new Intent(this, NivelActivity.class));
    }

    public void menuClicked(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}
