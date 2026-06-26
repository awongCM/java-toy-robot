package com.andywong.application;

import com.andywong.components.Direction;
import com.andywong.components.Grid;
import com.andywong.components.Location;

import java.util.Optional;

/**
 * Applies robot commands according to the challenge spec: invalid placements,
 * pre-PLACE commands, and moves that would fall off the table are silently ignored.
 */
public class RobotSimulator {

    private final Grid grid;

    public RobotSimulator(Grid grid) {
        this.grid = grid;
    }

    public void place(Location location, Direction direction) {
        try {
            grid.place(location, direction);
        } catch (IllegalArgumentException e) {
            // Out-of-bounds or otherwise invalid placement — ignore per spec
        }
    }

    public void move() {
        try {
            grid.move();
        } catch (IllegalStateException e) {
            // Not placed or would fall off the table — ignore per spec
        }
    }

    public void left() {
        try {
            grid.left();
        } catch (IllegalStateException e) {
            // Not placed — ignore per spec
        }
    }

    public void right() {
        try {
            grid.right();
        } catch (IllegalStateException e) {
            // Not placed — ignore per spec
        }
    }

    /**
     * Returns report output when the robot is placed; empty when not placed (silent no-op).
     */
    public Optional<String> report() {
        String result = grid.report();
        if ("The robot hasn't been placed".equals(result)) {
            return Optional.empty();
        }
        return Optional.of(result);
    }
}
