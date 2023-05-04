package ija.ija2022.project;

import ija.ija2022.project.game.ReplayController;
import ija.ija2022.project.game.configure.MazeConfigure;
import ija.ija2022.project.settings.GAME_MODE;
import ija.ija2022.project.tool.common.CommonMaze;
import ija.ija2022.project.ui.Window;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
//        UIController.getInstance().showMainView();

        Window mainWindow = new Window();
        mainWindow.getFrame().setVisible(true);

//        keyboardController.addObserver(ghostMoveObserver);

        ArrayList<String> lines = new ArrayList<>();

        try (
                FileReader fileReader = new FileReader("mapa02.txt");
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
        cfg.startReading(5, 5);
        lines.forEach(cfg::processLine);
        cfg.stopReading();

        CommonMaze commonMaze = cfg.createMaze();

//        LoggerController loggerController = new LoggerController(LOGGER_MODE.WRITE);
//        loggerController.addItem(new LogItem(CHARACTER_MAP.PACMAN, new Pair<>(0, 0), new Pair<>(0, 1)));
//        loggerController.addItem(new LogItem(CHARACTER_MAP.GHOST, new Pair<>(4, 4), new Pair<>(4, 3)));
//        loggerController.nextEntry();
//        loggerController.addItem(new LogItem(CHARACTER_MAP.PACMAN, new Pair<>(0, 1), new Pair<>(0, 2)));
//        loggerController.addItem(new LogItem(CHARACTER_MAP.GHOST, new Pair<>(4, 3), new Pair<>(4, 2)));
//        loggerController.nextEntry();
//        loggerController.addItem(new LogItem(CHARACTER_MAP.PACMAN, new Pair<>(0, 2), new Pair<>(0, 3)));
//        loggerController.addItem(new LogItem(CHARACTER_MAP.GHOST, new Pair<>(4, 2), new Pair<>(4, 1)));
//        loggerController.nextEntry();
//        loggerController.addItem(new LogItem(CHARACTER_MAP.PACMAN, new Pair<>(0, 3), new Pair<>(0, 4)));
//        loggerController.addItem(new LogItem(CHARACTER_MAP.GHOST, new Pair<>(4, 1), new Pair<>(4, 0)));
//        loggerController.nextEntry();
//        loggerController.addItem(new LogItem(CHARACTER_MAP.PACMAN, new Pair<>(0, 4), new Pair<>(1, 4)));
//        loggerController.addItem(new LogItem(CHARACTER_MAP.GHOST, new Pair<>(4, 0), new Pair<>(3, 0)));
//        loggerController.nextEntry();
//        loggerController.addItem(new LogItem(CHARACTER_MAP.PACMAN, new Pair<>(1, 4), new Pair<>(2, 4)));
//        loggerController.addItem(new LogItem(CHARACTER_MAP.GHOST, new Pair<>(3, 0), new Pair<>(2, 0)));

//        FileOutputStream os = null;
//        try {
//            os = new FileOutputStream("data/history01.json");
//        } catch (FileNotFoundException e) {
//            System.out.println("Settings file not found");
//        }
//
//        try {
//            assert os != null;
//            os.write(loggerController.toJSON().toString().getBytes());
//            os.close();
//        } catch (IOException e) {
//            System.out.println("Error while saving settings");
//        }

//        LoggerController loggerController = new LoggerController(LOGGER_MODE.READ, "data/history01.json");

        ReplayController replayController = new ReplayController(commonMaze, GAME_MODE.CONTINUOUS, "data/history02.json");
        replayController.start();

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
