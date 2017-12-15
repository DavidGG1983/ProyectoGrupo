package com.proyectogrupo.powerups;

import android.content.Context;

import com.proyectogrupo.GameView;
import com.proyectogrupo.R;
import com.proyectogrupo.gestores.CargadorGraficos;
import com.proyectogrupo.modelos.Avion;
import com.proyectogrupo.modelos.Nivel;
import com.proyectogrupo.modelos.enemigos.Enemigo;

import java.util.List;

/**
 * Created by Fer on 15/12/2017.
 */

public class CajaAvion extends PowerUp {
    public CajaAvion(Context context, double x, double y) {
        super(context, x, y, 30,30);
        imagen= CargadorGraficos.cargarDrawable(context, R.drawable.caja_dest);
    }

    @Override
    public void efecto(Nivel nivel) {
        nivel.aviones.add(new Avion(context, x, y));
    }
}
