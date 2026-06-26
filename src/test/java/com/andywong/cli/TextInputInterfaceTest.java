package com.andywong.cli;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TextInputInterfaceTest {

    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;

    private TextInputInterface textInputInterface;

    // Tests reset shared singleton state before each run so the suite is order-independent.

    @BeforeEach
    public void beforeEach() {
        TextInputInterface.resetForTesting();
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
        // Arrange
        String command = "move";
        String[] commandInput = {command, null};

        // Act
        textInputInterface.runCommand(commandInput);

        // Assert
        assertEquals("", outContent.toString());
    }

    @Test
    void runCommand_throw_exception_when_PLACE_has_null_arguments() {
        // Arrange
        String command = "place";
        String [] commandInput = {command, null};
        String expected = "The PLACE command must have three parameters: X,Y,DIRECTION";

        // Act
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> textInputInterface.runCommand(commandInput));

        // Assert
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void runCommand_throw_exception_when_PLACE_has_invalid_number_of_arguments() {
        // Arrange
        String command = "place";
        String[] params = new String[]{"a", "b"};
        String expected = "The PLACE command must have three parameters: X,Y,DIRECTION";

        String [] commandInput = {command, Arrays.toString(params).replace("[", "").replace("]","")};

        // Act
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> textInputInterface.runCommand(commandInput));

        // Assert
        assertEquals(expected, exception.getMessage());
    }


    @Test
    void runCommand_throw_exception_when_PLACE_has_invalid_coordinate_params() {
        // Arrange
        String command = "place";
        String[] params = new String[]{"a", "b", "c"};
        String expected ="Location must be integers";

        String [] commandInput = {command, Arrays.toString(params).replace("[", "").replace("]","")};

        // Act
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> textInputInterface.runCommand(commandInput));

        // Assert
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void runCommand_REPORT_should_print_robot_state() {
        // Arrange
        String[] params = new String[]{"0", "0", "NORTH"};
        String expected = "0,0,NORTH";

        String [] commandInput1 = {Command.PLACE.name(), Arrays.toString(params).replace("[","").replace("]","")};
        String [] commandInput2 = {Command.REPORT.name(), null};


        // Act
        textInputInterface.runCommand(commandInput1);
        textInputInterface.runCommand(commandInput2);
        String actual = outContent.toString();

        // Assert
        assertEquals(expected, actual.trim());
    }
//
    @Test
    void runCommand_LEFT_should_print_nothing() {
        // Arrange
        String[] params = new String[]{"0", "0", "NORTH"};
        String expected = "";

        String [] commandInput1 = {Command.PLACE.name(), Arrays.toString(params).replace("[","").replace("]","")};
        String [] commandInput2 = {Command.LEFT.name(), null};

        // Act
        textInputInterface.runCommand(commandInput1);
        textInputInterface.runCommand(commandInput2);

        // Assert
        assertEquals(expected, outContent.toString());
    }

    @Test
    void runCommand_RIGHT_should_print_nothing() {
        // Arrange
        String[] params = new String[]{"0", "0", "NORTH"};
        String expected = "";

        String [] commandInput1 = {Command.PLACE.name(), Arrays.toString(params).replace("[","").replace("]","")};
        String [] commandInput2 = {Command.RIGHT.name(), null};


        // Act
        textInputInterface.runCommand(commandInput1);
        textInputInterface.runCommand(commandInput2);

        // Assert
        assertEquals(expected, outContent.toString());
    }

    @Test
    void runCommand_REPORT_beforePlace_printsNothing() {
        // Arrange
        String[] commandInput = {Command.REPORT.name(), null};

        // Act
        textInputInterface.runCommand(commandInput);

        // Assert
        assertEquals("", outContent.toString());
    }

    @Test
    void runCommand_MOVE_offTable_isIgnored() {
        // Arrange
        String[] params = new String[]{"0", "0", "WEST"};
        String[] placeInput = {Command.PLACE.name(), Arrays.toString(params).replace("[", "").replace("]", "")};
        String[] moveInput = {Command.MOVE.name(), null};
        String[] reportInput = {Command.REPORT.name(), null};

        // Act
        textInputInterface.runCommand(placeInput);
        textInputInterface.runCommand(moveInput);
        textInputInterface.runCommand(reportInput);

        // Assert
        assertEquals("0,0,WEST", outContent.toString().trim());
    }

    @Test
    void runCommand_invalidPlace_isIgnored_thenValidPlaceWorks() {
        // Arrange
        String[] invalidParams = new String[]{"5", "5", "NORTH"};
        String[] validParams = new String[]{"0", "0", "NORTH"};
        String[] invalidPlace = {Command.PLACE.name(), Arrays.toString(invalidParams).replace("[", "").replace("]", "")};
        String[] validPlace = {Command.PLACE.name(), Arrays.toString(validParams).replace("[", "").replace("]", "")};
        String[] reportInput = {Command.REPORT.name(), null};

        // Act
        textInputInterface.runCommand(invalidPlace);
        textInputInterface.runCommand(validPlace);
        textInputInterface.runCommand(reportInput);

        // Assert
        assertEquals("0,0,NORTH", outContent.toString().trim());
    }

    @Test
    void runCommand_MOVE_should_print_nothing() {
        // Arrange
        String[] params = new String[]{"0", "0", "NORTH"};
        String expected = "";

        String [] commandInput1 = {Command.PLACE.name(), Arrays.toString(params).replace("[","").replace("]","")};
        String [] commandInput2 = {Command.MOVE.name(), null};


        // Act
        textInputInterface.runCommand(commandInput1);
        textInputInterface.runCommand(commandInput2);

        // Assert
        assertEquals(expected, outContent.toString());
    }

}
