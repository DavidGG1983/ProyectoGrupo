package com.proyectogrupo.modelos.disparos;

import android.content.Context;
import android.graphics.Canvas;

import com.proyectogrupo.modelos.Modelo;
import com.proyectogrupo.modelos.Nivel;
import com.proyectogrupo.modelos.enemigos.Enemigo;

public abstract class DisparoEnemigo extends Modelo {
    public double aceleracionX;
    public boolean orientacion;
    public Enemigo enemigo;
    public int damage;

    public DisparoEnemigo(Context context, double x, double y,
                          boolean orientacion, double aceleracionX, Enemigo enemigo, int altura, int ancho) {
        super(context, x, y, altura, ancho);
        this.aceleracionX = aceleracionX;
        this.orientacion = orientacion;
        this.enemigo = enemigo;
        this.damage = 1;
    }

    public int getDamage() {
        return damage;
    }

    public void dibujar(Canvas canvas) {
        int yArriba = (int) y - altura / 2;
        int xIzquierda = (int) x - ancho / 2;

        imagen.setBounds(xIzquierda, yArriba - Nivel.scrollEjeY, xIzquierda
                + ancho, yArriba - Nivel.scrollEjeY + altura);
        imagen.draw(canvas);
    }

    public abstract void moverAutomaticamente();

}
