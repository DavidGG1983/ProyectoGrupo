package com.proyectogrupo.modelos.disparos;

import android.content.Context;

import com.proyectogrupo.GameView;
import com.proyectogrupo.R;
import com.proyectogrupo.gestores.CargadorGraficos;
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
    public void moverAutomaticamente() {

    }
}

