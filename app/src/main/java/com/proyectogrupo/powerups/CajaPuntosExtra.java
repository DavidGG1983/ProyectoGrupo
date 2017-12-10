package com.proyectogrupo.powerups;

import android.content.Context;

import com.proyectogrupo.Hilo;
import com.proyectogrupo.R;
import com.proyectogrupo.gestores.CargadorGraficos;
import com.proyectogrupo.modelos.Nave;
import com.proyectogrupo.modelos.Nivel;

/**
 * Created by Daniel on 10/12/2017.
 */

public class CajaPuntosExtra extends PowerUp {

    public CajaPuntosExtra(Context context, double x, double y) {
        super(context, x, y, 33, 34);
        imagen = CargadorGraficos.cargarDrawable(context, R.drawable.points_icon);
    }

    @Override

    public void efecto(Nivel nivel) {
        final Nave nave = nivel.nave;
        nave.multiplicadorPuntos = 2;
        Runnable action = new Runnable() {
            @Override
            public void run() {
                nave.multiplicadorPuntos = 1;
            }
        };

        new Hilo(10000, action);
    }
}
