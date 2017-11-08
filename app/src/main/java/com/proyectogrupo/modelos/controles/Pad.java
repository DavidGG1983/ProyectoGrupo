package com.proyectogrupo.modelos.controles;

import android.content.Context;

import com.proyectogrupo.GameView;
import com.proyectogrupo.R;
import com.proyectogrupo.gestores.CargadorGraficos;
import com.proyectogrupo.modelos.Modelo;

/**
 * Created by davidgarcia on 8/11/17.
 */

public class Pad extends Modelo {

    public Pad(Context context) {
        super(context, GameView.pantallaAncho * 0.15, GameView.pantallaAlto * 0.8,
                GameView.pantallaAlto, GameView.pantallaAncho);

        altura = 100;
        ancho = 100;
        imagen = CargadorGraficos.cargarDrawable(context, R.drawable.pad);
    }

    public boolean estaPulsado(float clickX, float clickY) {
        boolean estaPulsado = false;

        if (clickX <= (x + ancho / 2) && clickX >= (x - ancho / 2)
                && clickY <= (y + altura / 2) && clickY >= (y - altura / 2)) {
            estaPulsado = true;
        }
        return estaPulsado;
    }

    public int getOrientacionX(
            float cliclX) {
        return (int) (x - cliclX);
    }

}
