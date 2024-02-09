package edu.project;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MazeTest {
    Maze maze = new Maze(new EllerAlgorithm(11), 5, 5);
    private static Stream<Arguments> provideArgumentsForMazeExceptionTest() {
        return Stream.of(
            Arguments.of(new Coordinate(0, 0)),
            Arguments.of(new Coordinate(0, 4)),
            Arguments.of(new Coordinate(8, 0)),
            Arguments.of(new Coordinate(2, 2))
        );
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForMazeExceptionTest")
    void mazeStartExceptionTest(Coordinate startTest) {
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            maze.setStartPoint(startTest);
        });
        assertEquals("Illegal start point.", e.getMessage());
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForMazeExceptionTest")
    void mazeExitExceptionTest(Coordinate exitTest) {
        Exception e = assertThrows(IllegalArgumentException.class, () -> {
            maze.setExitPoint(exitTest);
        });
        assertEquals("Illegal exit point.", e.getMessage());
    }
}
