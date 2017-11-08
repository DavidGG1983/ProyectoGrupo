package com.proyectogrupo.modelos;

import android.content.Context;
import android.graphics.Canvas;

import com.proyectogrupo.GameView;
import com.proyectogrupo.R;
import com.proyectogrupo.gestores.CargadorGraficos;
import com.proyectogrupo.graficos.Sprite;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by davidgarcia on 8/11/17.
 */

public class Nave extends Modelo {

    static int posicionInicialX = (int) (GameView.pantallaAncho * 0.5);
    static int posicionInicialY = (int) (GameView.pantallaAlto * 0.90);
    private static final String NAVE_MOVIENDOSE = "nave_moviendose";
    Map<String, Sprite> sprites;
    Sprite sprite;

    public Nave(Context context) {
        super(context, posicionInicialX, posicionInicialY, 63, 50);
        this.x = posicionInicialX;
        this.y = posicionInicialY;
        Sprite moviendose = new Sprite(CargadorGraficos.cargarDrawable(context,
                R.drawable.animacion_nave), 50, 63, 4, 4, true);
        sprites = new HashMap<>();
        sprites.put(NAVE_MOVIENDOSE,moviendose);
        this.sprite  = moviendose;
    }

    public void actualizar(long tiempo){
        this.sprite.actualizar(tiempo);
    }

    public void dibujar(Canvas canvas){
        this.sprite.dibujarSprite(canvas,(int)this.x,(int)this.y);
    }
}
