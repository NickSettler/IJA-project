package ija.ija2022.project;

import ija.ija2022.project.game.GameController;
import ija.ija2022.project.game.configure.MazeConfigure;
import ija.ija2022.project.settings.GAME_MODE;
import ija.ija2022.project.tool.common.CommonMaze;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
//        UIController.getInstance().showMainView();

//        Window mainWindow = new Window();
//        mainWindow.getFrame().setVisible(true);

//        keyboardController.addObserver(ghostMoveObserver);

        ArrayList<String> lines = new ArrayList<>();

        try (
                FileReader fileReader = new FileReader("mapa01.txt");
                BufferedReader bufferedReader = new BufferedReader(fileReader);
        ) {
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        MazeConfigure cfg = new MazeConfigure();
        cfg.startReading(10, 10);
        lines.forEach(cfg::processLine);
        cfg.stopReading();

        CommonMaze commonMaze = cfg.createMaze();

//        ReplayController replayController = new ReplayController(commonMaze, GAME_MODE.CONTINUOUS, "data/history03.json");
//        replayController.start();

        GameController controller = new GameController(commonMaze, GAME_MODE.STEP_BY_STEP);
        controller.start();
    }

    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
