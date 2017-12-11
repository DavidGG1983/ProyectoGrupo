package com.proyectogrupo.modelos;

import android.content.Context;

/**
 * Created by Daniel on 26/09/2017.
 */

public abstract class DisparoEnemigo extends Modelo {
    public double aceleracionX;
    boolean orientacion;

    public DisparoEnemigo(Context context, double x, double y,
                          boolean orientacion,double aceleracionX, int altura, int ancho) {
        super(context, x, y, altura, ancho);
        this.aceleracionX = aceleracionX;
        this.orientacion = orientacion;
    }

    public abstract void moverAutomaticamente();

}
