package com.proyectogrupo.powerups;

import android.content.Context;

import com.proyectogrupo.R;
import com.proyectogrupo.gestores.CargadorGraficos;
import com.proyectogrupo.modelos.Nivel;
import com.proyectogrupo.modelos.enemigos.Enemigo;

import java.util.ArrayList;
import java.util.List;


public class CajaDestruccion extends PowerUp {
    public CajaDestruccion(Context context, double x, double y) {
        super(context, x, y, 30, 30);
        imagen = CargadorGraficos.cargarDrawable(context, R.drawable.caja_dest);
    }

    @Override

    public void efecto(Nivel nivel) {
        List<Enemigo> aBorrar = new ArrayList<Enemigo>();
        for (Enemigo e : nivel.enemigos) {
            if (e.y - e.altura <= this.y + altura
                    && e.y + e.altura >= this.y - altura)
                aBorrar.add(e);
        }

        for (Enemigo e : aBorrar)
            nivel.enemigos.remove(e);
    }
}
