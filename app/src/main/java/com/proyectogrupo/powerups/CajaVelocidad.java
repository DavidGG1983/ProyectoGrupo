package com.proyectogrupo.powerups;

import android.content.Context;

import com.proyectogrupo.Hilo;
import com.proyectogrupo.R;
import com.proyectogrupo.gestores.CargadorGraficos;
import com.proyectogrupo.modelos.Nave;
import com.proyectogrupo.modelos.Nivel;

public class CajaVelocidad extends PowerUp {

    public CajaVelocidad(Context context, double x, double y) {
        super(context, x, y, 30, 30);
        imagen = CargadorGraficos.cargarDrawable(context, R.drawable.speed_icon);
    }

    @Override
    public void efecto(Nivel nivel) {
        final Nave nave = nivel.nave;
        if (nave.velocidadNave <= nave.velocidadInicial) {
            nave.aumentarVelocidad(2);
            Runnable action = new Runnable() {
                @Override
                public void run() {
                    nave.recuperarVelocidad();
                }
            };
            new Hilo(context, 5000, action).start();
        }
    }
}
