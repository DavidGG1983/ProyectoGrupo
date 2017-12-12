package com.proyectogrupo.modelos.disparos;

import android.content.Context;

import com.proyectogrupo.modelos.enemigos.Enemigo;
import com.proyectogrupo.modelos.Modelo;

/**
 * Created by Daniel on 26/09/2017.
 */

public abstract class DisparoEnemigo extends Modelo {
    public double aceleracionX;
    public boolean orientacion;
    public Enemigo enemigo;

    public DisparoEnemigo(Context context, double x, double y,
                          boolean orientacion, double aceleracionX, Enemigo enemigo, int altura, int ancho) {
        super(context, x, y, altura, ancho);
        this.aceleracionX = aceleracionX;
        this.orientacion = orientacion;
        this.enemigo = enemigo;

    }

    public abstract void moverAutomaticamente();

}
