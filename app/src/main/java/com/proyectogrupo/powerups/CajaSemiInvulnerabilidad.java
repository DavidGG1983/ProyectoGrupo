package com.proyectogrupo.powerups;

import android.content.Context;

import com.proyectogrupo.Hilo;
import com.proyectogrupo.R;
import com.proyectogrupo.gestores.CargadorGraficos;
import com.proyectogrupo.modelos.Nave;
import com.proyectogrupo.modelos.Nivel;

public class CajaSemiInvulnerabilidad extends PowerUp {
    public CajaSemiInvulnerabilidad(Context context, double x, double y) {
        super(context, x, y, 32, 32);
        imagen = CargadorGraficos.cargarDrawable(context, R.drawable.half_invul);
    }

    @Override
    public void efecto(Nivel nivel) {
        final Nave nave = nivel.nave;
        nave.increaseShield(1);
        Runnable action = new Runnable() {
            @Override
            public void run() {
                if (nave.getShield() > 0)
                    nave.decreaseShield(nave.getShield());
            }
        };
        new Hilo(5000, action).start();

    }
}
