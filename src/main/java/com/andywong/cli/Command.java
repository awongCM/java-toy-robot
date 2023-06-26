package com.andywong.cli;

public enum Command {
    PLACE,
    MOVE,
    LEFT,
    RIGHT,
    REPORT;

    public static Command matchAndReturnValidCommand(String command) {
        try {

            String sanitisedCommand = command.trim().toUpperCase();

            for (Command c: Command.values()) {
                if (c.name().equals(sanitisedCommand)) {
                    return c;
                }
            }

            return null;

        } catch(Exception e) {

            System.out.println("Unknown command detected");
            return null;
        }

    }
}


