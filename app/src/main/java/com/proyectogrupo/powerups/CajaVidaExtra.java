package com.proyectogrupo.powerups;

import android.content.Context;

import com.proyectogrupo.R;
import com.proyectogrupo.gestores.CargadorGraficos;
import com.proyectogrupo.modelos.Nave;
import com.proyectogrupo.modelos.Nivel;

public class CajaVidaExtra extends PowerUp {
    public CajaVidaExtra(Context context, double x, double y) {
        super(context, x, y, 30, 30);
        imagen = CargadorGraficos.cargarDrawable(context, R.drawable.life_icon);
    }

    @Override

    public void efecto(Nivel nivel) {
        Nave nave = nivel.nave;
        if (nave.getVida() < 3)
            nave.setVida(nave.getVida() + 1);
    }
}
