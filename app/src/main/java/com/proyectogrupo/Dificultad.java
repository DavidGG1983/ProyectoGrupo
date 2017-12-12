package com.proyectogrupo;

/**
 * Created by Fer on 10/12/2017.
 */

public enum Dificultad {
    FACIL {
        @Override
        public int getMaxSegundosQuieto() {
            return 60;
        }
    }, MEDIO {
        @Override
        public int getMaxSegundosQuieto() {
            return 20;
        }
    }, DIFICIL {
        @Override
        public int getMaxSegundosQuieto() {
            return 10;
        }
    }, EXTREMO {
        @Override
        public int getMaxSegundosQuieto() {
            return 5;
        }
    };

    public abstract int getMaxSegundosQuieto();
}
