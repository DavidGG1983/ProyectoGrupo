package com.proyectogrupo.powerups;

import android.content.Context;

import com.proyectogrupo.Hilo;
import com.proyectogrupo.R;
import com.proyectogrupo.gestores.CargadorGraficos;
import com.proyectogrupo.modelos.Nave;
import com.proyectogrupo.modelos.Nivel;

public class CajaInvulnerabilidad extends PowerUp {

    public CajaInvulnerabilidad(Context context, double x, double y) {
        super(context, x, y, 30, 30);
        imagen = CargadorGraficos.cargarDrawable(context, R.drawable.invul_icon);
    }

    @Override
    public void efecto(Nivel nivel) {
        final Nave nave = nivel.nave;
        nave.activarInvunerabilidad();
        Runnable action = new Runnable() {
            @Override
            public void run() {
                nave.desactivarInvunerabilidad();
            }
        };
        new Hilo(context, 5000, action).start();
    }
}
