package com.proyectogrupo.modelos.disparos;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;

import com.proyectogrupo.R;
import com.proyectogrupo.gestores.CargadorGraficos;
import com.proyectogrupo.graficos.Sprite;
import com.proyectogrupo.modelos.Nivel;
import com.proyectogrupo.modelos.enemigos.Enemigo;

import java.util.HashMap;
import java.util.Map;

public class DisparoEnemigoLanzallamas extends DisparoEnemigo {


    private static final String llamaDerecha = "Llama derecha";
    private static final String llamaIzquieda = "Llama izquierda";

    //private final Drawable imagenDerecha;
    //private final Drawable imagenIzquierda;
    private Sprite sprite;
    private Map<String, Sprite> sprites =
            new HashMap<String, Sprite>();

    public DisparoEnemigoLanzallamas(Context context, double x,
                                     double y, boolean orientacion, Enemigo enemigo) {
        super(context, x, y, orientacion, 6, enemigo, 20, 50);
        sprites.put(llamaDerecha, new Sprite(CargadorGraficos.cargarDrawable
                (context, R.drawable.llama_derecha), ancho, altura, 2, 6, true));
        sprites.put(llamaIzquieda, new Sprite(CargadorGraficos.cargarDrawable
                (context, R.drawable.llama_izquierda), ancho, altura, 2, 6, true));
        this.damage = 2;
        this.moverAutomaticamente();
    }

    public void dibujar(Canvas canvas) {
        this.sprite.dibujarSprite(canvas, (int) x,
                (int) y - Nivel.scrollEjeY, true);
    }

    public void actualizar(long tiempo) {
        this.sprite.actualizar(tiempo);
        moverAutomaticamente();
    }

    public void moverAutomaticamente() {
        if (enemigo.velocidadX > 0) {
            x = enemigo.x + 47;
            y = enemigo.y;
            this.sprite = sprites.get(llamaDerecha);
        } else {
            this.sprite = sprites.get(llamaIzquieda);
            x = enemigo.x - 47;
            y = enemigo.y;
        }
    }

}
