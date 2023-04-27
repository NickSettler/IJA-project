package org.example;

import ija.ija2022.homework2.game.MazeConfigure;
import ija.ija2022.homework2.tool.MazePresenter;
import ija.ija2022.homework2.tool.common.CommonMazeObject;
import ija.ija2022.homework2.tool.common.IField;
import ija.ija2022.homework2.tool.common.IMaze;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
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

        IMaze commonMaze = cfg.createMaze();

        MazePresenter presenter = new MazePresenter(commonMaze);
        presenter.open();

//        sleep(2000);

        List<CommonMazeObject> ghosts = commonMaze.ghosts();
        CommonMazeObject ghost = null;
        for (CommonMazeObject _ghost : ghosts) {
            if (_ghost != null) {
                ghost = _ghost;
                break;
            }
        }

        ghost.move(IField.Direction.R);
        sleep(1000);
        ghost.move(IField.Direction.R);
        sleep(1000);

//        commonMaze.getPacman().move(IField.Direction.R);

//        commonMaze.getPacman().move(IField.Direction.U);
    }

    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
