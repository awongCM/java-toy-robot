package com.andywong.cli;

import com.andywong.application.RobotSimulator;
import com.andywong.components.Direction;
import com.andywong.components.Grid;
import com.andywong.components.Location;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TextInputInterface {

    private static final String INVALID_PLACE_PARAMETERS = "The PLACE command must have three parameters: X,Y,DIRECTION";
    private static final String INVALID_LOCATION_PARAMETERS = "Location must be integers";
    private static final int DIRECTION_POSITION = 2;
    private static final int X_POSITION = 0;
    private static final int Y_POSITION = 1;

    private static Grid grid = Grid.getInstance();
    private static RobotSimulator simulator = new RobotSimulator(grid);

    static void resetForTesting() {
        Grid.resetForTesting();
        grid = Grid.getInstance();
        simulator = new RobotSimulator(grid);
    }

    public TextInputInterface(){}

    public static void main(String[] args) {

        List<String> commands = getCommands(args);

        for (String command: commands) {
            String [] commandlineAsArray = command.trim().split(" ");
            String [] commandInput = getCommandWithOrWithoutParams(commandlineAsArray);

            try{
                runCommand(commandInput);
            } catch (Exception e) {
                System.out.println("Something went wrong: " + e.getMessage());
            }
        }
    }

    private static void placePosition(String paramsAsString) {

        if (paramsAsString == null) {
            throw new IllegalArgumentException(INVALID_PLACE_PARAMETERS);
        }

        String[] locationParams = paramsAsString.split(",");

        if (locationParams.length != 3) {
            throw new IllegalArgumentException(INVALID_PLACE_PARAMETERS);
        }

        for (int i = 0; i < locationParams.length; i++) {
            locationParams[i] = locationParams[i].trim();
        }

        Location location = getLocationFromParams(locationParams);
        Direction direction = Direction.find(locationParams[DIRECTION_POSITION]);
        simulator.place(location, direction);
    }

    private static void reportRobotPosition() {
        simulator.report().ifPresent(System.out::println);
    }

    public static void runCommand(String [] commandInput) {
        String commandOnly = commandInput[0];

        Command command = Command.matchAndReturnValidCommand(commandOnly);

        switch (command) {
            case PLACE:
                placePosition(commandInput[1]);
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
                throw new RuntimeException("unknown command detected");
        }
    }

    private static Location getLocationFromParams(String [] params) {
        Integer x;
        Integer y;

        try{
            x = Integer.valueOf(params[X_POSITION]);
            y = Integer.valueOf(params[Y_POSITION]);
        }catch(NumberFormatException e) {
            throw new IllegalArgumentException(INVALID_LOCATION_PARAMETERS);
        }

        return new Location(x, y);

    }

    private static String[] getCommandWithOrWithoutParams(String[] commandlineAsArray) {

        String command =  commandlineAsArray[0];

        String[] params = commandlineAsArray.length < 2 ? null : commandlineAsArray[1].split(",");

        String [] commandInput = {command , Arrays.toString(params)
                .replace("[","").replace("]","")};

        return commandInput;

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
            throw new IllegalArgumentException("Could not read commands from file: " + filePath, e);
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
