package edu.project;

import java.util.HashSet;
import java.util.List;

public class RenderMaze implements Renderer {
    public RenderMaze() {}

    final String wey = " ● ";
    final String wall = "███";
    final String pass = "   ";

    @Override
    public String render(Maze maze) {
        StringBuilder renderedMaze = new StringBuilder();
        renderedMaze.append("\n");
        int[][] preRenderedGrid = maze.getPreRenderedGrid();
        for (int i = 0; i < preRenderedGrid.length; i++) {
            for (int j = 0; j < preRenderedGrid[i].length; j++) {
                if (preRenderedGrid[i][j] == 2) {
                    renderedMaze.append(wey);
                } else if (preRenderedGrid[i][j] == 1) {
                    renderedMaze.append(wall);
                } else {
                    renderedMaze.append(pass);
                }
            }
            renderedMaze.append("\n");
        }
        return renderedMaze.toString();
    }

    @Override
    public String render(Maze maze, List<Coordinate> path) {
        HashSet<Coordinate> pathCoords = new HashSet<>(path);
        StringBuilder renderedMaze = new StringBuilder();
        renderedMaze.append("\n");
        if (pathCoords.isEmpty()) {
            renderedMaze.append("Path not found.\n");
        }
        int[][] preRenderedGrid = maze.getPreRenderedGrid();
        for (int i = 0; i < preRenderedGrid.length; i++) {
            for (int j = 0; j < preRenderedGrid[i].length; j++) {
                if (preRenderedGrid[i][j] == 1) {
                    renderedMaze.append(wall);
                } else if (path.contains(new Coordinate(i, j))) {
                    renderedMaze.append(wey);
                } else {
                    renderedMaze.append(pass);
                }
            }
            renderedMaze.append("\n");
        }
        return renderedMaze.toString();
    }
}
