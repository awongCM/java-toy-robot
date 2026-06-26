package com.andywong.domain;

public enum Direction {
    NORTH(new Position(0, 1)),
    EAST(new Position(1, 0)),
    SOUTH(new Position(0, -1)),
    WEST(new Position(-1, 0));

    private final Position delta;

    private Direction left;

    static {
        NORTH.left = WEST;
        EAST.left = NORTH;
        SOUTH.left = EAST;
        WEST.left = SOUTH;
    }

    private Direction right;

    static {
        NORTH.right = EAST;
        EAST.right = SOUTH;
        SOUTH.right = WEST;
        WEST.right = NORTH;
    }

    Direction(Position delta) {
        this.delta = delta;
    }

    public Direction left() {
        return left;
    }

    public Direction right() {
        return right;
    }

    public Position moveTowards(Position position) {
        return new Position(position.x() + delta.x(), position.y() + delta.y());
    }

    public static Direction find(String stringDirection) {
        if (stringDirection == null || stringDirection.isBlank()) {
            return null;
        }

        String sanitizedCommand = stringDirection.toUpperCase();
        for (Direction direction : Direction.values()) {
            if (direction.name().equals(sanitizedCommand)) {
                return direction;
            }
        }

        return null;
    }

}
