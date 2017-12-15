package com.proyectogrupo.modelos.controles;

import android.content.Context;

import com.proyectogrupo.GameView;
import com.proyectogrupo.R;
import com.proyectogrupo.gestores.CargadorGraficos;
import com.proyectogrupo.modelos.Modelo;

public class Pad extends Modelo {

    public static final int EJE_X = 1;
    public static final int EJE_Y = 2;

    public Pad(Context context) {
        super(context, GameView.pantallaAncho * 0.15, GameView.pantallaAlto * 0.9, GameView.pantallaAlto,
                GameView.pantallaAncho);

        altura = 75;
        ancho = 75;
        imagen = CargadorGraficos.cargarDrawable(context, R.drawable.pad);
    }

    public int estaPulsado(float clickX, float clickY) {
        if (clickX <= (x + ancho / 2) &&
                clickX >= (x - ancho / 2) &&
                clickY <= (y + altura / 2) &&
                clickY >= (y - altura / 2)) {
            if (Math.abs(x - clickX) > Math.abs(y - clickY))
                return EJE_X;
            else
                return EJE_Y;
        }
        return 0;
    }

    public int getOrientacionX(
            float cliclX) {
        return (int) (x - cliclX);
    }

    public int getOrientacionY(float clickY) {
        return (int) (y - clickY);
    }
}
