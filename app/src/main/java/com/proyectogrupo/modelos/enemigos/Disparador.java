package com.proyectogrupo.modelos.enemigos;

import com.proyectogrupo.modelos.disparos.DisparoEnemigo;

public interface Disparador {
    public abstract DisparoEnemigo disparar(long milisegundos);
}
