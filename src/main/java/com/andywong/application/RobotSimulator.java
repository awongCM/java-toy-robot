package com.andywong.application;

import com.andywong.domain.Direction;
import com.andywong.domain.Location;
import com.andywong.domain.Table;

import java.util.Optional;

/**
 * Applies robot commands according to the challenge spec: invalid placements,
 * pre-PLACE commands, and moves that would fall off the table are silently ignored.
 */
public class RobotSimulator {

    private final Table table;

    public RobotSimulator(Table table) {
        this.table = table;
    }

    public void place(Location location, Direction direction) {
        try {
            table.place(location, direction);
        } catch (IllegalArgumentException e) {
            // Out-of-bounds or otherwise invalid placement — ignore per spec
        }
    }

    public void move() {
        try {
            table.move();
        } catch (IllegalStateException e) {
            // Not placed or would fall off the table — ignore per spec
        }
    }

    public void left() {
        try {
            table.left();
        } catch (IllegalStateException e) {
            // Not placed — ignore per spec
        }
    }

    public void right() {
        try {
            table.right();
        } catch (IllegalStateException e) {
            // Not placed — ignore per spec
        }
    }

    /**
     * Returns report output when the robot is placed; empty when not placed (silent no-op).
     */
    public Optional<String> report() {
        String result = table.report();
        if ("The robot hasn't been placed".equals(result)) {
            return Optional.empty();
        }
        return Optional.of(result);
    }
}
