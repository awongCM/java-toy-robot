package com.andywong.components;

public class Grid {

    private static final String ROBOT_NOT_PLACED_ERROR_MESSAGE = "The robot hasn't been placed";
    private static final String ROBOT_WILL_FALL_ERROR_MESSAGE = "The robot will fall";
    private static final String NO_LOCATION_ERROR_MESSAGE = "No Location is set";
    private static final String INVALID_LOCATION_ERROR_MESSAGE = "Location provided is invalid";
    private static final String NO_DIRECTION_ERROR_MESSAGE = "No direction is set";

    private final static int GRID_HEIGHT = 5;
    private final static int GRID_WIDTH = 5;
    private static final int LOCATION_MIN_VALUE = 0;
    

    private static Grid _instance = null;
    private Robot robot;
    public Grid() {
        robot = Robot.getInstance();
    }

    public static Grid getInstance() {
        if (_instance == null) {
            _instance = new Grid();
        }
        return _instance;
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

    private static boolean isValidCoordinate(Location Location) {
        Integer x = Location.getX();
        if (x == null) return false;
        if (x < LOCATION_MIN_VALUE) return false;
        if (x >= GRID_WIDTH) return false;

        Integer y = Location.getY();
        if (y == null) return false;
        if (y < LOCATION_MIN_VALUE) return false;
        if (y >= GRID_HEIGHT) return false;

        return true;
    }

    public void move() {
        if(!isRobotPlaced()){
            throw new IllegalStateException(ROBOT_NOT_PLACED_ERROR_MESSAGE);
        }

        Location nextLocation = robot.getDirection().moveTowards(robot.getLocation());
        if(!isValidCoordinate(nextLocation)){
            throw new IllegalStateException(ROBOT_WILL_FALL_ERROR_MESSAGE);
        }

        robot.setLocation(nextLocation);
    }

    private boolean isRobotPlaced() {
        return robot.getLocation() != null;
    }

    public void left(){
        if(!isRobotPlaced()){
            throw new IllegalStateException(ROBOT_NOT_PLACED_ERROR_MESSAGE);
        }

        Direction newDirection = robot.getDirection().left();
        robot.setDirection(newDirection);
    }

    public void right(){
        if(!isRobotPlaced()){
            throw new IllegalStateException(ROBOT_NOT_PLACED_ERROR_MESSAGE);
        }

        Direction newDirection = robot.getDirection().right();
        robot.setDirection(newDirection);
    }

    public String report(){
        if(!isRobotPlaced()){
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
