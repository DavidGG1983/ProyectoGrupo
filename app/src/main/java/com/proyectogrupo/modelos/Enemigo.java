package com.proyectogrupo.modelos;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.graphics.Palette;

import com.proyectogrupo.graficos.Sprite;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by davidgarcia on 2/12/17.
 */

public abstract class Enemigo extends Modelo {

    public double xAnterior;
    public Sprite sprite;
    public Map<String,Sprite> sprites = new HashMap<>();

    public double velocidadX = 4;
    private double velocidadXCopia = velocidadX;

    public Enemigo(Context context, double x, double y) {
        super(context, 0, 0, 40, 40);

        this.x = x;
        this.y = y - altura/2;

        this.inicializar();
    }

    public void girar(){

    }

    public abstract void mover();

    public abstract void inicializar();

    public void dibujar(Canvas canvas){
        this.sprite.dibujarSprite(canvas, (int)x, (int)y - Nivel.scrollEjeY,true);
    }

    public void actualizar (long tiempo){

    }

    @Override
    public int getColor() {
        return sprite.getDominantColor();
    }

    public void recuperarVelocidad() {
        this.velocidadX = velocidadXCopia;
    }

    public void reducirVelocidad(int i) {
        this.velocidadX -= i;
    }
}
