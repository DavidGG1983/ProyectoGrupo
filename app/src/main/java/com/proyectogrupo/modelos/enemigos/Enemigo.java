package com.proyectogrupo.modelos.enemigos;

import android.content.Context;
import android.graphics.Canvas;

import com.proyectogrupo.GameView;
import com.proyectogrupo.graficos.Sprite;
import com.proyectogrupo.modelos.Modelo;
import com.proyectogrupo.modelos.Nivel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by davidgarcia on 2/12/17.
 */

public abstract class Enemigo extends Modelo {

    public double xAnterior;
    public Sprite sprite;
    public Map<String,Sprite> sprites = new HashMap<>();
    public final static String MOVER_DERECHA = "mover_derecha";
    public final static String MOVER_IZQUIERDA = "mover_izquierda";
    long tiempoDisparo;

    public double velocidadX = 4;
    private double velocidadXCopia = velocidadX;

    public Enemigo(Context context, double x, double y) {
        super(context, 0, 0, 40, 40);

        this.x = x;
        this.y = y - altura/2;

        this.inicializar();
    }

    public void girar() {
        this.velocidadX *= -1;
        if (sprite == sprites.get(MOVER_DERECHA))
            sprite = sprites.get(MOVER_IZQUIERDA);
        else
            sprite = sprites.get(MOVER_DERECHA);
    }

    public void mover() {
        this.xAnterior = x;
        this.x += velocidadX;
        if (x > GameView.pantallaAncho)
            x = 0;
        if (x < 0)
            x = GameView.pantallaAncho;
    }

    public abstract void inicializar();

    public void dibujar(Canvas canvas){
        this.sprite.dibujarSprite(canvas, (int)x, (int)y - Nivel.scrollEjeY,true);
    }


    public void actualizar(long tiempo) {
        sprite.actualizar(tiempo);
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
