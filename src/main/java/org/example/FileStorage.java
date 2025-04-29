package org.example;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.example.models.Note;

public class FileStorage {
    private static final String FILE_PATH = "notes.txt";

    public static void writeInFile(Note note)
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.newLine();
            writer.write(note.serialize());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readFromFile()
    {
        List<Note> notes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                notes.add(Note.deserialize(line));
            }
            notes.stream()
                    .sorted(Comparator.comparing(Note::getDateTime).reversed())
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}