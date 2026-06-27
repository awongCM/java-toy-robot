package com.andywong.cli;

/**
 * Thrown when a command line cannot be parsed (malformed PLACE, unknown command, unreadable input file).
 * Parsing errors fail fast; they are not silently ignored like invalid domain operations.
 */
public class CommandParseException extends RuntimeException {

    public CommandParseException(String message) {
        super(message);
    }

    public CommandParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
