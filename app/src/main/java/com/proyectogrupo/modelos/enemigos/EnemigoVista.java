package com.proyectogrupo.modelos.enemigos;

import android.content.Context;

import com.proyectogrupo.GameView;
import com.proyectogrupo.R;
import com.proyectogrupo.gestores.CargadorGraficos;
import com.proyectogrupo.graficos.Sprite;
import com.proyectogrupo.modelos.disparos.DisparoEnemigo;
import com.proyectogrupo.modelos.disparos.DisparoVista;

/**
 * Created by Daniel on 14/12/2017.
 */

public class EnemigoVista extends Enemigo implements Disparador {
    long tiempoGiro;
    public DisparoEnemigo actual;


    public EnemigoVista(Context context, double x, double y) {
        super(context, x, y);

    }

    @Override
    public DisparoEnemigo disparar(long milisegundos) {
        if (actual == null) {
            actual = new DisparoVista(context, x, y, sprite.equals(sprites.get(MOVER_DERECHA)), this);
            return actual;
        }
        return null;
    }

    public void cambiarLadoDisparo() {
        if (sprite.equals(sprites.get(MOVER_DERECHA))) {
            actual.x = GameView.pantallaAncho;
            actual.ancho = (int) ((actual.x - x) * 2) - ancho;
        } else {
            actual.x = 0;
            actual.ancho = (int) (x * 2) - ancho;
        }
    }

    @Override
    public void mover() {
        if (System.currentTimeMillis() - tiempoGiro > 3000) {
            tiempoGiro = System.currentTimeMillis();
            girar();
        }
    }

    @Override
    public void inicializar() {
        Sprite moverDerecha = new Sprite(CargadorGraficos.cargarDrawable
                (this.context, R.drawable.enemigo_basico_derecha), ancho, altura, 4, 4, true);
        Sprite moverIzquierda = new Sprite(CargadorGraficos.cargarDrawable
                (this.context, R.drawable.enemigo_basico_izquierda), ancho, altura, 4, 4, true);
        sprites.put(MOVER_DERECHA, moverDerecha);
        sprites.put(MOVER_IZQUIERDA, moverIzquierda);
        this.sprite = moverDerecha;
    }


}
