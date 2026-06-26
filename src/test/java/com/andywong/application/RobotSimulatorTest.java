package com.andywong.application;

import com.andywong.domain.Direction;
import com.andywong.domain.Position;
import com.andywong.domain.Robot;
import com.andywong.domain.Table;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RobotSimulatorTest {

    private RobotSimulator simulator;

    @BeforeEach
    void setUp() {
        simulator = new RobotSimulator(new Table(new Robot()));
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
        simulator.place(new Position(0, 0), Direction.WEST);
        simulator.move();
        assertEquals(Optional.of("0,0,WEST"), simulator.report());
    }

    @Test
    void invalidPlace_isIgnored() {
        simulator.place(new Position(5, 5), Direction.NORTH);
        assertEquals(Optional.empty(), simulator.report());
    }

    @Test
    void validPlace_afterInvalidPlace_succeeds() {
        simulator.place(new Position(5, 5), Direction.NORTH);
        simulator.place(new Position(0, 0), Direction.NORTH);
        assertEquals(Optional.of("0,0,NORTH"), simulator.report());
    }

    @Test
    void move_afterValidPlace_updatesPosition() {
        simulator.place(new Position(0, 0), Direction.NORTH);
        simulator.move();
        assertEquals(Optional.of("0,1,NORTH"), simulator.report());
    }
}
