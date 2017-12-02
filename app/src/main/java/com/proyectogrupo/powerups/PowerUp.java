package com.proyectogrupo.powerups;

import android.content.Context;

import com.proyectogrupo.modelos.Modelo;
import com.proyectogrupo.modelos.Nave;
import com.proyectogrupo.modelos.Nivel;

/**
 * Created by CMUAmerica on 02/12/2017.
 */

public abstract class PowerUp extends Modelo{
    public PowerUp(Context context, double x, double y, int altura, int ancho) {
        super(context, x, y, altura, ancho);
    }

    public abstract void efecto(Nivel nivel);
}
