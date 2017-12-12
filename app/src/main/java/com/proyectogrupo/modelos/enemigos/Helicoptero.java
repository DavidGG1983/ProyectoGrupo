package com.proyectogrupo.modelos.enemigos;

import android.content.Context;
import android.graphics.Canvas;

import com.proyectogrupo.R;
import com.proyectogrupo.Utils;
import com.proyectogrupo.gestores.CargadorGraficos;
import com.proyectogrupo.graficos.Sprite;
import com.proyectogrupo.modelos.Modelo;
import com.proyectogrupo.modelos.Nivel;
import com.proyectogrupo.modelos.disparos.DisparoEnemigo;
import com.proyectogrupo.modelos.disparos.DisparoEnemigoBasico;
import com.proyectogrupo.modelos.disparos.DisparoHelicoptero;

/**
 * Created by davidgarcia on 2/12/17.
 */

public class Helicoptero extends Modelo {

    private Sprite sprite;
    private long tiempoDisparo;

    public Helicoptero(Context context, double x, double y) {
        super(context, x, y, 30, 48);
        inicializar();
    }

    private void inicializar() {
        sprite = new Sprite(CargadorGraficos.cargarDrawable
                (this.context, R.drawable.animhelicoptero), ancho, altura, 4, 4, true);
    }

    @Override
    public void actualizar(long tiempo) {
        sprite.actualizar(tiempo);
        x -= Utils.randBetween(0, 1);
        y += Utils.randBetween(-10, 10);
    }

    @Override
    public void dibujar(Canvas canvas) {
        sprite.dibujarSprite(canvas, (int)x, (int)y - Nivel.scrollEjeY, true);
    }

    public DisparoHelicoptero disparar(long milisegundos) {
        if (milisegundos - tiempoDisparo > 2000 + Math.random() * 2000) {
            tiempoDisparo = System.currentTimeMillis();
            return new DisparoHelicoptero(context, x - ancho / 2 + (0.2 * ancho), y + (altura / 2));
        }
        return null;
    }
}
