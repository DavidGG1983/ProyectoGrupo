package com.proyectogrupo.modelos;

import android.content.Context;
import android.graphics.Canvas;

import com.proyectogrupo.graficos.Sprite;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by davidgarcia on 2/12/17.
 */

public abstract class Enemigo extends Modelo {

    public double xAnterior;
    public Sprite sprite;
    public Map<String, Sprite> sprites = new HashMap<>();

    public double velocidadX;
    public double velocidadY;
    public final double velocidadInicialX;
    public final double velocidadInicialY;

    public Enemigo(Context context, double x, double y, double velocidadX, double velocidadY) {
        super(context, 0, 0, 40, 40);

        this.x = x;
        this.y = y - altura / 2;


        this.velocidadInicialX = velocidadX;
        this.velocidadX = velocidadX;
        this.velocidadInicialY = velocidadY;
        this.velocidadY = velocidadY;

        this.inicializar();
    }

    public abstract void girar();

    public abstract void mover();

    public abstract void inicializar();

    public void dibujar(Canvas canvas) {
        this.sprite.dibujarSprite(canvas, (int) x, (int) y - Nivel.scrollEjeY);
    }

    public abstract void actualizar(long tiempo);

    public void reducirVelocidad(int divisor) {
        velocidadX /= divisor;
        velocidadY /= divisor;
    }

    public void aumentarVelocidad(int multiplicador) {
        velocidadX *= multiplicador;
        velocidadY *= multiplicador;
    }

    public void recuperarVelocidad() {
        velocidadX = velocidadInicialX;
        velocidadY = velocidadInicialY;
    }
}
