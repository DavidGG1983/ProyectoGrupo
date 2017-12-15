package com.proyectogrupo.powerups;

import android.content.Context;

import com.proyectogrupo.R;
import com.proyectogrupo.gestores.CargadorGraficos;
import com.proyectogrupo.modelos.Nivel;
import com.proyectogrupo.modelos.Tile;

public class Teletransporte extends PowerUp {


    public Teletransporte(Context context, double x, double y) {
        super(context, x, y, 30, 30);
        this.imagen = CargadorGraficos.cargarDrawable(context,
                R.drawable.go);
    }

    @Override
    public void efecto(Nivel nivel) {
        int avanzar;
        do {
            avanzar = (int) (Math.random() * 5 + 4);
        }
        while (nivel.nave.y - (avanzar * Tile.altura) <= 0
                || nivel.getTile((int) nivel.nave.x / Tile.ancho,
                (int) (nivel.nave.y - avanzar * Tile.altura) / Tile.altura) != Tile.PASABLE
                || nivel.getTile((int) nivel.nave.x / Tile.ancho,
                (int) (nivel.nave.y - avanzar * Tile.altura) / Tile.altura + 1) != Tile.PASABLE);

        double yFinal = nivel.nave.y - avanzar * Tile.altura;

        while (nivel.nave.y > yFinal) {
            nivel.nave.y--;
            nivel.aplicarScroll();
        }
    }
}
