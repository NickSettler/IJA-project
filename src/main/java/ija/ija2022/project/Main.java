package ija.ija2022.project;

import ija.ija2022.project.events.EVENTS;
//import ija.ija2022.project.events.EventCallback;
import ija.ija2022.project.events.EventsSystem;
import ija.ija2022.project.game.MazeConfigure;
import ija.ija2022.project.game.PacmanObject;
import ija.ija2022.project.settings.GAME_MODE;
import ija.ija2022.project.tool.MazePresenter;
import ija.ija2022.project.tool.common.CommonMaze;
import ija.ija2022.project.tool.common.CommonMazeObject;
import ija.ija2022.project.tool.common.CommonField;
import ija.ija2022.project.tool.common.Observable;
import ija.ija2022.project.ui.*;

import java.awt.event.*;
import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
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

        EventsSystem.getInstance().on(EVENTS.KEYDOWN, (Object codes) -> {
            Integer code = (Integer) ((Object[]) codes)[0];
            PacmanObject pacman = commonMaze.getPacman();

            switch (code) {
                case KeyEvent.VK_W -> pacman.move(CommonField.Direction.U);
                case KeyEvent.VK_S -> pacman.move(CommonField.Direction.D);
                case KeyEvent.VK_A -> pacman.move(CommonField.Direction.L);
                case KeyEvent.VK_D -> pacman.move(CommonField.Direction.R);
            }


            return null;
        });

        MazePresenter presenter = new MazePresenter(commonMaze);
        presenter.open();


//        List<CommonMazeObject> ghosts = commonMaze.ghosts();
//        CommonMazeObject ghost = null;
//        for (CommonMazeObject _ghost : ghosts) {
//            if (_ghost != null) {
//                ghost = _ghost;
//                break;
//            }
//        }

//        ghost.move(CommonField.Direction.R);
//        sleep(1000);
//        ghost.move(CommonField.Direction.R);
//        sleep(1000);
    }

    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
