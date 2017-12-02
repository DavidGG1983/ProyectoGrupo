package com.proyectogrupo.powerups;

import android.content.Context;

import com.proyectogrupo.R;
import com.proyectogrupo.gestores.CargadorGraficos;
import com.proyectogrupo.modelos.Modelo;
import com.proyectogrupo.modelos.Nivel;

/**
 * Created by miguel on 02/12/2017.
 */

public class MonedaRecolectable extends Modelo implements PowerUp {


    public MonedaRecolectable(Context context, double x, double y, int altura, int ancho) {
        super(context, x, y, altura, ancho);
        imagen = CargadorGraficos.cargarDrawable(context, R.drawable.moneda1);

    }

    @Override
    public void efecto(Nivel nivel) {
        nivel.monedasRecogidas += 1;
    }

}
