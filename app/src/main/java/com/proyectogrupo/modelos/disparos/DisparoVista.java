package com.proyectogrupo.modelos.disparos;

import android.content.Context;
import android.graphics.Canvas;

import com.proyectogrupo.GameView;
import com.proyectogrupo.R;
import com.proyectogrupo.gestores.CargadorGraficos;
import com.proyectogrupo.modelos.Modelo;
import com.proyectogrupo.modelos.enemigos.Enemigo;

/**
 * Created by Daniel on 14/12/2017.
 */

public class DisparoVista extends DisparoEnemigo {
    public DisparoVista(Context context, double x, double y, boolean orientacion, Enemigo enemigo) {
        super(context, x, y, orientacion, 6, enemigo, 20, 10);
        imagen = CargadorGraficos.cargarDrawable(context, R.drawable.disparohelicoptero);
    }

    @Override
    public boolean colisiona(Modelo modelo) {
        return modelo.x >= x - ancho / 2 && modelo.x <= x + ancho / 2 && modelo.y >= y - altura / 2
                && modelo.y <= y + altura / 2;
    }

    @Override
    public void moverAutomaticamente() {

    }

    @Override
    public void dibujar(Canvas canvas) {
    }
}

