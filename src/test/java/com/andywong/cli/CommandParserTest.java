package com.andywong.cli;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CommandParserTest {

    @Test
    void parse_placeCommand() {
        CommandParser.ParsedCommand parsed = CommandParser.parse("PLACE 0,0,NORTH");

        assertEquals(Command.PLACE, parsed.command());
        assertEquals("0,0,NORTH", parsed.placeParameters());
    }

    @Test
    void parse_moveCommand() {
        CommandParser.ParsedCommand parsed = CommandParser.parse("MOVE");

        assertEquals(Command.MOVE, parsed.command());
        assertNull(parsed.placeParameters());
    }

    @Test
    void parse_trimsInput() {
        CommandParser.ParsedCommand parsed = CommandParser.parse("  REPORT  ");

        assertEquals(Command.REPORT, parsed.command());
        assertNull(parsed.placeParameters());
    }

    @Test
    void parse_unknownCommand_throws() {
        CommandParseException ex = assertThrows(
                CommandParseException.class,
                () -> CommandParser.parse("JUMP")
        );
        assertEquals("Unknown command: JUMP", ex.getMessage());
    }
}
