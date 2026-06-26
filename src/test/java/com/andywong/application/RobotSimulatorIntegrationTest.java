package com.andywong.application;

import com.andywong.domain.Direction;
import com.andywong.domain.Position;
import com.andywong.domain.Robot;
import com.andywong.domain.Table;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RobotSimulatorIntegrationTest {

    private RobotSimulator simulator;

    @BeforeEach
    void setUp() {
        simulator = new RobotSimulator(new Table(new Robot()));
    }

    @Test
    void example1_placeMoveReport() {
        simulator.place(new Position(0, 0), Direction.NORTH);
        simulator.move();
        assertEquals(Optional.of("0,1,NORTH"), simulator.report());
    }

    @Test
    void example2_placeLeftReport() {
        simulator.place(new Position(0, 0), Direction.NORTH);
        simulator.left();
        assertEquals(Optional.of("0,0,WEST"), simulator.report());
    }

    @Test
    void example3_placeMoveMoveLeftMoveReport() {
        simulator.place(new Position(1, 2), Direction.EAST);
        simulator.move();
        simulator.move();
        simulator.left();
        simulator.move();
        assertEquals(Optional.of("3,3,NORTH"), simulator.report());
    }
}
