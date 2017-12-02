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

    public final static String BASICO = "moviendose";

    public EnemigoBasico(Context context, double x, double y) {
        super(context, x, y);
        this.velocidadX = 3;
    }

    @Override
    public void inicializar() {
        this.sprite = new Sprite(CargadorGraficos.cargarDrawable
                (this.context, R.drawable.enemigo_basico),ancho,altura,4,4,true);
    }

    public void mover(){
        this.xAnterior = x;
        this.x += velocidadX;
        if(x > GameView.pantallaAncho)
            x = 0;
    }

    public void actualizar(long tiempo){
        sprite.actualizar(tiempo);
    }
}
