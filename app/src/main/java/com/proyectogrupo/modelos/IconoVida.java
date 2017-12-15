package com.proyectogrupo.modelos;

import android.content.Context;

import com.proyectogrupo.R;
import com.proyectogrupo.gestores.CargadorGraficos;


/**
 * Created by jordansoy on 10/10/2017.
 */

public class IconoVida extends Modelo {

    public IconoVida(Context context, double x, double y) {
        super(context, x, y, 60, 60);
        imagen = CargadorGraficos.cargarDrawable(context, R.drawable.icono_vidas);
    }
}

