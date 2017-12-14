package com.proyectogrupo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.IOException;
import java.util.Random;

/**
 * Created by Fer on 12/12/2017.
 */

public class Utils {
    public static int randBetween(int min, int max) {
        return new Random().nextInt(max + 1 - min) + min;
    }

    public static int getNumNiveles(Context context) {
        int numNiveles = 0;
        String[] files = new String[0];
        try {
            files = context.getAssets().list("");
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String file : files) {
            if (file.endsWith(".txt")) {
                numNiveles++;
            }
        }

        return numNiveles;
    }

    public static void mostrarPuntos(Context context, int puntos) {
        Intent intent = new Intent(context, PuntosActivity.class);
        intent.putExtra(PuntosActivity.EXTRA_PUNTOS, puntos);
        context.startActivity(intent);
    }

    public static boolean record(Context context, int puntos) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        int recordPuntos = prefs.getInt(PuntosActivity.EXTRA_PUNTOS, -1);

        if (puntos > recordPuntos) {
            SharedPreferences.Editor prefsEditor = prefs.edit();
            prefsEditor.putInt(PuntosActivity.EXTRA_PUNTOS, puntos);
            prefsEditor.apply();
            return true;
        }

        return false;
    }
}
