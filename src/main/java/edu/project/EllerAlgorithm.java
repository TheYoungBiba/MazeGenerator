package edu.project;

import java.util.Arrays;
import java.util.Random;

public class EllerAlgorithm implements Generator {
    private int[][] sets;
    private int[][] preRenderedGrid;
    private Random random;

    public EllerAlgorithm(int randomSeed) {
        random = new Random(randomSeed);
    }

    public EllerAlgorithm() {
        random = new Random();
    }

    @Override
    public int[][] generateMaze(int rows, int cols) {
        sets = new int[rows][cols];
        preRenderedGrid = new int[2 * rows + 1][2 * cols + 1];
        initPreRenderedGrid();
        initSetsInFirstLine();
        generateRightWalls(0);
        generateBottomWalls(0);
        for (int row = 1; row < sets.length; row++) {
            initNewLine(row);
            generateRightWalls(row);
            generateBottomWalls(row);
        }
        finalizeMaze();
        return preRenderedGrid;
    }

    private void initPreRenderedGrid() {
        for (int i = 0; i < preRenderedGrid.length; i++) {
            preRenderedGrid[i][0] = 1;
            preRenderedGrid[i][preRenderedGrid[i].length - 1] = 1;
        }
        for (int j = 1; j < preRenderedGrid[0].length; j++) {
            preRenderedGrid[0][j] = 1;
            preRenderedGrid[preRenderedGrid.length - 1][j] = 1;
        }
    }

    private void setRightWall(int row, int col) {
        preRenderedGrid[row * 2 + 1][col * 2 + 2] = 1;
        preRenderedGrid[row * 2 + 2][col * 2 + 2] = 1;
        if (row > 0) {
            preRenderedGrid[row * 2][col * 2 + 2] = 1;
        }
    }

    private void setBottomWall(int row, int col) {
        preRenderedGrid[row * 2 + 2][col * 2 + 1] = 1;
        preRenderedGrid[row * 2 + 2][col * 2 + 2] = 1;
        if (col > 0) {
            preRenderedGrid[row * 2 + 2][col * 2] = 1;
        }
    }

    private void deleteRightWall(int row, int col) {
        preRenderedGrid[row * 2 + 1][col * 2 + 2] = 0;
    }

    private boolean isBottomWall(int row, int col) {
        return preRenderedGrid[row * 2 + 2][col * 2 + 1] == 1;
    }

    private void initSetsInFirstLine() {
        for (int i = 0; i < sets[0].length; i++) {
            sets[0][i] = i + 1;
        }
    }

    private void mergeSets(int row, int fromSet, int toSet) {
        for (int i = 0; i < sets[row].length; i++) {
            if (sets[row][i] == fromSet) {
                sets[row][i] = toSet;
            }
        }
    }

    private void generateRightWalls(int row) {
        for (int i = 0; i < sets[row].length - 1; i++) {
            boolean toStayWall = random.nextBoolean();
            if (toStayWall) {
                setRightWall(row, i);
            } else if (sets[row][i] == sets[row][i + 1]) {
                setRightWall(row, i);
            } else {
                mergeSets(row, sets[row][i + 1], sets[row][i]);
            }
        }
    }

    private boolean isPossibleToStayBottom(int row, int set) {
        int count = 0;
        for (int i = 0; i < sets[row].length; i++) {
            if (sets[row][i] == set && !isBottomWall(row, i)) {
                count++;
            }
        }
        return count > 1;
    }

    private void generateBottomWalls(int row) {
        for (int i = 0; i < sets[row].length; i++) {
            boolean toStayWall = random.nextBoolean();
            if (toStayWall && isPossibleToStayBottom(row, sets[row][i])) {
                setBottomWall(row, i);
            }
        }
    }

    private void initNewLine(int row) {
        sets[row] = sets[row - 1];
        for (int i = 0; i < sets[row].length; i++) {
            if (isBottomWall(row - 1, i)) {
                sets[row][i] = Arrays.stream(sets).flatMapToInt(ints -> Arrays.stream(ints)).max().getAsInt() + 1;
            }
        }
    }

    private void finalizeMaze() {
        for (int i = 0; i < sets[sets.length - 1].length - 1; i++) {
            if (sets[sets.length - 1][i] != sets[sets.length - 1][i + 1]) {
                deleteRightWall(sets.length - 1, i);
                mergeSets(sets.length - 1, sets[sets.length - 1][i + 1], sets[sets.length - 1][i]);
            }
        }
    }
}

