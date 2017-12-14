package com.proyectogrupo.modelos.enemigos;

import android.content.Context;

import com.proyectogrupo.R;
import com.proyectogrupo.gestores.CargadorGraficos;
import com.proyectogrupo.graficos.Sprite;
import com.proyectogrupo.modelos.disparos.DisparoEnemigo;
import com.proyectogrupo.modelos.disparos.DisparoEnemigoRalentizador;

/**
 * Created by Daniel on 12/12/2017.
 */

public class EnemigoRalentizador extends Enemigo implements Disparador{

    public EnemigoRalentizador(Context context, double x, double y) {
        super(context, x, y);
    }

    @Override
    public void inicializar() {
        Sprite moverDerecha = new Sprite(CargadorGraficos.cargarDrawable
                (this.context, R.drawable.enemigo_basico_derecha_azul), ancho, altura, 4, 4, true);
        Sprite moverIzquierda = new Sprite(CargadorGraficos.cargarDrawable
                (this.context, R.drawable.enemigo_basico_izquierda_azul), ancho, altura, 4, 4, true);
        sprites.put(MOVER_DERECHA, moverDerecha);
        sprites.put(MOVER_IZQUIERDA, moverIzquierda);
        this.sprite = moverDerecha;
    }

    @Override
    public DisparoEnemigo disparar(long milisegundos) {
        if (milisegundos - tiempoDisparo > (4000 + Math.random() * 4000)) {
            tiempoDisparo = System.currentTimeMillis();
            return new DisparoEnemigoRalentizador(context, x, y, sprite.equals(sprites.get(MOVER_DERECHA)), this);
        }
        return null;
    }
}
