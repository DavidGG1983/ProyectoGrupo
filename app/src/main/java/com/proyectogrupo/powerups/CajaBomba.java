package com.proyectogrupo.powerups;

import android.content.Context;

import com.proyectogrupo.modelos.Modelo;
import com.proyectogrupo.modelos.Nave;

/**
 * Created by CMUAmerica on 02/12/2017.
 */

public class CajaBomba extends Modelo implements PowerUp {
    public CajaBomba(Context context, double x, double y){
        super(context, x,y, 0,0);
        //Falta meter sprite
    }

    @Override
    public void efecto(Nave nave){
        nave.vida--;
    }
}
