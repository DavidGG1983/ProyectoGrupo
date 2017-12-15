package com.proyectogrupo.modelos.enemigos;

import android.content.Context;

import com.proyectogrupo.R;
import com.proyectogrupo.gestores.CargadorGraficos;
import com.proyectogrupo.graficos.Sprite;

public class EnemigoBasico extends Enemigo {

    public EnemigoBasico(Context context, double x, double y) {
        super(context, x, y);
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

        randomVelocidad();
    }

}
