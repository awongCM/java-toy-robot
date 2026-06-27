package com.andywong.cli;

import com.andywong.application.RobotSimulator;
import com.andywong.domain.Direction;
import com.andywong.domain.Position;
import com.andywong.domain.Robot;
import com.andywong.domain.Table;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TextInputInterface {

    private static final String INVALID_PLACE_PARAMETERS = "The PLACE command must have three parameters: X,Y,DIRECTION";
    private static final String INVALID_LOCATION_PARAMETERS = "Location must be integers";
    private static final int DIRECTION_POSITION = 2;
    private static final int X_POSITION = 0;
    private static final int Y_POSITION = 1;

    private final RobotSimulator simulator;

    public TextInputInterface() {
        this(new RobotSimulator(new Table(new Robot())));
    }

    public TextInputInterface(RobotSimulator simulator) {
        this.simulator = simulator;
    }

    public static void main(String[] args) {
        TextInputInterface app = new TextInputInterface();
        List<String> commands = getCommands(args);

        for (String commandLine : commands) {
            try {
                app.runCommand(commandLine);
            } catch (Exception e) {
                System.out.println("Something went wrong: " + e.getMessage());
            }
        }
    }

    public void runCommand(String commandLine) {
        runCommand(CommandParser.parse(commandLine));
    }

    public void runCommand(CommandParser.ParsedCommand parsedCommand) {
        Command command = parsedCommand.command();

        switch (command) {
            case PLACE:
                placePosition(parsedCommand.placeParameters());
                break;
            case MOVE:
                simulator.move();
                break;
            case LEFT:
                simulator.left();
                break;
            case RIGHT:
                simulator.right();
                break;
            case REPORT:
                reportRobotPosition();
                break;
            default:
                throw new CommandParseException("Unknown command detected");
        }
    }

    private void placePosition(String paramsAsString) {

        if (paramsAsString == null || paramsAsString.isBlank()) {
            throw new CommandParseException(INVALID_PLACE_PARAMETERS);
        }

        String[] locationParams = paramsAsString.split(",");

        if (locationParams.length != 3) {
            throw new CommandParseException(INVALID_PLACE_PARAMETERS);
        }

        for (int i = 0; i < locationParams.length; i++) {
            locationParams[i] = locationParams[i].trim();
        }

        Position position = getPositionFromParams(locationParams);
        Direction direction = Direction.find(locationParams[DIRECTION_POSITION]);
        simulator.place(position, direction);
    }

    private void reportRobotPosition() {
        simulator.report().ifPresent(System.out::println);
    }

    private static Position getPositionFromParams(String [] params) {
        int x;
        int y;

        try{
            x = Integer.parseInt(params[X_POSITION]);
            y = Integer.parseInt(params[Y_POSITION]);
        } catch (NumberFormatException e) {
            throw new CommandParseException(INVALID_LOCATION_PARAMETERS);
        }

        return new Position(x, y);

    }

    private static List<String> getCommands(String[] args) {
        if (args != null && args.length > 0 && args[0] != null && !args[0].isBlank()) {
            return readCommandsFromFile(args[0].trim());
        }
        return getLineFeedFromInput();
    }

    private static List<String> readCommandsFromFile(String filePath) {
        try {
            return Files.readAllLines(Path.of(filePath)).stream()
                    .takeWhile(line -> !line.isEmpty())
                    .toList();
        } catch (IOException e) {
            throw new CommandParseException("Could not read commands from file: " + filePath, e);
        }
    }

    private static List<String> getLineFeedFromInput() {

        Scanner scanner = new Scanner(System.in);
        String line;

        List<String> commands = new ArrayList<String>();

        while (!(line = scanner.nextLine()).isEmpty()) {
            commands.add(line);
        }

        scanner.close();

        return  commands;

    }


}
