package com.andywong.components;

public class Robot {

    private Direction direction;
    private Location location;

    private static Robot _instance;
    protected Robot() {}
    public static Robot getInstance() {
        if (_instance == null) {
            _instance = new Robot();
        }
        return _instance;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return location.getX() + "," + location.getY() + "," + direction.name();
    }
}
