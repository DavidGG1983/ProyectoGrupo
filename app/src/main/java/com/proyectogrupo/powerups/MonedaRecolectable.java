package com.proyectogrupo.powerups;

import android.content.Context;
import android.graphics.Canvas;

import com.proyectogrupo.R;
import com.proyectogrupo.gestores.CargadorGraficos;
import com.proyectogrupo.graficos.Sprite;
import com.proyectogrupo.modelos.Nivel;

public class MonedaRecolectable extends PowerUp {

    private Sprite sprite;

    public MonedaRecolectable(Context context, double x, double y) {
        super(context, x, y, 25, 25);
        this.y = y - altura / 2;
        sprite = new Sprite(CargadorGraficos.cargarDrawable
                (context, R.drawable.animacion_moneda), ancho, altura,
                4, 14, true);
    }

    @Override
    public void efecto(Nivel nivel) {
        // asi se nota la suma de puntos respecto al ascender por la pantalla
        nivel.nave.sumarPuntos(10);
    }

    public void dibujar(Canvas canvas) {
        sprite.dibujarSprite(canvas, (int) x, (int) y - Nivel.scrollEjeY, true);
    }

    public void actualizar(long tiempo) {
        sprite.actualizar(tiempo);
    }
}
