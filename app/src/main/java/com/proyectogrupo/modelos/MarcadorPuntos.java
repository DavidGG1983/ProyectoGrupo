package com.proyectogrupo.modelos;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.proyectogrupo.R;
import com.proyectogrupo.gestores.CargadorGraficos;


/**
 * Created by jordansoy on 10/10/2017.
 */

public class MarcadorPuntos extends Modelo {

    private Paint paint;
    public int puntos;

    public MarcadorPuntos(Context context, double x, double y) {
        super(context, x, y, 60,60);
        paint = new Paint();
            paint.setTextAlign(Paint.Align.RIGHT);
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setTextSize(30);
    }

    @Override
    public void dibujar(Canvas canvas) {
        canvas.drawText(String.valueOf(puntos), (float)x, (float)y, paint);
    }
}

