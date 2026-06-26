package com.andywong.application;

import com.andywong.components.Direction;
import com.andywong.components.Grid;
import com.andywong.components.Location;
import com.andywong.components.Robot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RobotSimulatorTest {

    private RobotSimulator simulator;

    @BeforeEach
    void setUp() {
        simulator = new RobotSimulator(new Grid(new Robot()));
    }

    @Test
    void move_beforePlace_isIgnored() {
        simulator.move();
        assertEquals(Optional.empty(), simulator.report());
    }

    @Test
    void left_beforePlace_isIgnored() {
        simulator.left();
        assertEquals(Optional.empty(), simulator.report());
    }

    @Test
    void right_beforePlace_isIgnored() {
        simulator.right();
        assertEquals(Optional.empty(), simulator.report());
    }

    @Test
    void report_beforePlace_returnsEmpty() {
        assertTrue(simulator.report().isEmpty());
    }

    @Test
    void move_offTable_isIgnored() {
        simulator.place(new Location(0, 0), Direction.WEST);
        simulator.move();
        assertEquals(Optional.of("0,0,WEST"), simulator.report());
    }

    @Test
    void invalidPlace_isIgnored() {
        simulator.place(new Location(5, 5), Direction.NORTH);
        assertEquals(Optional.empty(), simulator.report());
    }

    @Test
    void validPlace_afterInvalidPlace_succeeds() {
        simulator.place(new Location(5, 5), Direction.NORTH);
        simulator.place(new Location(0, 0), Direction.NORTH);
        assertEquals(Optional.of("0,0,NORTH"), simulator.report());
    }

    @Test
    void move_afterValidPlace_updatesPosition() {
        simulator.place(new Location(0, 0), Direction.NORTH);
        simulator.move();
        assertEquals(Optional.of("0,1,NORTH"), simulator.report());
    }
}
