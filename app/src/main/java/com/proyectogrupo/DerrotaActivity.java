package com.proyectogrupo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DerrotaActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_derrota);
    }

    public void menuClicked(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void volverJugarClicked(View view) {
        startActivity(new Intent(this, NivelActivity.class));
    }
}
