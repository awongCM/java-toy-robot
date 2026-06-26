package com.andywong.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TableTest {

    @Test
    void place_should_throw_IllegalArgumentException_when_position_is_null() {
        Table table = new Table();

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> table.place(null, null));

        assertEquals("No Location is set", exception.getMessage());
    }

    @Test
    void place_should_throw_IllegalArgumentException_when_x_is_negative() {
        Table table = new Table();
        Position initialCoordinates = new Position(-1, 1);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> table.place(initialCoordinates, null));

        assertEquals("Location provided is invalid", exception.getMessage());
    }

    @Test
    void place_should_throw_IllegalArgumentException_when_y_is_negative() {
        Table table = new Table();
        Position initialCoordinates = new Position(0, -1);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> table.place(initialCoordinates, null));

        assertEquals("Location provided is invalid", exception.getMessage());
    }

    @Test
    void place_should_throw_IllegalArgumentException_when_x_is_out_of_width_bounds() {
        Table table = new Table();
        Position initialCoordinates = new Position(10000, 1);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> table.place(initialCoordinates, null));

        assertEquals("Location provided is invalid", exception.getMessage());
    }

    @Test
    void place_should_throw_IllegalArgumentException_when_y_is_out_of_height_bounds() {
        Table table = new Table();
        Position initialCoordinates = new Position(1, 10000);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> table.place(initialCoordinates, null));

        assertEquals("Location provided is invalid", exception.getMessage());
    }

    @Test
    void place_should_throw_IllegalArgumentException_when_no_direction_is_set() {
        Table table = new Table();
        Position initialCoordinates = new Position(0, 0);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> table.place(initialCoordinates, null));

        assertEquals("No direction is set", exception.getMessage());
    }

    @Test
    void place_should_set_robot_to_appropriate_position_and_direction() {
        Table table = new Table();
        Position initialCoordinates = new Position(0, 0);
        Direction direction = Direction.EAST;

        table.place(initialCoordinates, direction);

        assertEquals(initialCoordinates, table.getRobotPosition());
        assertEquals(direction, table.getRobotDirection());
    }

    @Test
    void place_respects_injectable_table_size() {
        Table table = new Table(3, 3, new Robot());

        assertThrows(IllegalArgumentException.class, () -> table.place(new Position(3, 0), Direction.NORTH));
        table.place(new Position(2, 2), Direction.NORTH);
        assertEquals(new Position(2, 2), table.getRobotPosition());
    }

    @Test
    void move_should_throw_IllegalStateException_if_robot_has_not_been_placed() {
        Table table = new Table();

        Throwable exception = assertThrows(IllegalStateException.class, table::move);

        assertEquals("The robot hasn't been placed", exception.getMessage());
    }

    @Test
    void move_should_throw_IllegalStateException_if_the_robot_is_facing_the_western_edge() {
        Table table = new Table();
        Position initialCoordinates = new Position(0, 0);

        table.place(initialCoordinates, Direction.WEST);
        Throwable exception = assertThrows(IllegalStateException.class, table::move);

        assertEquals("The robot will fall", exception.getMessage());
        assertEquals(initialCoordinates, table.getRobotPosition());
    }

    @Test
    void move_should_throw_IllegalStateException_if_the_robot_is_facing_the_southern_edge() {
        Table table = new Table();
        Position initialCoordinates = new Position(0, 0);

        table.place(initialCoordinates, Direction.SOUTH);
        Throwable exception = assertThrows(IllegalStateException.class, table::move);

        assertEquals("The robot will fall", exception.getMessage());
        assertEquals(initialCoordinates, table.getRobotPosition());
    }

    @Test
    void move_should_throw_IllegalStateException_if_the_robot_is_facing_the_eastern_edge() {
        Table table = new Table();
        Position initialCoordinates = new Position(4, 0);

        table.place(initialCoordinates, Direction.EAST);
        Throwable exception = assertThrows(IllegalStateException.class, table::move);

        assertEquals("The robot will fall", exception.getMessage());
        assertEquals(initialCoordinates, table.getRobotPosition());
    }

    @Test
    void move_should_throw_IllegalStateException_if_the_robot_is_facing_the_northern_edge() {
        Table table = new Table();
        Position initialCoordinates = new Position(4, 4);

        table.place(initialCoordinates, Direction.NORTH);
        Throwable exception = assertThrows(IllegalStateException.class, table::move);

        assertEquals("The robot will fall", exception.getMessage());
        assertEquals(initialCoordinates, table.getRobotPosition());
    }

    @Test
    void move_should_move_the_robot_towards_the_east_when_facing_east() {
        Table table = new Table();
        Position coordinates = new Position(0, 0);
        Position expected = new Position(1, 0);

        table.place(coordinates, Direction.EAST);
        table.move();

        assertEquals(expected, table.getRobotPosition());
    }

    @Test
    void left_should_throw_IllegalStateException_if_robot_has_not_been_placed() {
        Table table = new Table();

        Throwable exception = assertThrows(IllegalStateException.class, table::left);

        assertEquals("The robot hasn't been placed", exception.getMessage());
    }

    @Test
    void left_should_rotate_the_robot_to_the_west_when_facing_north() {
        Table table = new Table();
        table.place(new Position(0, 0), Direction.NORTH);
        table.left();
        assertEquals(Direction.WEST, table.getRobotDirection());
    }

    @Test
    void right_should_throw_IllegalStateException_if_robot_has_not_been_placed() {
        Table table = new Table();

        Throwable exception = assertThrows(IllegalStateException.class, table::right);

        assertEquals("The robot hasn't been placed", exception.getMessage());
    }

    @Test
    void right_should_rotate_the_robot_to_the_east_when_facing_north() {
        Table table = new Table();
        table.place(new Position(0, 0), Direction.NORTH);
        table.right();
        assertEquals(Direction.EAST, table.getRobotDirection());
    }

    @Test
    void report_should_return_message_if_robot_has_not_been_placed() {
        Table table = new Table();
        assertEquals("The robot hasn't been placed", table.report());
    }

    @Test
    void report_should_return_robot_position_and_facing_direction() {
        Table table = new Table();
        table.place(new Position(0, 0), Direction.NORTH);
        assertEquals("0,0,NORTH", table.report());
    }

    @Test
    void position_hashCode_matches_equals() {
        Position first = new Position(1, 2);
        Position second = new Position(1, 2);
        Position third = new Position(2, 1);

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
        assertEquals(first, first.copy());
        assertEquals(first.hashCode(), first.copy().hashCode());
        assertEquals(false, first.equals(third));
    }
}
