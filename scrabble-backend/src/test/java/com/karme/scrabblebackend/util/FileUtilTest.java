package com.karme.scrabblebackend.util;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilTest {

    @Test
    void readLinesFromFile_returnsLinesFromFile_whenFileExists() {
        String filePath = "src/test/resources/test-dictionary.txt";
        List<String> lines = FileUtil.readLinesFromFile(filePath);

        assertEquals(3, lines.size());
        assertEquals("firstword", lines.get(0));
        assertEquals("secondword", lines.get(1));
        assertEquals("thirdword", lines.get(2));
    }

    @Test
    void readLinesFromFile_throwsException_whenFileDoesNotExist() {
        String nonExistentFilePath = "src/test/resources/nonexistentfile.txt";

        Exception exception = assertThrows(RuntimeException.class,
                () -> FileUtil.readLinesFromFile(nonExistentFilePath));
        assertTrue(exception.getMessage().equals("Unable to find file with path: " + nonExistentFilePath));
    }

}