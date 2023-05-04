package ija.ija2022.project.game.logger;

import ija.ija2022.project.game.GhostObject;
import ija.ija2022.project.game.configure.CHARACTER_MAP;
import ija.ija2022.project.tool.common.CommonMazeObject;
import javafx.util.Pair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class LoggerController {
    private final LOGGER_MODE mode;

    private final String filePath;

    private ArrayList<LogEntry> entries = new ArrayList<>();

    private int index = 0;

    public LoggerController(LOGGER_MODE mode) {
        this.mode = mode;
        this.filePath = null;
    }

    public LoggerController(LOGGER_MODE mode, String filePath) {
        this.mode = mode;
        this.filePath = filePath;

        this.process();
    }

    private void process() {
        if (this.mode == LOGGER_MODE.WRITE) {
            throw new IllegalStateException("Cannot process in write mode");
        }

        if (this.filePath == null) {
            throw new IllegalStateException("Cannot process without file path");
        }


        FileInputStream is;
        try {
            is = new FileInputStream(this.filePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        JSONTokener tokener = new JSONTokener(is);
        JSONObject object = new JSONObject(tokener);
        JSONArray entries = object.getJSONArray("items");

        this.entries = new ArrayList<>(entries.length());

        for (int i = 0; i < entries.length(); i++) {
            this.entries.add(LogEntry.fromJSONArray(entries.getJSONArray(i)));
        }
    }

    public void addItem(LogItem item) {
        if (this.mode == LOGGER_MODE.READ) {
            throw new IllegalStateException("Cannot add item in read mode");
        }

        if (this.entries.size() == 0) {
            this.entries.add(new LogEntry(new ArrayList<>()));
        }

        if (this.entries.size() < this.index + 1) {
            this.entries.add(new LogEntry(new ArrayList<>()));
        }

        this.entries.get(this.index).items().add(item);
    }

    public void addItem(CommonMazeObject object) {
        CHARACTER_MAP character = object instanceof GhostObject ? CHARACTER_MAP.GHOST : CHARACTER_MAP.PACMAN;

        Pair<Integer, Integer> from = new Pair<>(
                object.getRow() - object.getDirection().deltaRow(),
                object.getCol() - object.getDirection().deltaCol()
        );

        Pair<Integer, Integer> to = new Pair<>(object.getRow(), object.getCol());

        this.addItem(new LogItem(character, from, to));
    }

    public void nextEntry() {
        this.index++;
    }

    public LogEntry currentEntry() {
        if (this.index >= this.entries.size()) return null;

        return this.entries.get(this.index);
    }

    public int getIndex() {
        return index;
    }

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();

        object.put("items", new ArrayList<>());

        JSONArray[] entries = this.entries.stream().map(LogEntry::toJSONArray).toArray(JSONArray[]::new);
        for (JSONArray entry : entries) {
            object.getJSONArray("items").put(entry);
        }

        return object;
    }
}
