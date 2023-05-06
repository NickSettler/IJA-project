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
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoggerController {
    private final LOGGER_MODE mode;

    private String filePath;

    private ArrayList<LogEntry> entries = new ArrayList<>();

    private String mapText;

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
        if (this.mode == LOGGER_MODE.WRITE) return;

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

        this.mapText = object.getString("map");
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
        if (this.index >= this.entries.size()) return;
        this.index++;
    }

    public void previousEntry() {
        if (this.index <= -1) return;
        this.index--;
    }

    public LogEntry currentEntry() {
        if (this.index >= this.entries.size() || this.index < 0)
            return null;

        return this.entries.get(this.index);
    }

    public LogEntry getEntry(int index) {
        if (index >= this.entries.size() || index < 0)
            return null;

        return this.entries.get(index);
    }

    public int getIndex() {
        return index;
    }

    public String getMapText() {
        return mapText;
    }

    public JSONObject toJSON() {
        JSONObject object = new JSONObject();

        object.put("items", new ArrayList<>());

        JSONArray[] entries = this.entries.stream().map(LogEntry::toJSONArray).toArray(JSONArray[]::new);
        for (JSONArray entry : entries) {
            object.getJSONArray("items").put(entry);
        }

        object.put("map", this.mapText);

        return object;
    }

    public void close() {
        if (this.mode == LOGGER_MODE.READ) {
            this.afterClose();
            return;
        }

        if (this.filePath == null) {
            throw new IllegalStateException("Cannot close without file path");
        }

        FileOutputStream os = null;
        try {
            os = new FileOutputStream(this.filePath);
        } catch (FileNotFoundException e) {
            System.out.println("Settings file not found");
        }

        try {
            assert os != null;
            os.write(this.toJSON().toString().getBytes());
            os.close();
        } catch (IOException e) {
            System.out.println("Error while saving settings");
        }

        this.afterClose();
    }

    public void close(String filePath, String mapText) {
        this.filePath = filePath;
        this.mapText = mapText;

        this.close();
    }

    public void afterClose() {
        this.filePath = null;
        this.mapText = null;
        this.entries.clear();
        this.entries = null;
        this.index = 0;
    }

    public Map<Integer, Pair<Integer, Integer>> getGhostsAssociations() {
        if (this.mode == LOGGER_MODE.WRITE) return null;

        if (this.entries.size() == 0) {
            throw new IllegalStateException("Cannot get associations from empty log");
        }

        LogEntry entry = this.entries.get(0);

        if (entry.items().size() == 0) {
            throw new IllegalStateException("Cannot get associations from empty entry");
        }

        Map<Integer, Pair<Integer, Integer>> associations = new HashMap<>();

        if (entry.items().size() == 0) {
            throw new IllegalStateException("Cannot get associations from empty entry");
        }

        for (int i = 0; i < entry.items().size(); i++) {
            LogItem item = entry.items().get(i);
            if (item.character() == CHARACTER_MAP.GHOST) {
                associations.put(i, item.from());
            }
        }

        return associations;
    }
}
