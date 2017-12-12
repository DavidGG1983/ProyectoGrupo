package com.proyectogrupo.powerups;

import android.content.Context;

import com.proyectogrupo.Hilo;
import com.proyectogrupo.R;
import com.proyectogrupo.gestores.CargadorGraficos;
import com.proyectogrupo.modelos.Nave;
import com.proyectogrupo.modelos.Nivel;

/**
 * Created by Daniel on 11/12/2017.
 */

public class CajaContraEnemigos extends PowerUp {
    public CajaContraEnemigos(Context context, double x, double y) {
        super(context, x, y, 30, 30);
        imagen = CargadorGraficos.cargarDrawable(context, R.mipmap.ic_launcher);
    }

    @Override
    public void efecto(Nivel nivel) {
        final Nave nave = nivel.nave;
        nave.activarContraataque();
        Runnable action = new Runnable() {
            @Override
            public void run() {
                nave.desactivarContraataque();
            }
        };
        new Hilo(5000, action).start();
    }
}
