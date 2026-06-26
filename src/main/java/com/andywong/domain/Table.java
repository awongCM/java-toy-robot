package com.andywong.domain;

public class Table {

    private static final String ROBOT_NOT_PLACED_ERROR_MESSAGE = "The robot hasn't been placed";
    private static final String ROBOT_WILL_FALL_ERROR_MESSAGE = "The robot will fall";
    private static final String NO_LOCATION_ERROR_MESSAGE = "No Location is set";
    private static final String INVALID_LOCATION_ERROR_MESSAGE = "Location provided is invalid";
    private static final String NO_DIRECTION_ERROR_MESSAGE = "No direction is set";

    private static final int TABLE_HEIGHT = 5;
    private static final int TABLE_WIDTH = 5;
    private static final int LOCATION_MIN_VALUE = 0;

    private final Robot robot;

    public Table() {
        this(new Robot());
    }

    public Table(Robot robot) {
        this.robot = robot;
    }

    public void place(Location location, Direction direction) {
        if (location == null) {
            throw new IllegalArgumentException(NO_LOCATION_ERROR_MESSAGE);
        }

        if (!isValidCoordinate(location)) {
            throw new IllegalArgumentException(INVALID_LOCATION_ERROR_MESSAGE);
        }

        if (direction == null) {
            throw new IllegalArgumentException(NO_DIRECTION_ERROR_MESSAGE);
        }

        robot.setLocation(location);
        robot.setDirection(direction);
    }

    private static boolean isValidCoordinate(Location location) {
        Integer x = location.getX();
        if (x == null) return false;
        if (x < LOCATION_MIN_VALUE) return false;
        if (x >= TABLE_WIDTH) return false;

        Integer y = location.getY();
        if (y == null) return false;
        if (y < LOCATION_MIN_VALUE) return false;
        if (y >= TABLE_HEIGHT) return false;

        return true;
    }

    public void move() {
        if (!isRobotPlaced()) {
            throw new IllegalStateException(ROBOT_NOT_PLACED_ERROR_MESSAGE);
        }

        Location nextLocation = robot.getDirection().moveTowards(robot.getLocation());
        if (!isValidCoordinate(nextLocation)) {
            throw new IllegalStateException(ROBOT_WILL_FALL_ERROR_MESSAGE);
        }

        robot.setLocation(nextLocation);
    }

    private boolean isRobotPlaced() {
        return robot.getLocation() != null;
    }

    public void left() {
        if (!isRobotPlaced()) {
            throw new IllegalStateException(ROBOT_NOT_PLACED_ERROR_MESSAGE);
        }

        robot.setDirection(robot.getDirection().left());
    }

    public void right() {
        if (!isRobotPlaced()) {
            throw new IllegalStateException(ROBOT_NOT_PLACED_ERROR_MESSAGE);
        }

        robot.setDirection(robot.getDirection().right());
    }

    public String report() {
        if (!isRobotPlaced()) {
            return ROBOT_NOT_PLACED_ERROR_MESSAGE;
        }

        return robot.toString();
    }

    public Location getRobotLocation() {
        try {
            return robot.getLocation().clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public Direction getRobotDirection() {
        return robot.getDirection();
    }
}
