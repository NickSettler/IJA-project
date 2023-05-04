package ija.ija2022.project.astar;

import ija.ija2022.project.game.BaseField;
import ija.ija2022.project.game.GameModel;
import ija.ija2022.project.game.Maze;
import ija.ija2022.project.tool.common.CommonField;

import java.util.*;

public class AStarPathFinder {
    private final GameModel gameModel;
    private final Maze maze;
    private final int[][] heuristic;
    private final PriorityQueue<Node> openSet;
    private final Set<Node> closedSet;

    public AStarPathFinder(GameModel gameModel, Maze maze) {
        this.gameModel = gameModel;
        this.maze = maze;
        this.heuristic = new int[maze.numRows()][maze.numCols()];
        this.openSet = new PriorityQueue<>(Comparator.comparingInt(Node::getF));
        this.closedSet = new HashSet<>();
    }

    public List<CommonField.Direction> findPath(int startX, int startY, int endX, int endY) {
        for (int x = 0; x < heuristic.length; x++) {
            for (int y = 0; y < heuristic[0].length; y++) {
                heuristic[x][y] = Math.abs(x - endX) + Math.abs(y - endY);
            }
        }
        Node startNode = new Node((BaseField) maze.getField(startX, startY), null, 0, heuristic[startX][startY], null);
        openSet.offer(startNode);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            if (current.getPoint().equals(maze.getField(endX, endY))) {
                List<CommonField.Direction> path = new ArrayList<>();
                while (current.getParent() != null) {
                    path.add(current.getDirection());
                    current = current.getParent();
                }
                Collections.reverse(path);
                return path;
            }
            for (CommonField.Direction direction : CommonField.Direction.values()) {
                BaseField neighbourField = (BaseField) this.gameModel.getMaze().getField(current.getField().getRow(), current.getField().getCol()).nextField(direction);
                int g = current.getG();
                int h = heuristic[neighbourField.getRow()][neighbourField.getCol()];
                Node neighbourNode = new Node(neighbourField, current, g, h, direction);
                if (neighbourField.isEmpty() && !closedSet.contains(neighbourNode)) { // TODO
                    openSet.offer(neighbourNode);
                }
            }
            closedSet.add(current);
        }
        return Collections.emptyList();
    }
}
