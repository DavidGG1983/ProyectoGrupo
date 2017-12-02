package com.proyectogrupo.powerups;

import android.content.Context;
import android.graphics.Canvas;

import com.proyectogrupo.modelos.Modelo;
import com.proyectogrupo.modelos.Nivel;

/**
 * Created by CMUAmerica on 02/12/2017.
 */

public abstract class PowerUp extends Modelo{
    public PowerUp(Context context, double x, double y, int altura, int ancho) {
        super(context, x, y, altura, ancho);
    }

    public abstract void efecto(Nivel nivel);

    @Override
    public void dibujar(Canvas canvas) {
        int yArriba = (int)  y - altura / 2;
        int xIzquierda = (int) x - ancho / 2;

        imagen.setBounds(xIzquierda, yArriba - Nivel.scrollEjeY , xIzquierda
                + ancho, yArriba - Nivel.scrollEjeY + altura);
        imagen.draw(canvas);

    }
}
