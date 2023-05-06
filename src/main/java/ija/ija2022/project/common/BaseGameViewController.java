package ija.ija2022.project.common;

import ija.ija2022.project.events.EventManager;
import ija.ija2022.project.settings.GAME_MODE;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class BaseGameViewController implements Runnable {
    protected GAME_MODE mode;
    protected Thread tickThread;

    protected int timeToSleep = 250;

    protected AtomicBoolean isRunning = new AtomicBoolean(false);

    public BaseGameViewController(GAME_MODE mode) {
        this.mode = mode;
        this.tickThread = new Thread(this);

        EventManager.getInstance().addEventListener(this);
    }

    @Override
    public void run() {
        isRunning.set(true);
        while (isRunning.get()) {
            try {
                this.tick();

                if (this.mode == GAME_MODE.CONTINUOUS) Thread.sleep(timeToSleep);

                runCheck();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void updateTimeToSleep(int newTime) {
        timeToSleep = newTime;
        if (timeToSleep < 0) {
            timeToSleep = 0;
        }
    }

    protected void runCheck() {
    }

    abstract protected void update();

    abstract protected void render();

    protected void tick() {
        this.update();
        this.render();
    }

    public void start() {
        this.tickThread = new Thread(this);
        this.tickThread.start();
    }

    public void stop() {
        this.isRunning.set(false);
    }

    public void destroy() {
        this.stop();
        this.tickThread.interrupt();
        this.tickThread = null;
        this.isRunning = null;

        EventManager.getInstance().removeEventListener(this);
    }

    public void setMode(GAME_MODE mode) {
        this.mode = mode;
    }

    public GAME_MODE getMode() {
        return mode;
    }

    abstract public JPanel getMazeView();
}
