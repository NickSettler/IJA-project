package ija.ija2022.project.astar;

import ija.ija2022.project.fields.BaseField;
import ija.ija2022.project.fields.CommonField;
import ija.ija2022.project.fields.WallField;
import ija.ija2022.project.maze.CommonMaze;

import java.util.*;

public class AStarPathFinder {
    private final CommonMaze maze;
    private final int[][] heuristic;
    private final PriorityQueue<Node> openSet;
    private final Set<Node> closedSet;

    public AStarPathFinder(CommonMaze maze) {
        this.maze = maze;
        this.heuristic = new int[maze.numRows()][maze.numCols()];
        this.openSet = new PriorityQueue<>(Comparator.comparingInt(Node::getF));
        this.closedSet = new HashSet<>();
    }

    public List<int[]> findPath(int startX, int startY, int endX, int endY) {
        for (int x = 0; x < heuristic.length; x++) {
            for (int y = 0; y < heuristic[0].length; y++) {
                heuristic[x][y] = Math.abs(x - endX) + Math.abs(y - endY);
            }
        }
        Node startNode = new Node((BaseField) maze.getField(startX, startY), null, 0, heuristic[startX][startY], null);
        openSet.offer(startNode);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            if (current.getField().equals(maze.getField(endX, endY))) {
                List<int[]> path = new ArrayList<>();
                while (current.getParent() != null) {
                    path.add(new int[]{current.getField().getRow(), current.getField().getCol()});
                    current = current.getParent();
                }
                path.add(new int[]{current.getField().getRow(), current.getField().getCol()});
                Collections.reverse(path);
                return path;
            }
            for (CommonField.Direction direction : CommonField.Direction.values()) {
                BaseField neighbourField = (BaseField) maze.getField(current.getField().getRow(), current.getField().getCol()).nextField(direction);
                int g = current.getG() + 1;
                int h = heuristic[neighbourField.getRow()][neighbourField.getCol()];
                Node neighbourNode = new Node(neighbourField, current, g, h, direction);
                if (!(neighbourField instanceof WallField) && !closedSet.contains(neighbourNode)) {
                    openSet.offer(neighbourNode);
                }
            }
            closedSet.add(current);
        }
        return Collections.emptyList();
    }
}
