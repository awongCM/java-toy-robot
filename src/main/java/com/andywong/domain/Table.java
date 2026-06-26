package com.andywong.domain;

public class Table {

    private static final String ROBOT_NOT_PLACED_ERROR_MESSAGE = "The robot hasn't been placed";
    private static final String ROBOT_WILL_FALL_ERROR_MESSAGE = "The robot will fall";
    private static final String NO_POSITION_ERROR_MESSAGE = "No Location is set";
    private static final String INVALID_POSITION_ERROR_MESSAGE = "Location provided is invalid";
    private static final String NO_DIRECTION_ERROR_MESSAGE = "No direction is set";

    private static final int DEFAULT_SIZE = 5;
    private static final int MIN_COORDINATE = 0;

    private final int width;
    private final int height;
    private final Robot robot;

    public Table() {
        this(DEFAULT_SIZE, DEFAULT_SIZE, new Robot());
    }

    public Table(Robot robot) {
        this(DEFAULT_SIZE, DEFAULT_SIZE, robot);
    }

    public Table(int width, int height, Robot robot) {
        this.width = width;
        this.height = height;
        this.robot = robot;
    }

    public void place(Position position, Direction direction) {
        if (position == null) {
            throw new IllegalArgumentException(NO_POSITION_ERROR_MESSAGE);
        }

        if (!isValidCoordinate(position)) {
            throw new IllegalArgumentException(INVALID_POSITION_ERROR_MESSAGE);
        }

        if (direction == null) {
            throw new IllegalArgumentException(NO_DIRECTION_ERROR_MESSAGE);
        }

        robot.setPosition(position);
        robot.setDirection(direction);
    }

    private boolean isValidCoordinate(Position position) {
        if (position.x() < MIN_COORDINATE || position.x() >= width) {
            return false;
        }
        if (position.y() < MIN_COORDINATE || position.y() >= height) {
            return false;
        }
        return true;
    }

    public void move() {
        if (!isRobotPlaced()) {
            throw new IllegalStateException(ROBOT_NOT_PLACED_ERROR_MESSAGE);
        }

        Position nextPosition = robot.getDirection().moveTowards(robot.getPosition());
        if (!isValidCoordinate(nextPosition)) {
            throw new IllegalStateException(ROBOT_WILL_FALL_ERROR_MESSAGE);
        }

        robot.setPosition(nextPosition);
    }

    private boolean isRobotPlaced() {
        return robot.getPosition() != null;
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

    public Position getRobotPosition() {
        Position position = robot.getPosition();
        return position == null ? null : position.copy();
    }

    public Direction getRobotDirection() {
        return robot.getDirection();
    }
}
