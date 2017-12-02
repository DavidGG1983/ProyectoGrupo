package com.proyectogrupo.modelos.controles;

import android.content.Context;

import com.proyectogrupo.GameView;
import com.proyectogrupo.R;
import com.proyectogrupo.gestores.CargadorGraficos;
import com.proyectogrupo.graficos.Sprite;
import com.proyectogrupo.modelos.Enemigo;

/**
 * Created by davidgarcia on 2/12/17.
 */

public class EnemigoBasico extends Enemigo {

    public final static String MOVER_DERECHA = "mover_derecha";
    public final static String MOVER_IZQUIERDA = "mover_izquierda";


    public EnemigoBasico(Context context, double x, double y) {
        super(context, x, y);
        this.velocidadX = 3;
        this.inicializar();
    }

    @Override
    public void inicializar() {
        Sprite moverDerecha = new Sprite(CargadorGraficos.cargarDrawable
                (this.context, R.drawable.enemigo_basico_derecha),ancho,altura,4,4,true);
        Sprite moverIzquierda = new Sprite(CargadorGraficos.cargarDrawable
                (this.context, R.drawable.enemigo_basico_izquierda),ancho,altura,4,4,true);
        sprites.put(MOVER_DERECHA,moverDerecha);
        sprites.put(MOVER_IZQUIERDA,moverIzquierda);
        this.sprite = moverDerecha;
    }

    public void mover(){
        this.xAnterior = x;
        this.x += velocidadX;
        if(x > GameView.pantallaAncho)
            x = 0;
        if(x < 0)
            x = GameView.pantallaAncho;
    }

    public void actualizar(long tiempo){
        sprite.actualizar(tiempo);
    }

    public void girar(){
        this.velocidadX *= -1;
        if(sprite == sprites.get(MOVER_DERECHA))
            sprite = sprites.get(MOVER_IZQUIERDA);
        else
            sprite = sprites.get(MOVER_DERECHA);
    }
}
