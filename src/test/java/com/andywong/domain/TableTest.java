package com.andywong.domain;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TableTest {

    @Test
    void place_should_throw_IllegalArgumentException_when_no_coordinate_are_set() {
        // Arrange
        Table table = new Table();

        // Act
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> table.place(null, null));

        // Act
        assertEquals("No Location is set", exception.getMessage());
    }

    @Test
    void place_should_throw_IllegalArgumentException_when_coordinate_do_not_have_x_value() {
        // Arrange
        Table table = new Table();
        Location initialCoordinates = new Location(null, 0);

        // Act
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> table.place(initialCoordinates, null));

        // Act
        assertEquals("Location provided is invalid", exception.getMessage());
    }

    @Test
    void place_should_throw_IllegalArgumentException_when_coordinate_do_not_have_y_value() {
        // Arrange
        Table table = new Table();
        Location initialCoordinates = new Location(0, null);

        // Act
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> table.place(initialCoordinates, null));

        // Act
        assertEquals("Location provided is invalid", exception.getMessage());
    }

    @Test
    void place_should_throw_IllegalArgumentException_when_x_is_negative() {
        // Arrange
        Table table = new Table();
        Location initialCoordinates = new Location(-1, 1);

        // Act
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> table.place(initialCoordinates, null));

        // Act
        assertEquals("Location provided is invalid", exception.getMessage());
    }

    @Test
    void place_should_throw_IllegalArgumentException_when_y_is_negative() {
        // Arrange
        Table table = new Table();
        Location initialCoordinates = new Location(0, -1);

        // Act
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> table.place(initialCoordinates, null));

        // Act
        assertEquals("Location provided is invalid", exception.getMessage());
    }

    @Test
    void place_should_throw_IllegalArgumentException_when_x_is_out_of_width_bounds() {
        // Arrange
        Table table = new Table();
        Location initialCoordinates = new Location(10000, 1);

        // Act
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> table.place(initialCoordinates, null));

        // Act
        assertEquals("Location provided is invalid", exception.getMessage());
    }

    @Test
    void place_should_throw_IllegalArgumentException_when_y_is_out_of_height_bounds() {
        // Arrange
        Table table = new Table();
        Location initialCoordinates = new Location(1, 10000);

        // Act
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> table.place(initialCoordinates, null));

        // Act
        assertEquals("Location provided is invalid", exception.getMessage());
    }

    @Test
    void place_should_throw_IllegalArgumentException_when_no_direction_is_set() {
        // Arrange
        Table table = new Table();
        Location initialCoordinates = new Location(0, 0);

        // Act
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> table.place(initialCoordinates, null));

        // Act
        assertEquals("No direction is set", exception.getMessage());
    }

    @Test
    void place_should_set_Robot_to_appropriate_coordinates_and_direction() {
        // Arrange
        Table table = new Table();
        Location initialCoordinates = new Location(0, 0);
        Direction direction = Direction.EAST;

        // Act
        table.place(initialCoordinates, direction);

        // Act
        assertEquals(table.getRobotLocation(), initialCoordinates);
        assertEquals(table.getRobotDirection(), direction);
    }

    @Test
    void move_should_throw_IllegalStateException_if_robot_has_not_been_placed() {
        // Arrange
        Table table = new Table();

        // Act
        Throwable exception = assertThrows(IllegalStateException.class, () -> table.move());

        // Act
        assertEquals("The robot hasn't been placed", exception.getMessage());
    }

    @Test
    public void move_should_throw_IllegalStateException_if_the_robot_is_facing_the_western_edge() {
        // Arrange
        Table table = new Table();
        Location initialCoordinates = new Location(0, 0);
        Direction direction = Direction.WEST;

        // Act
        table.place(initialCoordinates, direction);
        Throwable exception = assertThrows(IllegalStateException.class, () -> table.move());

        // Act
        assertEquals("The robot will fall", exception.getMessage());
        assertEquals(initialCoordinates, table.getRobotLocation());
    }

    @Test
    public void move_should_throw_IllegalStateException_if_the_robot_is_facing_the_southern_edge() {
        // Arrange
        Table table = new Table();
        Location initialCoordinates = new Location(0, 0);
        Direction direction = Direction.SOUTH;

        // Act
        table.place(initialCoordinates, direction);
        Throwable exception = assertThrows(IllegalStateException.class, () -> table.move());

        // Act
        assertEquals("The robot will fall", exception.getMessage());
        assertEquals(initialCoordinates, table.getRobotLocation());
    }

    @Test
    public void move_should_throw_IllegalStateException_if_the_robot_is_facing_the_eastern_edge() {
        // Arrange
        Table table = new Table();
        Location initialCoordinates = new Location(4, 0);
        Direction direction = Direction.EAST;

        // Act
        table.place(initialCoordinates, direction);
        Throwable exception = assertThrows(IllegalStateException.class, () -> table.move());

        // Act
        assertEquals("The robot will fall", exception.getMessage());
        assertEquals(initialCoordinates, table.getRobotLocation());
    }

    @Test
    public void move_should_throw_IllegalStateException_if_the_robot_is_facing_the_northern_edge() {
        // Arrange
        Table table = new Table();
        Location initialCoordinates = new Location(4, 4);
        Direction direction = Direction.NORTH;

        // Act
        table.place(initialCoordinates, direction);
        Throwable exception = assertThrows(IllegalStateException.class, () -> table.move());

        // Act
        assertEquals("The robot will fall", exception.getMessage());
        assertEquals(initialCoordinates, table.getRobotLocation());
    }

    @Test
    public void move_should_move_the_robot_towards_the_east_when_facing_east() {
        // Arrange
        Table table = new Table();
        Location coordinates = new Location(0, 0);
        Direction direction = Direction.EAST;
        Location expected = new Location(1, 0);

        // Act
        table.place(coordinates, direction);
        table.move();

        // Act
        assertEquals(expected, table.getRobotLocation());
    }

    @Test
    void left_should_throw_IllegalStateException_if_robot_has_not_been_placed() {
        // Arrange
        Table table = new Table();

        // Act
        Throwable exception = assertThrows(IllegalStateException.class, () -> table.left());

        // Act
        assertEquals("The robot hasn't been placed", exception.getMessage());
    }

    @Test
    public void left_should_rotate_the_robot_to_the_west_when_facing_north() {
        // Arrange
        Table table = new Table();
        Location coordinates = new Location(0, 0);
        Direction direction = Direction.NORTH;
        Direction expected = Direction.WEST;

        // Act
        table.place(coordinates, direction);
        table.left();

        // Act
        assertEquals(expected, table.getRobotDirection());
    }


    @Test
    void right_should_throw_IllegalStateException_if_robot_has_not_been_placed() {
        // Arrange
        Table table = new Table();

        // Act
        Throwable exception = assertThrows(IllegalStateException.class, () -> table.right());

        // Act
        assertEquals("The robot hasn't been placed", exception.getMessage());
    }

    @Test
    public void right_should_rotate_the_robot_to_the_east_when_facing_north() {
        // Arrange
        Table table = new Table();
        Location coordinates = new Location(0, 0);
        Direction direction = Direction.NORTH;
        Direction expected = Direction.EAST;

        // Act
        table.place(coordinates, direction);
        table.right();

        // Act
        assertEquals(expected, table.getRobotDirection());
    }


    @Test
    void report_should_return_message_if_robot_has_not_been_placed() {
        // Arrange
        Table table = new Table();

        // Act
        String actual = table.report();

        // Act
        assertEquals("The robot hasn't been placed", actual);
    }

    @Test
    void report_should_return_robot_position_and_facing_direction() {
        // Arrange
        Table table = new Table();
        Location coordinates = new Location(0, 0);
        Direction direction = Direction.NORTH;
        String expected = "0,0,NORTH";

        // Act
        table.place(coordinates, direction);
        String actual = table.report();

        // Act
        assertEquals(expected, actual);
    }
}