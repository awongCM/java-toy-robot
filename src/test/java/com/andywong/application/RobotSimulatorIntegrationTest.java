package com.andywong.application;

import com.andywong.components.Grid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Canonical integration tests from the Robot Challenge spec.
 */
class RobotSimulatorIntegrationTest {

    private RobotSimulator simulator;

    @BeforeEach
    void setUp() {
        Grid.resetForTesting();
        simulator = new RobotSimulator(new Grid());
    }

    @Test
    void example1_placeMoveReport() {
        simulator.place(parseLocation("0,0"), com.andywong.components.Direction.NORTH);
        simulator.move();
        assertEquals(Optional.of("0,1,NORTH"), simulator.report());
    }

    @Test
    void example2_placeLeftReport() {
        simulator.place(parseLocation("0,0"), com.andywong.components.Direction.NORTH);
        simulator.left();
        assertEquals(Optional.of("0,0,WEST"), simulator.report());
    }

    @Test
    void example3_placeMoveMoveLeftMoveReport() {
        simulator.place(parseLocation("1,2"), com.andywong.components.Direction.EAST);
        simulator.move();
        simulator.move();
        simulator.left();
        simulator.move();
        assertEquals(Optional.of("3,3,NORTH"), simulator.report());
    }

    private static com.andywong.components.Location parseLocation(String coords) {
        String[] parts = coords.split(",");
        return new com.andywong.components.Location(
                Integer.parseInt(parts[0]),
                Integer.parseInt(parts[1])
        );
    }
}
