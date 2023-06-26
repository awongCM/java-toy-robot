package com.andywong.cli;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandTest {

    @Test
    void matchAndReturnValidCommand_should_return_the_correct_command() {
        // Arrange
        String command = "LEFT";
        Command expected = Command.LEFT;

        // Act
        Command actual = Command.matchAndReturnValidCommand(command);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void matchAndReturnValidCommand_should_return_null_when_string_is_empty() {
        // Arrange
        String command = "";
        Command expected = null;

        // Act
        Command actual = Command.matchAndReturnValidCommand(command);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void matchAndReturnValidCommand_should_return_null_when_command_is_unknown() {
        // Arrange
        String command = "not a command";
        Command expected = null;

        // Act
        Command actual = Command.matchAndReturnValidCommand(command);

        // Assert
        assertEquals(expected, actual);
    }
}