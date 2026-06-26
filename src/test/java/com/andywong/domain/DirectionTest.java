package com.andywong.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DirectionTest {

    @Test
    void moveTowards_NORTH_should_return_0_1() {
        final Position coordinates = new Position(0, 0);
        final Position expected = new Position(0, 1);

        assertEquals(expected, Direction.NORTH.moveTowards(coordinates));
    }

    @Test
    void moveTowards_EAST_should_return_1_0() {
        final Position coordinates = new Position(0, 0);
        final Position expected = new Position(1, 0);

        assertEquals(expected, Direction.EAST.moveTowards(coordinates));
    }

    @Test
    void moveTowards_SOUTH_should_return_0_minus_1() {
        final Position coordinates = new Position(0, 0);
        final Position expected = new Position(0, -1);

        assertEquals(expected, Direction.SOUTH.moveTowards(coordinates));
    }

    @Test
    void moveTowards_WEST_should_return_minus_1_0() {
        final Position coordinates = new Position(0, 0);
        final Position expected = new Position(-1, 0);

        assertEquals(expected, Direction.WEST.moveTowards(coordinates));
    }

    @Test
    void find_should_return_the_correct_command() {
        assertEquals(Direction.NORTH, Direction.find("NORTH"));
    }

    @Test
    void find_should_return_null_when_string_is_empty() {
        assertNull(Direction.find(""));
    }

    @Test
    void find_should_return_null_when_command_is_unknown() {
        assertNull(Direction.find("not a command"));
    }
}
