package ija.ija2022.project.common;

import ija.ija2022.project.events.EventManager;
import ija.ija2022.project.settings.GAME_MODE;
import ija.ija2022.project.settings.SettingsController;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class BaseGameViewController implements Runnable {
    protected GAME_MODE mode;
    protected Thread tickThread;
    protected int tickTime = 250;
    protected AtomicBoolean isRunning = new AtomicBoolean(false);
    protected final SettingsController settingsController;

    public BaseGameViewController(GAME_MODE mode) {
        this.mode = mode;
        this.tickThread = new Thread(this);
        this.settingsController = new SettingsController();

        EventManager.getInstance().addEventListener(this);
    }

    @Override
    public void run() {
        isRunning.set(true);
        while (isRunning.get()) {
            try {
                this.tick();

                if (this.mode == GAME_MODE.CONTINUOUS) Thread.sleep(tickTime);

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

    public void destroy() {
        this.stop();
        this.tickThread.interrupt();
        this.tickThread = null;
        this.isRunning = null;

        EventManager.getInstance().removeEventListener(this);
    }

    public void increaseTickTime() {
        tickTime += 50;
    }

    public void decreaseTickTime() {
        tickTime -= 50;
        if (tickTime < 0) {
            tickTime = 0;
        }
    }

    public void setMode(GAME_MODE mode) {
        this.mode = mode;
    }

    public GAME_MODE getMode() {
        return mode;
    }

    abstract public JPanel getMazeView();

    public int getMaxLives() {
        return this.settingsController.getMaxLives();
    }
}
