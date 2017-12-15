package com.proyectogrupo;

import android.app.Activity;
import android.content.Context;

public class Hilo extends Thread {
    private long time;
    private Context context;
    private Runnable action;

    public Hilo(Context context, long time, Runnable action) {
        super(action);
        this.time = time;
        this.context = context;
        this.action = action;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(time);
            ((Activity)context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    action.run();
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
