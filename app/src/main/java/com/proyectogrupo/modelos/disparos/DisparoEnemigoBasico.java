package com.proyectogrupo.modelos.disparos;

import android.content.Context;

import com.proyectogrupo.R;
import com.proyectogrupo.gestores.CargadorGraficos;
import com.proyectogrupo.modelos.enemigos.Enemigo;

/**
 * Created by Daniel on 15/10/2017.
 */

public class DisparoEnemigoBasico extends DisparoEnemigo {

    public DisparoEnemigoBasico(Context context, double x, double y, boolean orientacion, Enemigo enemigo) {
        super(context, x, y, orientacion, 6, enemigo, 20, 26);
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
