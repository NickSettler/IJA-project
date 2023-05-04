package ija.ija2022.project;

import ija.ija2022.project.ui.Window;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
//        UIController.getInstance().showMainView();

        Window mainWindow = new Window();
        mainWindow.getFrame().setVisible(true);

//        keyboardController.addObserver(ghostMoveObserver);

//        MazeConfigure cfg = new MazeConfigure("mapa01.txt");
//        CommonMaze commonMaze = cfg.createMaze();

//        ReplayController replayController = new ReplayController(commonMaze, GAME_MODE.CONTINUOUS, "data/history03.json");
//        replayController.start();

//        GameController controller = new GameController(commonMaze, GAME_MODE.STEP_BY_STEP);
//        controller.start();
    }

    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
