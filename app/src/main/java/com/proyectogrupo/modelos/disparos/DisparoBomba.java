package com.proyectogrupo.modelos.disparos;

import android.content.Context;

import com.proyectogrupo.R;
import com.proyectogrupo.gestores.CargadorGraficos;
import com.proyectogrupo.modelos.enemigos.Enemigo;

/**
 * Created by Alumno on 12/12/2017.
 */

public class DisparoBomba extends DisparoEnemigo {
    public static final double RADIO = 20;
    public boolean explotando;

    public DisparoBomba(Context context, double x, double y, boolean orientacion, Enemigo enemigo) {
        super(context, x, y, orientacion, 0, enemigo, 20, 26);
        imagen = CargadorGraficos.cargarDrawable(context, R.drawable.bomba);
        this.explotando = false;
    }

    @Override
    public void moverAutomaticamente() {

    }
}
