package com.proyectogrupo;


import android.os.Handler;
import android.os.Looper;

/**
 * Created by Daniel on 02/12/2017.
 */

public class Hilo extends Thread {
    private long time;

    public Hilo(long time, Runnable action) {
        super(action);
        this.time = time;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(time);
            super.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.run();
    }
}
