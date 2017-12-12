package com.proyectogrupo.powerups;

import android.content.Context;

import com.proyectogrupo.Hilo;
import com.proyectogrupo.R;
import com.proyectogrupo.gestores.CargadorGraficos;
import com.proyectogrupo.modelos.enemigos.Enemigo;
import com.proyectogrupo.modelos.Nave;
import com.proyectogrupo.modelos.Nivel;

import java.util.List;

/**
 * Created by Daniel on 05/12/2017.
 */

public class CajaLentitud extends PowerUp {
    public CajaLentitud(Context context, double x, double y) {
        super(context, x, y, 30, 30);
        imagen = CargadorGraficos.cargarDrawable(context, R.drawable.caja_lentitud);
    }

    @Override
    public void efecto(Nivel nivel) {
        final List<Enemigo> enemigos = nivel.enemigos;
        final Nave nave = nivel.nave;
        for (Enemigo e : enemigos)
            e.reducirVelocidad(2);
        nave.reducirVelocidad(2);
        Runnable action = new Runnable() {
            @Override
            public void run() {
                for (Enemigo e : enemigos)
                    e.recuperarVelocidad();
                nave.recuperarVelocidad();
            }
        };
        new Hilo(10000, action).start();
    }
}
