package com.proyectogrupo.modelos.disparos;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.proyectogrupo.R;
import com.proyectogrupo.gestores.CargadorGraficos;
import com.proyectogrupo.modelos.enemigos.Enemigo;

/**
 * Created by miguel on 12/12/2017.
 */

public class DisparoEnemigoLanzallamas extends DisparoEnemigo {


    private final Drawable imagenDerecha;
    private final Drawable imagenIzquierda;

    public DisparoEnemigoLanzallamas(Context context, double x, double y, boolean orientacion, Enemigo enemigo) {
        super(context, x, y, orientacion, 6, enemigo, 20, 26);
        imagenDerecha = CargadorGraficos.cargarDrawable(context, R.drawable.disparo_flame_derecha);
        imagenIzquierda = CargadorGraficos.cargarDrawable(context, R.drawable.disparo_flame_izquierda);
        imagen = imagenDerecha;
    }

    public void moverAutomaticamente() {
        if (orientacion) {
            x = enemigo.x + 35;
            imagen = imagenDerecha;
        }else{
            imagen = imagenIzquierda;
            x = enemigo.x-35;
        }
    }

}
