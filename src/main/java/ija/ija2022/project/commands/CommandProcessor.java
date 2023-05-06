package ija.ija2022.project.commands;

import ija.ija2022.project.fields.CommonField;
import ija.ija2022.project.logger.LogEntry;
import ija.ija2022.project.logger.LogItem;
import ija.ija2022.project.logger.LoggerController;
import ija.ija2022.project.maze.CommonMaze;
import ija.ija2022.project.maze.configure.CHARACTER_MAP;
import ija.ija2022.project.objects.CommonMazeObject;
import ija.ija2022.project.objects.GhostObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandProcessor {
    private CommonMaze maze;
    private LoggerController loggerController;

    private Map<Integer, CommonMazeObject> ghostAssociations;

    public CommandProcessor(CommonMaze maze, LoggerController loggerController) {
        this.maze = maze;
        this.loggerController = loggerController;
        this.ghostAssociations = new HashMap<>();

        this.loggerController.getGhostsAssociations().forEach((index, association) -> {
            CommonMazeObject object = maze.getField(association.getKey(), association.getValue()).get().get(0);
            this.ghostAssociations.put(index, object);
        });
    }

    public void processEntry(LogEntry entry) {
        this.processEntry(entry, false);
    }

    public void processEntry(LogEntry entry, boolean reverse) {
        if (entry == null || entry.items().size() == 0) return;

        for (int i = 0; i < entry.items().size(); i++) {
            LogItem item = entry.items().get(i);
            CommonField.Direction direction = reverse ? item.direction().opposite() : item.direction();

            if (item.character() == CHARACTER_MAP.PACMAN) {
                this.maze.getPacman().move(direction);
            } else if (item.character() == CHARACTER_MAP.GHOST) {
                boolean reviveGhost = (entry.getGhosts().size() > this.maze.ghosts().length) && reverse;

                Arrays.stream(this.maze.ghosts())
                        .filter(ghost -> ghost.getRow() == item.from(reverse).getKey() && ghost.getCol() == item.from(reverse).getValue())
                        .findFirst()
                        .ifPresentOrElse(ghost -> ghost.move(item.to(reverse).getKey(), item.to(reverse).getValue()), () -> {
                            if (reviveGhost) {
                                GhostObject ghost = new GhostObject(item.from().getKey(), item.from().getValue(), this.maze);
                                this.maze.putObject(ghost, item.from().getKey(), item.from().getValue());
                            }
                        });
            }
        }
    }

    public void processEntries(List<LogEntry> entries) {
        entries.forEach(this::processEntry);
    }
}
