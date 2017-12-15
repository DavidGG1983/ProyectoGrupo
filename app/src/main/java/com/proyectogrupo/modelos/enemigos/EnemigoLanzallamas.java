package com.proyectogrupo.modelos.enemigos;

import android.content.Context;

import com.proyectogrupo.R;
import com.proyectogrupo.gestores.CargadorGraficos;
import com.proyectogrupo.graficos.Sprite;
import com.proyectogrupo.modelos.disparos.DisparoEnemigo;
import com.proyectogrupo.modelos.disparos.DisparoEnemigoLanzallamas;

public class EnemigoLanzallamas extends Enemigo implements Disparador {

    boolean estaDisparado;

    public EnemigoLanzallamas(Context context, double x, double y) {
        super(context, x, y);
        randomVelocidad();
    }

    @Override
    public void inicializar() {
        Sprite moverDerecha = new Sprite(CargadorGraficos.cargarDrawable
                (this.context, R.drawable.enemigo_basico_derecha_verde), ancho, altura, 4, 4, true);
        Sprite moverIzquierda = new Sprite(CargadorGraficos.cargarDrawable
                (this.context, R.drawable.enemigo_basico_izquierda_verde), ancho, altura, 4, 4, true);
        sprites.put(MOVER_DERECHA, moverDerecha);
        sprites.put(MOVER_IZQUIERDA, moverIzquierda);
        this.sprite = moverDerecha;
        estaDisparado = false;
    }

    @Override
    public DisparoEnemigo disparar(long milisegundos) {
        if (!estaDisparado && milisegundos - tiempoDisparo > 4000 + Math.random() * 4000) {
            tiempoDisparo = System.currentTimeMillis();
            estaDisparado = true;
            return new DisparoEnemigoLanzallamas(context, x, y, sprite.equals(sprites.get(MOVER_DERECHA)), this);
        }
        return null;
    }
}
