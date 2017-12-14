package com.proyectogrupo.modelos.enemigos;

import android.content.Context;

import com.proyectogrupo.R;
import com.proyectogrupo.gestores.CargadorGraficos;
import com.proyectogrupo.graficos.Sprite;
import com.proyectogrupo.modelos.disparos.DisparoBomba;
import com.proyectogrupo.modelos.disparos.DisparoEnemigo;


/**
 * Created by Alumno on 12/12/2017.
 */

public class EnemigoLanzaBombas extends Enemigo implements Disparador {

    public EnemigoLanzaBombas(Context context, double x, double y) {
        super(context, x, y);
    }

    @Override
    public DisparoEnemigo disparar(long milisegundos) {
        if (milisegundos - tiempoDisparo > 2000 + Math.random() * 2000) {
            tiempoDisparo = System.currentTimeMillis();
            return new DisparoBomba(context, x, y, sprite.equals(sprites.get(MOVER_DERECHA)), this);
        }
        return null;
    }

    @Override
    public void inicializar() {
        Sprite moverDerecha = new Sprite(CargadorGraficos.cargarDrawable
                (this.context, R.drawable.enemigo_basico_derecha_morado), ancho, altura, 4, 4, true);
        Sprite moverIzquierda = new Sprite(CargadorGraficos.cargarDrawable
                (this.context, R.drawable.enemigo_basico_izquierda_morado), ancho, altura, 4, 4, true);
        sprites.put(MOVER_DERECHA, moverDerecha);
        sprites.put(MOVER_IZQUIERDA, moverIzquierda);
        this.sprite = moverDerecha;
    }
}
