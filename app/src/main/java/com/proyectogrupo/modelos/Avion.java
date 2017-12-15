package com.proyectogrupo.modelos;

import android.content.Context;
import android.graphics.Canvas;

import com.proyectogrupo.R;
import com.proyectogrupo.Utils;
import com.proyectogrupo.gestores.CargadorGraficos;
import com.proyectogrupo.graficos.Sprite;
import com.proyectogrupo.modelos.disparos.DisparoAvion;
import com.proyectogrupo.modelos.disparos.DisparoHelicoptero;

public class Avion extends Modelo {

    private long tiempoDisparo;

    public Avion(Context context, double x, double y) {
        super(context, x, y, 30, 48);
        this.y = y - altura / 2;
        inicializar();
    }

    private void inicializar() {
        imagen = CargadorGraficos.cargarDrawable(context, R.drawable.avion);
    }

    public void actualizar(long tiempo) {
        x += Utils.randBetween(0, 1);
        y -= Utils.randBetween(-10, 10);
    }

    public DisparoAvion disparar(long milisegundos) {
        if (milisegundos - tiempoDisparo > 2000 + Math.random() * 2000) {
            tiempoDisparo = System.currentTimeMillis();
            return new DisparoAvion(context, x - ancho / 2 + (0.2 * ancho), y + (altura / 2));
        }
        return null;
    }
}
