package com.proyectogrupo.powerups;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.proyectogrupo.Hilo;
import com.proyectogrupo.Utils;
import com.proyectogrupo.modelos.Nivel;

public class CajaColor extends PowerUp {
    private static final int[] colores = {
            Color.parseColor("#C8D0D0"),
            Color.parseColor("#1818C0"),
            Color.parseColor("#C080B0")
    };

    private int color;
    private long tiempoColorGenerado;

    public CajaColor(Context context, double x, double y) {
        super(context, x, y, 30, 30);
        generarColor();
    }

    @Override
    public void efecto(final Nivel nivel) {
        final int colorActual = color;
        nivel.coloresCajas.add(colorActual);
        Runnable action = new Runnable() {
            @Override
            public void run() {
                nivel.coloresCajas.remove(Integer.valueOf(colorActual));
            }
        };
        new Hilo(context, 10000, action).start();
    }

    @Override
    public void dibujar(Canvas canvas) {
        if (System.currentTimeMillis() - tiempoColorGenerado > 3000) {
            generarColor();
            tiempoColorGenerado = System.currentTimeMillis();
        }
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(color);
        canvas.drawRect(new Rect((int) x, (int) y - Nivel.scrollEjeY, (int) (x + ancho), (int) (y - Nivel.scrollEjeY + altura)), paint);
    }

    private void generarColor() {
        color = colores[Utils.randBetween(0, colores.length - 1)];
    }
}
