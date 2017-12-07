package com.proyectogrupo.modelos;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;

import com.proyectogrupo.R;
import com.proyectogrupo.gestores.CargadorGraficos;
import com.proyectogrupo.graficos.Sprite;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by davidgarcia on 8/11/17.
 */

public class Nave extends Modelo {

    int xInicial, yInicial;
    private static final String NAVE_MOVIENDOSE = "nave_moviendose";
    Map<String, Sprite> sprites;
    Sprite sprite;
    final double velocidadInicial = 5;
    double velocidadNave;
    double velocidadXActual;
    double velocidadYActual;
    public int vida;
    public boolean invulnerable;


    public Nave(Context context, int x, int y) {
        //super(context, x, y, 63, 50);
        super(context, x, y, 32, 40);
        this.y = y - this.altura / 2;
        this.xInicial = x;
        this.yInicial = y;
        this.vida = 3;
        this.velocidadNave = velocidadInicial;
        this.invulnerable = false;
        Sprite moviendose = new Sprite(CargadorGraficos.cargarDrawable(context,
                R.drawable.animacion_nave), 50, 63, 4, 4, true);
        sprites = new HashMap<>();
        sprites.put(NAVE_MOVIENDOSE, moviendose);
        this.sprite = moviendose;
    }

    public void actualizar(long tiempo) {
        this.sprite.actualizar(tiempo);
    }

    public void dibujar(Canvas canvas) {
        this.sprite.dibujarSprite(canvas, (int) this.x, (int) this.y - Nivel.scrollEjeY);
    }

    public void procesarOrdenes(float orientacionPadX, float orientacionPadY) {
        velocidadXActual = mover(orientacionPadX);
        velocidadYActual = mover(orientacionPadY);
    }

    private double mover(float orientacionPad) {
        double velocidad;
        if (orientacionPad > 0) {
            velocidad = -velocidadNave;
        } else if (orientacionPad < 0) {
            velocidad = velocidadNave;
        } else {
            velocidad = 0;
        }
        return velocidad;
    }

    public void aumentarVelocidad(int multiplicador) {
        velocidadNave *= multiplicador;
    }

    public void recuperarVelocidad() {
        velocidadNave = velocidadInicial;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public void activarInvunerabilidad() {
        invulnerable = true;
    }

    public void desactivarInvunerabilidad() {
        invulnerable = false;
    }

    public boolean esInvulnerable() {
        return invulnerable;
    }
}
