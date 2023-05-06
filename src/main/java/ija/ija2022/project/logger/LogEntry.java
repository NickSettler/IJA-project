package ija.ija2022.project.logger;

import ija.ija2022.project.maze.configure.CHARACTER_MAP;
import org.json.JSONArray;

import java.util.ArrayList;

public record LogEntry(ArrayList<LogItem> items) {
    public LogEntry {
        if (items == null) {
            throw new IllegalArgumentException("Items cannot be null");
        }
    }

    public JSONArray toJSONArray() {
        JSONArray array = new JSONArray();

        for (LogItem item : this.items) {
            array.put(item.toString());
        }

        return array;
    }

    public ArrayList<LogItem> getGhosts() {
        return this.items.stream()
                .filter(item -> item.character() == CHARACTER_MAP.GHOST)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public static LogEntry fromJSONArray(JSONArray array) {
        ArrayList<LogItem> items = new ArrayList<>(array.length());

        for (int i = 0; i < array.length(); i++) {
            String string = array.getString(i);
            items.add(LogItem.fromString(string));
        }

        return new LogEntry(items);
    }
}
