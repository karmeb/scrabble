package com.karme.scrabblebackend.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileUtil {
    public static List<String> readLinesFromFile(String filePath) {
        List<String> lines = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(filePath));
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Unable to find file with path: " + filePath, e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred while attempting to read from file: " + filePath, e);
        }

        return lines;
    }
}
