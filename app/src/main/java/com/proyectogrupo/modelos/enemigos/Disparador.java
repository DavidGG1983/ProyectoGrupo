package com.proyectogrupo.modelos.enemigos;

import com.proyectogrupo.modelos.disparos.DisparoEnemigo;

/**
 * Created by migue on 12/12/2017.
 */

public interface Disparador {
    public abstract DisparoEnemigo disparar(long milisegundos);
}
