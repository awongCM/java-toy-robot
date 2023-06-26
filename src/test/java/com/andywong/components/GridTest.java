package com.andywong.components;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GridTest {

    // TODO - need to investigate why running tests individually passes but failed when running
    // together
    @Test
    void place_should_throw_IllegalArgumentException_when_no_coordinate_are_set() {
        // Arrange
        Grid grid = new Grid();

        // Act
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> grid.place(null, null));

        // Act
        assertEquals("No Location is set", exception.getMessage());
    }

    @Test
    void place_should_throw_IllegalArgumentException_when_coordinate_do_not_have_x_value() {
        // Arrange
        Grid grid = new Grid();
        Location initialCoordinates = new Location(null, 0);

        // Act
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> grid.place(initialCoordinates, null));

        // Act
        assertEquals("Location provided is invalid", exception.getMessage());
    }

    @Test
    void place_should_throw_IllegalArgumentException_when_coordinate_do_not_have_y_value() {
        // Arrange
        Grid grid = new Grid();
        Location initialCoordinates = new Location(0, null);

        // Act
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> grid.place(initialCoordinates, null));

        // Act
        assertEquals("Location provided is invalid", exception.getMessage());
    }

    @Test
    void place_should_throw_IllegalArgumentException_when_x_is_negative() {
        // Arrange
        Grid grid = new Grid();
        Location initialCoordinates = new Location(-1, 1);

        // Act
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> grid.place(initialCoordinates, null));

        // Act
        assertEquals("Location provided is invalid", exception.getMessage());
    }

    @Test
    void place_should_throw_IllegalArgumentException_when_y_is_negative() {
        // Arrange
        Grid grid = new Grid();
        Location initialCoordinates = new Location(0, -1);

        // Act
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> grid.place(initialCoordinates, null));

        // Act
        assertEquals("Location provided is invalid", exception.getMessage());
    }

    @Test
    void place_should_throw_IllegalArgumentException_when_x_is_out_of_width_bounds() {
        // Arrange
        Grid grid = new Grid();
        Location initialCoordinates = new Location(10000, 1);

        // Act
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> grid.place(initialCoordinates, null));

        // Act
        assertEquals("Location provided is invalid", exception.getMessage());
    }

    @Test
    void place_should_throw_IllegalArgumentException_when_y_is_out_of_height_bounds() {
        // Arrange
        Grid grid = new Grid();
        Location initialCoordinates = new Location(1, 10000);

        // Act
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> grid.place(initialCoordinates, null));

        // Act
        assertEquals("Location provided is invalid", exception.getMessage());
    }

    @Test
    void place_should_throw_IllegalArgumentException_when_no_direction_is_set() {
        // Arrange
        Grid grid = new Grid();
        Location initialCoordinates = new Location(0, 0);

        // Act
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> grid.place(initialCoordinates, null));

        // Act
        assertEquals("No direction is set", exception.getMessage());
    }

    @Test
    void place_should_set_Robot_to_appropriate_coordinates_and_direction() {
        // Arrange
        Grid grid = new Grid();
        Location initialCoordinates = new Location(0, 0);
        Direction direction = Direction.EAST;

        // Act
        grid.place(initialCoordinates, direction);

        // Act
        assertEquals(grid.getRobotLocation(), initialCoordinates);
        assertEquals(grid.getRobotDirection(), direction);
    }

    @Test
    void move_should_throw_IllegalStateException_if_robot_has_not_been_placed() {
        // Arrange
        Grid grid = new Grid();

        // Act
        Throwable exception = assertThrows(IllegalStateException.class, () -> grid.move());

        // Act
        assertEquals("The robot hasn't been placed", exception.getMessage());
    }

    @Test
    public void move_should_throw_IllegalStateException_if_the_robot_is_facing_the_western_edge() {
        // Arrange
        Grid grid = new Grid();
        Location initialCoordinates = new Location(0, 0);
        Direction direction = Direction.WEST;

        // Act
        grid.place(initialCoordinates, direction);
        Throwable exception = assertThrows(IllegalStateException.class, () -> grid.move());

        // Act
        assertEquals("The robot will fall", exception.getMessage());
        assertEquals(initialCoordinates, grid.getRobotDirection());
    }

    @Test
    public void move_should_throw_IllegalStateException_if_the_robot_is_facing_the_southern_edge() {
        // Arrange
        Grid grid = new Grid();
        Location initialCoordinates = new Location(0, 0);
        Direction direction = Direction.SOUTH;

        // Act
        grid.place(initialCoordinates, direction);
        Throwable exception = assertThrows(IllegalStateException.class, () -> grid.move());

        // Act
        assertEquals("The robot will fall", exception.getMessage());
        assertEquals(initialCoordinates, grid.getRobotDirection());
    }

    @Test
    public void move_should_throw_IllegalStateException_if_the_robot_is_facing_the_eastern_edge() {
        // Arrange
        Grid grid = new Grid();
        Location initialCoordinates = new Location(4, 0);
        Direction direction = Direction.EAST;

        // Act
        grid.place(initialCoordinates, direction);
        Throwable exception = assertThrows(IllegalStateException.class, () -> grid.move());

        // Act
        assertEquals("The robot will fall", exception.getMessage());
        assertEquals(initialCoordinates, grid.getRobotDirection());
    }

    @Test
    public void move_should_throw_IllegalStateException_if_the_robot_is_facing_the_northern_edge() {
        // Arrange
        Grid grid = new Grid();
        Location initialCoordinates = new Location(4, 4);
        Direction direction = Direction.NORTH;

        // Act
        grid.place(initialCoordinates, direction);
        Throwable exception = assertThrows(IllegalStateException.class, () -> grid.move());

        // Act
        assertEquals("The robot will fall", exception.getMessage());
        assertEquals(initialCoordinates, grid.getRobotLocation());
    }

    @Test
    public void move_should_move_the_robot_towards_the_east_when_facing_east() {
        // Arrange
        Grid grid = new Grid();
        Location coordinates = new Location(0, 0);
        Direction direction = Direction.EAST;
        Location expected = new Location(1, 0);

        // Act
        grid.place(coordinates, direction);
        grid.move();

        // Act
        assertEquals(expected, grid.getRobotLocation());
    }

    @Test
    void left_should_throw_IllegalStateException_if_robot_has_not_been_placed() {
        // Arrange
        Grid grid = new Grid();

        // Act
        Throwable exception = assertThrows(IllegalStateException.class, () -> grid.left());

        // Act
        assertEquals("The robot hasn't been placed", exception.getMessage());
    }

    @Test
    public void left_should_rotate_the_robot_to_the_west_when_facing_north() {
        // Arrange
        Grid grid = new Grid();
        Location coordinates = new Location(0, 0);
        Direction direction = Direction.NORTH;
        Direction expected = Direction.WEST;

        // Act
        grid.place(coordinates, direction);
        grid.left();

        // Act
        assertEquals(expected, grid.getRobotDirection());
    }


    @Test
    void right_should_throw_IllegalStateException_if_robot_has_not_been_placed() {
        // Arrange
        Grid grid = new Grid();

        // Act
        Throwable exception = assertThrows(IllegalStateException.class, () -> grid.right());

        // Act
        assertEquals("The robot hasn't been placed", exception.getMessage());
    }

    @Test
    public void right_should_rotate_the_robot_to_the_east_when_facing_north() {
        // Arrange
        Grid grid = new Grid();
        Location coordinates = new Location(0, 0);
        Direction direction = Direction.NORTH;
        Direction expected = Direction.EAST;

        // Act
        grid.place(coordinates, direction);
        grid.right();

        // Act
        assertEquals(expected, grid.getRobotDirection());
    }


    @Test
    void report_should_return_message_if_robot_has_not_been_placed() {
        // Arrange
        Grid grid = new Grid();

        // Act
        String actual = grid.report();

        // Act
        assertEquals("The robot hasn't been placed", actual);
    }

    @Test
    void report_should_return_robot_position_and_facing_direction() {
        // Arrange
        Grid grid = new Grid();
        Location coordinates = new Location(0, 0);
        Direction direction = Direction.NORTH;
        String expected = "0,0,NORTH";

        // Act
        grid.place(coordinates, direction);
        String actual = grid.report();

        // Act
        assertEquals(expected, actual);
    }
}