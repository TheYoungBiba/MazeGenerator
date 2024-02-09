package edu.project;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFS implements PathFinder {
    private int rows;
    private int cols;
    private int[][] preRenderedMaze;
    private final int[] shiftCheckX = {-1, 0, 1, 0};
    private final int[] shiftCheckY = {0, 1, 0, -1};
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private boolean[][] isVisited;
    private int[][] parentX;
    private int[][] parentY;
    private Queue<Coordinate> queue;

    public BFS(Maze maze) {
        this.preRenderedMaze = maze.getPreRenderedGrid();
        this.rows = preRenderedMaze.length;
        this.cols = preRenderedMaze[0].length;
        if (maze.getStartPoint() == null) {
            throw new IllegalArgumentException("There is no start position in maze.");
        }
        if (maze.getExitPoint() == null) {
            throw new IllegalArgumentException("There is no exit position in maze.");
        }
        this.startX = maze.getStartPoint().x();
        this.startY = maze.getStartPoint().y();
        this.endX = maze.getExitPoint().x();
        this.endY = maze.getExitPoint().y();
        this.isVisited = new boolean[rows][cols];
        this.parentX = new int[rows][cols];
        this.parentY = new int[rows][cols];
        this.queue = new LinkedList<>();
    }

    private boolean isValid(int[][] preRenderedMaze, boolean[][] isVisited, int x, int y) {
        return (x >= 0 && x < rows && y >= 0 && y < cols
            && (preRenderedMaze[x][y] == 0 || preRenderedMaze[x][y] == 2) && !isVisited[x][y]);
    }

    private List<Coordinate> foundedPath() {
        int tempXCoord = endX;
        int tempYCoord = endY;
        List<Coordinate> path = new LinkedList<>();
        while (tempXCoord != startX || tempYCoord != startY) {
            path.add(new Coordinate(tempXCoord, tempYCoord));
            int prevX = parentX[tempXCoord][tempYCoord];
            int prevY = parentY[tempXCoord][tempYCoord];
            tempXCoord = prevX;
            tempYCoord = prevY;
        }
        path.add(new Coordinate(startX, startY));
        return path;
    }

    private void nearPointCheck(int x, int y) {
        for (int i = 0; i < shiftCheckX.length; i++) {
            int newX = x + shiftCheckX[i];
            int newY = y + shiftCheckY[i];
            if (isValid(preRenderedMaze, isVisited, newX, newY)) {
                isVisited[newX][newY] = true;
                queue.add(new Coordinate(newX, newY));
                parentX[newX][newY] = x;
                parentY[newX][newY] = y;
            }
        }
    }

    @Override
    public List<Coordinate> solve() {
        isVisited[startX][startY] = true;
        queue.add(new Coordinate(startX, startY));
        while (!queue.isEmpty()) {
            Coordinate current = queue.poll();
            int x = current.x();
            int y = current.y();
            if (x == endX && y == endY) {
                return foundedPath().reversed();
            }
            nearPointCheck(x, y);
        }
        return new LinkedList<>();
    }
}
