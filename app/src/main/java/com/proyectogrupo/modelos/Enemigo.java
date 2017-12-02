package com.proyectogrupo.modelos;

import android.content.Context;
import android.graphics.Canvas;

import com.proyectogrupo.graficos.Sprite;

import java.util.HashMap;

/**
 * Created by davidgarcia on 2/12/17.
 */

public abstract class Enemigo extends Modelo {

    public double xAnterior;
    public Sprite sprite;
    public HashMap<String,Sprite> sprites;

    public double velocidadX;

    public Enemigo(Context context, double x, double y) {
        super(context, 0, 0, 40, 40);

        this.x = x;
        this.y = y - altura/2;

        this.inicializar();
    }

    public abstract void mover();

    public abstract void inicializar();

    public void dibujar(Canvas canvas){
        this.sprite.dibujarSprite(canvas, (int)x, (int)y - Nivel.scrollEjeY);
    }

    public void actualizar (long tiempo){

    }
}
