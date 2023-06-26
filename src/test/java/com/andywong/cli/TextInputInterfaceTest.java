package com.andywong.cli;

import com.andywong.components.Grid;
import com.andywong.components.Robot;
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

    // TODO - need to investigate why running tests individually passes but failed when running
    // together

    @BeforeEach
    public void beforeEach() {
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
    void runCommand_throw_exception_when_robot_have_not_been_placed() {
        // Arrange
        String command = "move";
        String [] commandInput = {command, null};
        String expected = "The robot hasn't been placed";

        // Act
        Throwable exception = assertThrows(IllegalStateException.class, () -> textInputInterface.runCommand(commandInput));


        // Assert
        assertEquals(expected, exception.getMessage());
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
