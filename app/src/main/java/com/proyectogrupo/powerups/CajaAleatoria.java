package com.proyectogrupo.powerups;

import android.content.Context;
import android.graphics.Canvas;

import com.proyectogrupo.R;
import com.proyectogrupo.gestores.CargadorGraficos;
import com.proyectogrupo.modelos.Nivel;

import java.util.Random;

/**
 * Patron State.
 */

public class CajaAleatoria extends PowerUp {
    long tiempoPowerUpGenerado;
    PowerUp powerup;

    public CajaAleatoria(Context context, double x, double y) {
        super(context, x, y, 21, 21);
        selectPowerUp();
        imagen = CargadorGraficos.cargarDrawable(context, R.drawable.random);
    }

    private void selectPowerUp() {
        Random rdn = new Random();
        int value = rdn.nextInt(11);
        switch (11) {
            case 0:
                powerup = new CajaBomba(context, x, y);
                return;
            case 1:
                powerup = new CajaColor(context, x, y);
                return;
            case 2:
                powerup = new CajaDestruccion(context, x, y);
                return;
            case 3:
                powerup = new CajaInvulnerabilidad(context, x, y);
                return;
            case 4:
                powerup = new CajaSemiInvulnerabilidad(context, x, y);
                return;
            case 5:
                powerup = new CajaPuntosExtra(context, x, y);
                return;
            case 6:
                powerup = new CajaVelocidad(context, x, y);
                return;
            case 7:
                powerup = new CajaVidaExtra(context, x, y);
                return;
            case 8:
                powerup = new MonedaRecolectable(context, x, y);
                return;
            case 9:
                powerup = new Teletransporte(context, x, y);
                return;
            case 10:
                powerup = new CajaLentitud(context, x, y);
                return;
            case 11:
                powerup = new CajaEnemigos(context, x, y);
                return;
            case 12:
                powerup = new CajaContraEnemigos(context, x, y);
                return;
            default:
                powerup = null;
        }
    }

    @Override
    public void efecto(Nivel nivel) {
        powerup.efecto(nivel);
    }

    @Override
    public void dibujar(Canvas canvas) {
        if (System.currentTimeMillis() - tiempoPowerUpGenerado > 10000) {
            selectPowerUp();
            tiempoPowerUpGenerado = System.currentTimeMillis();
        }
        super.dibujar(canvas);
    }
}
