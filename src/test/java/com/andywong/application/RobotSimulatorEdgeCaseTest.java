package com.andywong.application;

import com.andywong.domain.Direction;
import com.andywong.domain.Position;
import com.andywong.domain.Robot;
import com.andywong.domain.Table;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RobotSimulatorEdgeCaseTest {

    private RobotSimulator simulator;

    @BeforeEach
    void setUp() {
        simulator = new RobotSimulator(new Table(new Robot()));
    }

    @ParameterizedTest(name = "edge {0},{1} facing {2} — move off table ignored")
    @MethodSource("tableEdgeCases")
    void moveOffTable_atEachEdge_isIgnored(int x, int y, Direction direction, String expectedReport) {
        simulator.place(new Position(x, y), direction);
        simulator.move();
        assertEquals(Optional.of(expectedReport), simulator.report());
    }

    static Stream<Arguments> tableEdgeCases() {
        return Stream.of(
                Arguments.of(0, 4, Direction.NORTH, "0,4,NORTH"),
                Arguments.of(0, 0, Direction.SOUTH, "0,0,SOUTH"),
                Arguments.of(0, 0, Direction.WEST, "0,0,WEST"),
                Arguments.of(4, 0, Direction.EAST, "4,0,EAST")
        );
    }

    @ParameterizedTest(name = "corner {0},{1} facing {2} — invalid move ignored")
    @MethodSource("cornerInvalidMoveCases")
    void moveOffTable_atCorner_isIgnored(int x, int y, Direction direction, String expectedReport) {
        simulator.place(new Position(x, y), direction);
        simulator.move();
        assertEquals(Optional.of(expectedReport), simulator.report());
    }

    static Stream<Arguments> cornerInvalidMoveCases() {
        return Stream.of(
                Arguments.of(0, 0, Direction.SOUTH, "0,0,SOUTH"),
                Arguments.of(0, 0, Direction.WEST, "0,0,WEST"),
                Arguments.of(4, 0, Direction.SOUTH, "4,0,SOUTH"),
                Arguments.of(4, 0, Direction.EAST, "4,0,EAST"),
                Arguments.of(0, 4, Direction.NORTH, "0,4,NORTH"),
                Arguments.of(0, 4, Direction.WEST, "0,4,WEST"),
                Arguments.of(4, 4, Direction.NORTH, "4,4,NORTH"),
                Arguments.of(4, 4, Direction.EAST, "4,4,EAST")
        );
    }

    @ParameterizedTest(name = "{0} consecutive invalid moves from {1},{2} {3}")
    @MethodSource("consecutiveInvalidMoveCases")
    void multipleInvalidMoves_areIgnored(
            int moveCount, int x, int y, Direction direction, String expectedReport) {
        simulator.place(new Position(x, y), direction);
        for (int i = 0; i < moveCount; i++) {
            simulator.move();
        }
        assertEquals(Optional.of(expectedReport), simulator.report());
    }

    static Stream<Arguments> consecutiveInvalidMoveCases() {
        return Stream.of(
                Arguments.of(3, 0, 0, Direction.WEST, "0,0,WEST"),
                Arguments.of(5, 4, 4, Direction.NORTH, "4,4,NORTH"),
                Arguments.of(2, 0, 4, Direction.WEST, "0,4,WEST")
        );
    }

    @ParameterizedTest(name = "re-PLACE to {0},{1} {2} after moving")
    @MethodSource("rePlaceCases")
    void rePlace_midSession_updatesPosition(
            int newX, int newY, Direction newDirection, String expectedReport) {
        simulator.place(new Position(0, 0), Direction.NORTH);
        simulator.move();
        simulator.place(new Position(newX, newY), newDirection);
        assertEquals(Optional.of(expectedReport), simulator.report());
    }

    static Stream<Arguments> rePlaceCases() {
        return Stream.of(
                Arguments.of(2, 2, Direction.EAST, "2,2,EAST"),
                Arguments.of(4, 0, Direction.SOUTH, "4,0,SOUTH"),
                Arguments.of(0, 4, Direction.WEST, "0,4,WEST")
        );
    }
}
