package com.proyectogrupo.powerups;

import android.content.Context;

import com.proyectogrupo.R;
import com.proyectogrupo.gestores.CargadorGraficos;
import com.proyectogrupo.modelos.Nivel;
import com.proyectogrupo.modelos.enemigos.Enemigo;
import com.proyectogrupo.modelos.enemigos.EnemigoBasico;
import com.proyectogrupo.modelos.enemigos.EnemigoDisparador;
import com.proyectogrupo.modelos.enemigos.EnemigoLanzaBombas;
import com.proyectogrupo.modelos.enemigos.EnemigoLanzallamas;
import com.proyectogrupo.modelos.enemigos.EnemigoRalentizador;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Daniel on 13/12/2017.
 */

public class CajaEnemigos extends PowerUp {

    public CajaEnemigos(Context context, double x, double y) {
        super(context, x, y, 29, 30);
        imagen = CargadorGraficos.cargarDrawable(context, R.drawable.caja_enemigos);
    }

    @Override
    public void efecto(Nivel nivel) {
        List<Enemigo> enemigos = nivel.enemigos;
        List<Enemigo> aAñadir = new ArrayList<>();
        Enemigo enemigo1;
        Enemigo enemigo2;
        Random rdn = new Random();
        int it = 2;
        do {
            int enemigo = rdn.nextInt(5);
            switch (enemigo) {
                case 0:
                    aAñadir.add(new EnemigoBasico(context, x, y));
                    break;
                case 1:
                    aAñadir.add(new EnemigoDisparador(context, x, y));
                    break;
                case 2:
                    aAñadir.add(new EnemigoLanzaBombas(context, x, y));
                    break;
                case 3:
                    aAñadir.add(new EnemigoLanzallamas(context, x, y));
                    break;
                case 4:
                    aAñadir.add(new EnemigoRalentizador(context, x, y));
                    break;
            }
            it--;
        } while (it > 0);

        enemigo1 = aAñadir.get(0);
        enemigo2 = aAñadir.get(1);
        enemigo1.y = enemigo1.y + enemigo1.altura + this.altura;
        enemigo2.y = enemigo2.y - this.altura;
        enemigos.add(enemigo1);
        enemigos.add(enemigo2);
    }
}