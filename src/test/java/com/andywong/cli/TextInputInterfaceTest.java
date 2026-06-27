package com.andywong.cli;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TextInputInterfaceTest {

    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;

    private TextInputInterface textInputInterface;

    @BeforeEach
    public void beforeEach() {
        outContent.reset();
        textInputInterface = new TextInputInterface();
    }

    @AfterEach
    public void afterEach() {
        textInputInterface = null;
    }

    @BeforeAll
    public static void setup() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterAll
    public static void restore() {
        System.setOut(originalOut);
    }

    @Test
    void runCommand_MOVE_beforePlace_isIgnored() {
        textInputInterface.runCommand("MOVE");
        assertEquals("", outContent.toString());
    }

    @Test
    void runCommand_throw_exception_when_PLACE_has_no_parameters() {
        String expected = "The PLACE command must have three parameters: X,Y,DIRECTION";

        Throwable exception = assertThrows(CommandParseException.class, () -> textInputInterface.runCommand("PLACE"));

        assertEquals(expected, exception.getMessage());
    }

    @Test
    void runCommand_throw_exception_when_PLACE_has_invalid_number_of_arguments() {
        String expected = "The PLACE command must have three parameters: X,Y,DIRECTION";

        Throwable exception = assertThrows(CommandParseException.class, () -> textInputInterface.runCommand("PLACE a,b"));

        assertEquals(expected, exception.getMessage());
    }

    @Test
    void runCommand_throw_exception_when_PLACE_has_invalid_coordinate_params() {
        String expected = "Location must be integers";

        Throwable exception = assertThrows(CommandParseException.class, () -> textInputInterface.runCommand("PLACE a,b,c"));

        assertEquals(expected, exception.getMessage());
    }

    @Test
    void runCommand_REPORT_should_print_robot_state() {
        textInputInterface.runCommand("PLACE 0,0,NORTH");
        textInputInterface.runCommand("REPORT");
        assertEquals("0,0,NORTH", outContent.toString().trim());
    }

    @Test
    void runCommand_LEFT_should_print_nothing() {
        textInputInterface.runCommand("PLACE 0,0,NORTH");
        textInputInterface.runCommand("LEFT");
        assertEquals("", outContent.toString());
    }

    @Test
    void runCommand_RIGHT_should_print_nothing() {
        textInputInterface.runCommand("PLACE 0,0,NORTH");
        textInputInterface.runCommand("RIGHT");
        assertEquals("", outContent.toString());
    }

    @Test
    void runCommand_REPORT_beforePlace_printsNothing() {
        textInputInterface.runCommand("REPORT");
        assertEquals("", outContent.toString());
    }

    @Test
    void runCommand_MOVE_offTable_isIgnored() {
        textInputInterface.runCommand("PLACE 0,0,WEST");
        textInputInterface.runCommand("MOVE");
        textInputInterface.runCommand("REPORT");
        assertEquals("0,0,WEST", outContent.toString().trim());
    }

    @Test
    void runCommand_invalidPlace_isIgnored_thenValidPlaceWorks() {
        textInputInterface.runCommand("PLACE 5,5,NORTH");
        textInputInterface.runCommand("PLACE 0,0,NORTH");
        textInputInterface.runCommand("REPORT");
        assertEquals("0,0,NORTH", outContent.toString().trim());
    }

    @Test
    void runCommand_MOVE_should_print_nothing() {
        textInputInterface.runCommand("PLACE 0,0,NORTH");
        textInputInterface.runCommand("MOVE");
        assertEquals("", outContent.toString());
    }
}
