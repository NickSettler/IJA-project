package ija.ija2022.project.astar;

import ija.ija2022.project.game.GameModel;
import ija.ija2022.project.game.Maze;
import ija.ija2022.project.tool.common.CommonField;

import java.awt.*;
import java.util.*;
import java.util.List;

public class AStarPathFinder {
    private final GameModel gameModel;
    private final int[][] heuristic;
    private final PriorityQueue<Node> openSet;
    private final Set<Node> closedSet;

    public AStarPathFinder(GameModel gameModel, Maze maze) {
        this.gameModel = gameModel;
        this.heuristic = new int[maze.numRows()][maze.numCols()];
        this.openSet = new PriorityQueue<>(Comparator.comparingInt(Node::getF));
        this.closedSet = new HashSet<>();
    }

    public List<CommonField.Direction> findPath(Point start, Point target) {
        for (int x = 0; x < heuristic.length; x++) {
            for (int y = 0; y < heuristic[0].length; y++) {
                heuristic[x][y] = Math.abs(x - target.x) + Math.abs(y - target.y);
            }
        }
        Node startNode = new Node(start, null, 0, heuristic[start.x][start.y], null);
        openSet.offer(startNode);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            if (current.getPoint().equals(target)) {
                List<CommonField.Direction> path = new ArrayList<>();
                while (current.getParent() != null) {
                    path.add(current.getDirection());
                    current = current.getParent();
                }
                Collections.reverse(path);
                return path;
            }
            for (CommonField.Direction direction: CommonField.Direction.values()) {
                Point neighbourPoint = null; // TODO
                if (gameModel.getMaze().getField(neighbourPoint.x, neighbourPoint.y).isEmpty() && !closedSet.contains(neighbourPoint)) { // TODO
                    int g = current.getG();
                    int h = heuristic[neighbourPoint.x][neighbourPoint.y];
                    Node neighbourNode = new Node(neighbourPoint, current, g, h, direction);
                    openSet.offer(neighbourNode);
                }
            }
            closedSet.add(current);
        }
        return Collections.emptyList();
    }
}
