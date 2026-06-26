package com.andywong.cli;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TextInputInterfaceFileInputTest {

    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        TextInputInterface.resetForTesting();
        outContent.reset();
    }

    @BeforeAll
    static void captureStdout() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterAll
    static void restoreStdout() {
        System.setOut(originalOut);
    }

    @Test
    void main_readsCommandsFromFile() throws IOException {
        Path commandsFile = tempDir.resolve("commands.txt");
        Files.writeString(commandsFile, """
                PLACE 0,0,NORTH
                MOVE
                REPORT
                """);

        TextInputInterface.main(new String[]{commandsFile.toString()});

        assertEquals("0,1,NORTH", outContent.toString().trim());
    }

    @Test
    void main_stopsAtBlankLineInFile() throws IOException {
        Path commandsFile = tempDir.resolve("commands.txt");
        Files.writeString(commandsFile, """
                PLACE 0,0,NORTH
                MOVE
                REPORT

                PLACE 1,1,EAST
                REPORT
                """);

        TextInputInterface.main(new String[]{commandsFile.toString()});

        assertEquals("0,1,NORTH", outContent.toString().trim());
    }

    @Test
    void main_throwsWhenFileDoesNotExist() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> TextInputInterface.main(new String[]{"nonexistent-commands.txt"})
        );
        assertEquals("Could not read commands from file: nonexistent-commands.txt", ex.getMessage());
    }
}
