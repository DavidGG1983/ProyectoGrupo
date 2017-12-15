package com.proyectogrupo.modelos;

import android.graphics.drawable.Drawable;

public class Tile {

    public static final int PASABLE = 0;
    public static final int SOLIDO = 1;

    public int tipoDeColision; // PASABLE o SOLIDO

    public static int ancho = 40;
    public static int altura = 32;

    public Drawable imagen;

    public Tile(Drawable imagen, int tipoDeColision) {
        this.imagen = imagen;
        this.tipoDeColision = tipoDeColision;
    }

}
