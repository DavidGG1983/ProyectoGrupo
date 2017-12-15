package com.proyectogrupo;

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
