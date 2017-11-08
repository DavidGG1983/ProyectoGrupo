package com.proyectogrupo.modelos;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.proyectogrupo.GameView;


public class Fondo extends Modelo{

    public Fondo(Context context, Drawable imagen) {
        super(context,
                GameView.pantallaAncho/2,
                GameView.pantallaAlto/2,
                GameView.pantallaAlto,
                GameView.pantallaAncho );

        this.imagen = imagen;
    }
}
