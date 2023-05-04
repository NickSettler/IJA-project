package ija.ija2022.project.game;

import ija.ija2022.project.events.EventManager;
import ija.ija2022.project.settings.GAME_MODE;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class BaseMazeController implements Runnable {
    protected GAME_MODE mode;
    protected Thread tickThread;

    protected final AtomicBoolean isRunning = new AtomicBoolean(false);

    public BaseMazeController(GAME_MODE mode) {
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

                if (this.mode == GAME_MODE.CONTINUOUS) Thread.sleep(250);

                runCheck();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
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

    public void setMode(GAME_MODE mode) {
        this.mode = mode;
    }

    public GAME_MODE getMode() {
        return mode;
    }

    abstract public JPanel getMazeView();
}
