package com.proyectogrupo.powerups;

import android.content.Context;

import com.proyectogrupo.modelos.Modelo;
import com.proyectogrupo.modelos.Nave;

/**
 * Created by CMUAmerica on 02/12/2017.
 */

public class CajaVelocidad extends Modelo implements PowerUp {

    public CajaVelocidad(Context context, double x, double y) {
        super(context, x, y, 0, 0);
    }

    @Override
    public void efecto(Nave nave) {
        nave.setVelocidad(2);
    }
}
