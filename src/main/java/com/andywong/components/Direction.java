package com.andywong.components;

public enum Direction {
    NORTH(new Location(0, 1)),
    EAST(new Location(1, 0)),
    SOUTH(new Location(0, -1)),
    WEST(new Location(-1, 0));

    private final Location delta;

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

    Direction(Location delta) {
        this.delta = delta;
    }

    public Direction left() {
        return left;
    }

    public Direction right() {
        return right;
    }

    public Location moveTowards(Location Location) {
        int newX = Location.getX() + 1 * delta.getX();
        int newY = Location.getY() + 1 * delta.getY();
        return new Location(newX, newY);
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
