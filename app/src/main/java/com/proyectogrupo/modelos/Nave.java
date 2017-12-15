package com.proyectogrupo.modelos;

import android.content.Context;
import android.graphics.Canvas;

import com.proyectogrupo.GameView;
import com.proyectogrupo.R;
import com.proyectogrupo.gestores.CargadorGraficos;
import com.proyectogrupo.graficos.Sprite;

import java.util.HashMap;
import java.util.Map;

public class Nave extends Modelo {

    double xInicial, yInicial;
    private static final String NAVE_MOVIENDOSE = "nave_moviendose";
    private static final String NAVE_INVULNERABLE = "nave_invulnerable";
    Map<String, Sprite> sprites;
    Sprite sprite;
    public final double velocidadInicial = 5;
    int shield;
    public  double velocidadNave;
    double velocidadXActual;
    double velocidadYActual;
    int puntos;
    public int multiplicadorPuntos = 1;
    public int vida;
    boolean invulnerable;
    boolean contrataque;
    boolean detenida = false;


    public Nave(Context context, int x, int y) {
        super(context, x, y, 26, 30);
        this.y = y - this.altura / 2;
        this.xInicial = x;
        this.yInicial = this.y;
        this.vida = GameView.VIDAS;
        this.shield = 0;
        this.velocidadNave = velocidadInicial;
        this.invulnerable = false;
        this.contrataque = false;
        Sprite moviendose = new Sprite(CargadorGraficos.cargarDrawable(context,
                R.drawable.animacion_nave), 50, 63, 4, 4, true);
        Sprite invulnerable = new Sprite(CargadorGraficos.cargarDrawable(context,
                R.drawable.animacion_nave_invulnerable), 50, 63, 4, 4, true);
        sprites = new HashMap<>();
        sprites.put(NAVE_MOVIENDOSE, moviendose);
        sprites.put(NAVE_INVULNERABLE, invulnerable);
        this.sprite = moviendose;
    }

    public void actualizar(long tiempo) {
        this.sprite.actualizar(tiempo);
    }

    public void dibujar(Canvas canvas) {
        this.sprite.dibujarSprite(canvas, (int) this.x, (int) this.y - Nivel.scrollEjeY, false);
    }

    public void procesarOrdenes(float orientacionPadX, float orientacionPadY) {
        if (!detenida) {
            velocidadXActual = mover(orientacionPadX);
            velocidadYActual = mover(orientacionPadY);
        }
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

    public void reducirVelocidad(int divisor) {
        velocidadNave /= divisor;
    }

    public void detenerNave() {
        this.velocidadXActual = 0;
        this.velocidadYActual = 0;
        detenida = true;
    }

    public void recuperarVelocidad() {
        velocidadNave = velocidadInicial;
        detenida = false;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public void activarInvunerabilidad() {
        sprite = sprites.get(NAVE_INVULNERABLE);
        invulnerable = true;
    }

    public void desactivarInvunerabilidad() {
        sprite = sprites.get(NAVE_MOVIENDOSE);
        invulnerable = false;
    }

    public void activarContraataque() {
        contrataque = true;
    }

    public void desactivarContraataque() {
        contrataque = false;
    }

    public boolean esInvulnerable() {
        return invulnerable;
    }

    public int getShield() {
        return shield;
    }

    public void decreaseShield(int value) {
        if (value < 0)
            value = 0;
        this.shield -= value;
        if (this.shield == 0)
            desactivarInvunerabilidad();
    }

    public void increaseShield(int value) {
        if (value < 0)
            value = 0;
        this.shield += value;
        if (this.shield > 0)
            activarInvunerabilidad();
    }

    public void sumarPuntos(int puntos) {
        this.puntos += puntos * multiplicadorPuntos;
    }

    public int getPuntos() {
        return puntos;
    }
}
