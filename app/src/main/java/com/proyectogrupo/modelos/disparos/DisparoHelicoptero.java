package com.proyectogrupo.modelos.disparos;

import android.content.Context;
import android.graphics.Canvas;

import com.proyectogrupo.R;
import com.proyectogrupo.gestores.CargadorGraficos;
import com.proyectogrupo.modelos.Modelo;
import com.proyectogrupo.modelos.Nivel;
import com.proyectogrupo.modelos.enemigos.Enemigo;

/**
 * Created by Daniel on 15/10/2017.
 */

public class DisparoHelicoptero extends Modelo {

    private double aceleracionY = 2;

    public DisparoHelicoptero(Context context, double x, double y) {
        super(context, x, y, 12, 5);
        imagen = CargadorGraficos.cargarDrawable(context, R.drawable.disparohelicoptero);
    }

    @Override
    public void actualizar(long tiempo) {
        moverAutomaticamente();
    }

    private void moverAutomaticamente() {
        y += aceleracionY;
    }

    @Override
    public void dibujar(Canvas canvas) {
        int yArriba = (int)  y - altura / 2;
        int xIzquierda = (int) x - ancho / 2;

        imagen.setBounds(xIzquierda, yArriba - Nivel.scrollEjeY, xIzquierda
                + ancho, yArriba - Nivel.scrollEjeY + altura);
        imagen.draw(canvas);
    }
}
