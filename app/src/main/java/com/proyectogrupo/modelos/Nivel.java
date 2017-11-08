package com.proyectogrupo.modelos;

import android.content.Context;
import android.graphics.Canvas;

import com.proyectogrupo.R;
import com.proyectogrupo.gestores.CargadorGraficos;

public class Nivel {
    private Context context = null;
    private int numeroNivel;
    private Fondo fondo;
    private Nave nave;

    public boolean inicializado;

    public Nivel(Context context, int numeroNivel) throws Exception {
        inicializado = false;

        this.context = context;
        this.numeroNivel = numeroNivel;
        inicializar();

        inicializado = true;
    }

    public void inicializar()throws Exception {
        fondo = new Fondo(context,CargadorGraficos.cargarDrawable(context, R.drawable.fondo));
        nave = new Nave(context);
    }


    public void actualizar (long tiempo){
        if (inicializado) {
            nave.actualizar(tiempo);
        }
    }


    public void dibujar (Canvas canvas) {
        if(inicializado) {
            fondo.dibujar(canvas);
            nave.dibujar(canvas);
        }
    }
}

