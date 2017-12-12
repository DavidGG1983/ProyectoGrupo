package com.proyectogrupo.modelos;

import android.content.Context;

/**
 * Created by Daniel on 26/09/2017.
 */

public abstract class DisparoEnemigo extends Modelo {
    public double aceleracionX;
    boolean orientacion;
    Enemigo enemigo;

    public DisparoEnemigo(Context context, double x, double y,
                          boolean orientacion, double aceleracionX, Enemigo enemigo, int altura, int ancho) {
        super(context, x, y, altura, ancho);
        this.aceleracionX = aceleracionX;
        this.orientacion = orientacion;
        this.enemigo = enemigo;

    }

    public abstract void moverAutomaticamente();

}
