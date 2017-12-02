package com.proyectogrupo.powerups;

import android.content.Context;

import com.proyectogrupo.Hilo;
import com.proyectogrupo.modelos.Modelo;
import com.proyectogrupo.modelos.Nave;
import com.proyectogrupo.modelos.Nivel;

/**
 * Created by CMUAmerica on 02/12/2017.
 */

public class CajaInvulnerabilidad extends PowerUp {

    public CajaInvulnerabilidad(Context context, double x, double y) {
        super(context, x, y, 0, 0);
        //Falta meter sprite
    }

    @Override
    public void efecto(Nivel nivel) {
        final Nave nave = nivel.nave;
        nave.invulnerable = true;
        Runnable action = new Runnable() {
            @Override
            public void run() {
                nave.desactivarInvunerabilidad();
            }
        };
        new Hilo(5000, action).start();
    }
}
