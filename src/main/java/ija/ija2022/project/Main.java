package ija.ija2022.project;

import ija.ija2022.project.game.GameController;
import ija.ija2022.project.game.MazeConfigure;
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

        GameController controller = new GameController(commonMaze, GAME_MODE.CONTINUOUS);
        controller.start();

//        GhostObject firstGhost = null;
//        for (CommonField[] fields : commonMaze.getFields()) {
//            for (CommonField field : fields) {
//                if (field.get() instanceof GhostObject) {
//                    firstGhost = (GhostObject) field.get();
//                }
//            }
//        }
//
//        sleep(1000);
//
//        if (firstGhost != null) {
//            firstGhost.move(CommonField.Direction.U);
//        }
    }

    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
