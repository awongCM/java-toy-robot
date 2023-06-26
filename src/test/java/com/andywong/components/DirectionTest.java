package com.andywong.components;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DirectionTest {

    @Test
    void moveTowards_NORTH_should_return_0_1() {
        // Arrange
        final Location coordinates = new Location(0, 0);
        final Location expected = new Location(0, 1);

        // Act
        final Location actual = Direction.NORTH.moveTowards(coordinates);

        // Act
        assertEquals(expected, actual);
    }

    @Test
    void moveTowards_EAST_should_return_1_0() {
        // Arrange
        final Location coordinates = new Location(0, 0);
        final Location expected = new Location(1, 0);

        // Act
        final Location actual = Direction.EAST.moveTowards(coordinates);

        // Act
        assertEquals(expected, actual);
    }

    @Test
    void moveTowards_SOUTH_should_return_0_minus_1() {
        // Arrange
        final Location coordinates = new Location(0, 0);
        final Location expected = new Location(0, -1);

        // Act
        final Location actual = Direction.SOUTH.moveTowards(coordinates);

        // Act
        assertEquals(expected, actual);
    }

    @Test
    void moveTowards_WEST_should_return_minus_1_0() {
        // Arrange
        final Location coordinates = new Location(0, 0);
        final Location expected = new Location(-1, 0);

        // Act
        final Location actual = Direction.WEST.moveTowards(coordinates);

        // Act
        assertEquals(expected, actual);
    }

    @Test
    void find_should_return_the_correct_command() {
        // Arrange
        String command = "NORTH";
        Direction expected = Direction.NORTH;

        // Act
        Direction actual = Direction.find(command);

        // Act
        assertEquals(expected, actual);
    }

    @Test
    void find_should_return_null_when_string_is_empty() {
        // Arrange
        String command = "";

        // Act
        Direction actual = Direction.find(command);

        // Act
        assertNull(actual);
    }

    @Test
    void find_should_return_null_when_command_is_unknown() {
        // Arrange
        String command = "not a command";

        // Act
        Direction actual = Direction.find(command);

        // Act
        assertNull(actual);
    }
}