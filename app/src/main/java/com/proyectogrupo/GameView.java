package com.proyectogrupo;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.proyectogrupo.GameLoop;
import com.proyectogrupo.graficos.Sprite;
import com.proyectogrupo.modelos.IconoVida;
import com.proyectogrupo.modelos.Nave;
import com.proyectogrupo.modelos.Nivel;
import com.proyectogrupo.modelos.controles.Pad;


public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    boolean iniciado = false;
    Context context;
    GameLoop gameloop;

    public static int pantallaAncho;
    public static int pantallaAlto;

    private Nivel nivel;
    public int numeroNivel = 0;
    private IconoVida[] iconosVida;
    private Pad pad;

    public GameView(Context context) {
        super(context);
        iniciado = true;

        getHolder().addCallback(this);
        setFocusable(true);

        this.context = context;
        gameloop = new GameLoop(this);
        gameloop.setRunning(true);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // valor a Binario
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        // Indice del puntero
        int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;

        int pointerId = event.getPointerId(pointerIndex);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                accion[pointerId] = ACTION_DOWN;
                x[pointerId] = event.getX(pointerIndex);
                y[pointerId] = event.getY(pointerIndex);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL:
                accion[pointerId] = ACTION_UP;
                x[pointerId] = event.getX(pointerIndex);
                y[pointerId] = event.getY(pointerIndex);
                break;
            case MotionEvent.ACTION_MOVE:
                int pointerCount = event.getPointerCount();
                for (int i = 0; i < pointerCount; i++) {
                    pointerIndex = i;
                    pointerId = event.getPointerId(pointerIndex);
                    accion[pointerId] = ACTION_MOVE;
                    x[pointerId] = event.getX(pointerIndex);
                    y[pointerId] = event.getY(pointerIndex);
                }
                break;
        }

        procesarEventosTouch();
        return true;
    }

    int NO_ACTION = 0;
    int ACTION_MOVE = 1;
    int ACTION_UP = 2;
    int ACTION_DOWN = 3;
    int accion[] = new int[6];
    float x[] = new float[6];
    float y[] = new float[6];

    public void procesarEventosTouch() {
        boolean pulsacionPadMoverX = false;
        boolean pulsacionPadMoverY = false;

        for (int i = 0; i < 6; i++) {
            if (accion[i] != NO_ACTION) {
                int pulsacion = pad.estaPulsado(x[i], y[i]);
                if (pulsacion != 0) {
                    float orientacion =
                            pulsacion == 1 ? pad.getOrientacionX(x[i]) : pad.getOrientacionY(y[i]);
                    // Si al menosuna pulsacion está en el pad
                    if (accion[i] != ACTION_UP) {
                        if (pulsacion == Pad.EJE_X) {
                            nivel.orientacionPadX = orientacion;
                            pulsacionPadMoverX = true;
                        } else {
                            nivel.orientacionPadY = orientacion;
                            pulsacionPadMoverY = true;
                        }
                    }
                }
            }
        }
        if (!pulsacionPadMoverX) {
            nivel.orientacionPadX = 0;
        }
        if (!pulsacionPadMoverY) {
            nivel.orientacionPadY = 0;
        }
    }

    protected void inicializar() throws Exception {
        nivel = new Nivel(context, numeroNivel);
        pad = new Pad(context);
        iconosVida = new IconoVida[3];

        iconosVida[0] = new IconoVida(context, GameView.pantallaAncho * 0.08,
                GameView.pantallaAlto * 0.05);
        iconosVida[1] = new IconoVida(context, GameView.pantallaAncho * 0.18,
                GameView.pantallaAlto * 0.05);
        iconosVida[2] = new IconoVida(context, GameView.pantallaAncho * 0.28,
                GameView.pantallaAlto * 0.05);
    }

    public void actualizar(long tiempo) throws Exception {
        nivel.actualizar(tiempo);
    }

    protected void dibujar(Canvas canvas) {
        nivel.dibujar(canvas);
        pad.dibujar(canvas);
        for (int i = 0; i < nivel.nave.getVida(); i++)
            iconosVida[i].dibujar(canvas);
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        pantallaAncho = width;
        pantallaAlto = height;
    }

    public void surfaceCreated(SurfaceHolder holder) {
        if (iniciado) {
            iniciado = false;
            if (gameloop.isAlive()) {
                iniciado = true;
                gameloop = new GameLoop(this);
            }

            gameloop.setRunning(true);
            gameloop.start();
        } else {
            iniciado = true;
            gameloop = new GameLoop(this);
            gameloop.setRunning(true);
            gameloop.start();
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        iniciado = false;

        boolean intentarDeNuevo = true;
        gameloop.setRunning(false);
        while (intentarDeNuevo) {
            try {
                gameloop.join();
                intentarDeNuevo = false;
            } catch (InterruptedException e) {
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
                nivel.orientacionPadY = 0.5f;
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                nivel.orientacionPadY = -0.5f;
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                nivel.orientacionPadX = 0.5f;
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                nivel.orientacionPadX = -0.5f;
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
                nivel.orientacionPadY = 0;
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                nivel.orientacionPadY = 0;
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                nivel.orientacionPadX = 0;
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                nivel.orientacionPadX = 0;
                break;
        }
        return super.onKeyUp(keyCode, event);
    }
}

