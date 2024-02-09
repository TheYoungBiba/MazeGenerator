package edu.project;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BFSTest {
    private static Stream<Arguments> provideArgumentsSolveTest() {
        Maze testCase1 = new Maze(new EllerAlgorithm(11), 5, 5);
        testCase1.setPoints(new Coordinate(0, 1), new Coordinate(10, 9));
        List<Coordinate> referent1 = new LinkedList<>();
        Collections.addAll(referent1,
            new Coordinate(0, 1),
            new Coordinate(1, 1),
            new Coordinate(2, 1),
            new Coordinate(3, 1),
            new Coordinate(3, 2),
            new Coordinate(3, 3),
            new Coordinate(3, 4),
            new Coordinate(3, 5),
            new Coordinate(2, 5),
            new Coordinate(1, 5),
            new Coordinate(1, 6),
            new Coordinate(1, 7),
            new Coordinate(1, 8),
            new Coordinate(1, 9),
            new Coordinate(2, 9),
            new Coordinate(3, 9),
            new Coordinate(3, 8),
            new Coordinate(3, 7),
            new Coordinate(4, 7),
            new Coordinate(5, 7),
            new Coordinate(6, 7),
            new Coordinate(7, 7),
            new Coordinate(8, 7),
            new Coordinate(9, 7),
            new Coordinate(9, 8),
            new Coordinate(9, 9),
            new Coordinate(10, 9)
            );
        Maze testCase2 = new Maze(new EllerAlgorithm(3), 8, 7);
        testCase2.setPoints(new Coordinate(0, 1), new Coordinate(16, 13));
        List<Coordinate> referent2 = new LinkedList<>();
        Collections.addAll(referent2,
            new Coordinate(0, 1),
            new Coordinate(1, 1),
            new Coordinate(2, 1),
            new Coordinate(3, 1),
            new Coordinate(3, 2),
            new Coordinate(3, 3),
            new Coordinate(4, 3),
            new Coordinate(5, 3),
            new Coordinate(5, 4),
            new Coordinate(5, 5),
            new Coordinate(6, 5),
            new Coordinate(7, 5),
            new Coordinate(8, 5),
            new Coordinate(9, 5),
            new Coordinate(9, 6),
            new Coordinate(9, 7),
            new Coordinate(9, 8),
            new Coordinate(9, 9),
            new Coordinate(10, 9),
            new Coordinate(11, 9),
            new Coordinate(11, 10),
            new Coordinate(11, 11),
            new Coordinate(10, 11),
            new Coordinate(9, 11),
            new Coordinate(9, 12),
            new Coordinate(9, 13),
            new Coordinate(10, 13),
            new Coordinate(11, 13),
            new Coordinate(12, 13),
            new Coordinate(13, 13),
            new Coordinate(14, 13),
            new Coordinate(15, 13),
            new Coordinate(16, 13)
            );
        return Stream.of(
            Arguments.of(testCase1, referent1),
            Arguments.of(testCase2, referent2)
        );
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsSolveTest")
    void solveTest(Maze testCase, List<Coordinate> referent) {
        assertEquals(referent, new BFS(testCase).solve());
    }

    @Test
    void startExceptionTest() {
        Maze testCase = new Maze(new EllerAlgorithm(11), 5, 5);
        testCase.setExitPoint(new Coordinate(10, 9));
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            new BFS(testCase);
        });
        assertEquals("There is no start position in maze.", e.getMessage());
    }

    @Test
    void exitExceptionTest() {
        Maze testCase = new Maze(new EllerAlgorithm(11), 5, 5);
        testCase.setStartPoint(new Coordinate(0, 1));
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            new BFS(testCase);
        });
        assertEquals("There is no exit position in maze.", e.getMessage());
    }
}
