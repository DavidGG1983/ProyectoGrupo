package com.proyectogrupo.powerups;

import android.content.Context;

import com.proyectogrupo.R;
import com.proyectogrupo.gestores.CargadorGraficos;
import com.proyectogrupo.modelos.Modelo;
import com.proyectogrupo.modelos.Nave;
import com.proyectogrupo.modelos.Nivel;

/**
 * Created by CMUAmerica on 02/12/2017.
 */

public class CajaBomba extends PowerUp {
    public CajaBomba(Context context, double x, double y){
        super(context, x,y, 30,30);
        imagen = CargadorGraficos.cargarDrawable(context, R.drawable.bomba);
    }

    @Override
    public void efecto(Nivel nivel){
        Nave nave = nivel.nave;
        nave.setVida(nave.getVida() - 1);
    }
}
