/**
 * Command processor class is used to process commands from game history logs.
 * Commands have the following grammar:
 * ```text
 * <change> ::= <object>:<coords>-<coords>
 * <object> ::= "G" | "S"
 * <coords> ::= "(" <int> "," <int> ")"
 * <int> ::= <digit> | <digit> <int>
 * <digit> ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
 * ```
 * Where:
 * - `<change>` is a change of object position
 * - `<object>` is an object type
 * - `<coords>` is a pair of coordinates
 * - `<int>` is an integer number
 * - `<digit>` is a digit
 *
 * @author xmoise01, Nikita Moiseev
 * @author xshevc01, Aleksandr Shevchenko
 */
package ija.ija2022.project.commands;

import ija.ija2022.project.fields.CommonField;
import ija.ija2022.project.logger.LogEntry;
import ija.ija2022.project.logger.LogItem;
import ija.ija2022.project.logger.LoggerController;
import ija.ija2022.project.maze.CommonMaze;
import ija.ija2022.project.maze.configure.CHARACTER_MAP;
import ija.ija2022.project.objects.CommonMazeObject;
import ija.ija2022.project.objects.GhostObject;

import java.util.*;

public class CommandProcessor {
    private final CommonMaze maze;

    private final Map<Integer, CommonMazeObject> ghostAssociations;

    public CommandProcessor(CommonMaze maze, LoggerController loggerController) {
        this.maze = maze;
        this.ghostAssociations = new HashMap<>();

        loggerController.getGhostsAssociations().forEach((index, association) -> {
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
                                this.maze.getPacman().incrLives();
                            }
                        });
            }
        }
    }

    public void processEntries(List<LogEntry> entries, boolean reverse) {
        if (reverse)
            Collections.reverse(entries);

        for (LogEntry entry : entries) {
            this.processEntry(entry, reverse);
        }
    }
}
