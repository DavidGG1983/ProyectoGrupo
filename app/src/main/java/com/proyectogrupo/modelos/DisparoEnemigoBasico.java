package com.proyectogrupo.modelos;

import android.content.Context;

import com.proyectogrupo.R;
import com.proyectogrupo.gestores.CargadorGraficos;

/**
 * Created by Daniel on 15/10/2017.
 */

public class DisparoEnemigoBasico extends DisparoEnemigo {

    public DisparoEnemigoBasico(Context context, double x, double y, boolean orientacion) {
        super(context, x, y, orientacion, 5, 20, 26);
        imagen =
                CargadorGraficos.cargarDrawable(context, R.drawable.disparo_enemigo);
    }

    public void moverAutomaticamente() {
        if (orientacion)
            x = x + aceleracionX;
        else
            x = x - aceleracionX;
    }

}
