package com.proyectogrupo.modelos.enemigos;

import android.content.Context;

import com.proyectogrupo.modelos.Nivel;
import com.proyectogrupo.modelos.disparos.DisparoEnemigo;
import com.proyectogrupo.modelos.disparos.DisparoEnemigoBasico;
import com.proyectogrupo.modelos.disparos.DisparoEnemigoRalentizador;

/**
 * Created by Daniel on 12/12/2017.
 */

public class EnemigoRalentizador extends Enemigo {

    public EnemigoRalentizador(Context context, double x, double y) {
        super(context, x, y);
    }

    @Override

    public void mover() {

    }

    @Override
    public void inicializar() {

    }

    @Override
    public DisparoEnemigo disparar(long milisegundos) {
        if (milisegundos - tiempoDisparo > 4000 + Math.random() * 4000) {
            tiempoDisparo = System.currentTimeMillis();
            return new DisparoEnemigoRalentizador(context, x, y - Nivel.scrollEjeY, sprite.equals(sprites.get(MOVER_DERECHA)), this);
        }
        return null;
    }
}
