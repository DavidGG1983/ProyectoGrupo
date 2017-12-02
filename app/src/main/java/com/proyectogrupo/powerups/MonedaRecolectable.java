package com.proyectogrupo.powerups;

import android.content.Context;
import android.graphics.Canvas;

import com.proyectogrupo.R;
import com.proyectogrupo.gestores.CargadorGraficos;
import com.proyectogrupo.modelos.Nivel;

/**
 * Created by miguel on 02/12/2017.
 */

public class MonedaRecolectable extends PowerUp {


    public MonedaRecolectable(Context context, double x, double y) {
        super(context, x, y, 40, 40);
        this.y = y - altura/2;
        imagen = CargadorGraficos.cargarDrawable(context, R.drawable.moneda1);

    }

    @Override
    public void efecto(Nivel nivel) {
        nivel.monedasRecogidas += 1;
    }

    @Override
    public void dibujar(Canvas canvas) {

        super.dibujar(canvas);
    }
}
