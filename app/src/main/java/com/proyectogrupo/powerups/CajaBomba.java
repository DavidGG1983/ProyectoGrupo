package com.proyectogrupo.powerups;

import android.content.Context;

import com.proyectogrupo.R;
import com.proyectogrupo.gestores.CargadorGraficos;
import com.proyectogrupo.modelos.Nave;
import com.proyectogrupo.modelos.Nivel;

public class CajaBomba extends PowerUp {
    public CajaBomba(Context context, double x, double y) {
        super(context, x, y, 30, 30);
        this.y = y - altura / 2;
        imagen = CargadorGraficos.cargarDrawable(context, R.drawable.bomba);
    }

    @Override
    public void efecto(Nivel nivel) {
        Nave nave = nivel.nave;
        nave.setVida(nave.getVida() - 1);
    }
}
