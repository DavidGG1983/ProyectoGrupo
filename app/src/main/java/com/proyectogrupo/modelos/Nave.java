package com.proyectogrupo.modelos;

import android.content.Context;
import android.graphics.Canvas;

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
    double velocidadX;
    double velocidadY;
    public int vida;
    public boolean invulnerable;

    long t1;

    public Nave(Context context, int x, int y) {
        //super(context, x, y, 63, 50);
        super(context, x, y, 32, 40);
        this.y = y - this.altura / 2;
        this.xInicial = x;
        this.yInicial = y;
        this.vida = 3;
        this.invulnerable = false;
        Sprite moviendose = new Sprite(CargadorGraficos.cargarDrawable(context,
                R.drawable.animacion_nave), 50, 63, 4, 4, true);
        sprites = new HashMap<>();
        sprites.put(NAVE_MOVIENDOSE, moviendose);
        this.sprite = moviendose;
    }

    public void actualizar(long tiempo) {
        this.sprite.actualizar(tiempo);

        if(tiempo - t1 > 5000){ // 5 mililisegundos
            invulnerable = false;
        }
    }

    public void dibujar(Canvas canvas) {
        this.sprite.dibujarSprite(canvas, (int) this.x, (int) this.y);
    }

    public void procesarOrdenes(float orientacionPadX, float orientacionPadY) {
        velocidadX = mover(orientacionPadX);
        velocidadY = mover(orientacionPadY);
    }

    private double mover(float orientacionPad) {
        double velocidad;
        if (orientacionPad > 0) {
            velocidad = -5;
        } else if (orientacionPad < 0) {
            velocidad = 5;
        } else {
            velocidad = 0;
        }
        return velocidad;
    }

    public void setVelocidad(int multiplicador) {
        velocidadX *= multiplicador;
        velocidadY *= multiplicador;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public void activarInvunerabilidad(){
        invulnerable = true;
        t1 = System.currentTimeMillis();

    }

    public boolean esInvulnerable(){
        return invulnerable;
    }
}
