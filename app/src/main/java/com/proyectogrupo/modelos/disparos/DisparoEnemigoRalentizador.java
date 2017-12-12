package com.proyectogrupo.modelos.disparos;

import android.content.Context;

import com.proyectogrupo.modelos.enemigos.Enemigo;

/**
 * Created by Daniel on 12/12/2017.
 */

public class DisparoEnemigoRalentizador extends DisparoEnemigo {
    public DisparoEnemigoRalentizador(Context context, double x, double y, boolean orientacion, Enemigo enemigo) {
        super(context, x, y, orientacion, 6, enemigo, 0, 0);
        imagen = null;
    }

    @Override
    public void moverAutomaticamente() {
        if (orientacion)
            x = x + aceleracionX;
        else
            x = x - aceleracionX;
    }
}
