package com.andywong.domain;

public class Robot {

    private Direction direction;
    private Position position;

    public Robot() {}

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return position.x() + "," + position.y() + "," + direction.name();
    }
}
