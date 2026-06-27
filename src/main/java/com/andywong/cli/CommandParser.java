package com.andywong.cli;

/**
 * Parses a single command line into a command and optional PLACE parameters.
 */
public final class CommandParser {

    private CommandParser() {}

    public record ParsedCommand(Command command, String placeParameters) {}

    public static ParsedCommand parse(String line) {
        String trimmed = line.trim();
        String commandToken;
        String placeParameters = null;

        int spaceIndex = trimmed.indexOf(' ');
        if (spaceIndex < 0) {
            commandToken = trimmed;
        } else {
            commandToken = trimmed.substring(0, spaceIndex);
            placeParameters = trimmed.substring(spaceIndex + 1).trim();
        }

        Command command = Command.matchAndReturnValidCommand(commandToken);
        if (command == null) {
            throw new CommandParseException("Unknown command: " + commandToken);
        }
        return new ParsedCommand(command, placeParameters);
    }
}
