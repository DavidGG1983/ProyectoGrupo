package com.proyectogrupo;

import java.util.Random;

/**
 * Created by Fer on 12/12/2017.
 */

public class Utils {
    public static int randBetween(int min, int max) {
        return new Random().nextInt(max + 1 - min) + min;
    }
}
