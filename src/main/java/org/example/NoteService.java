package org.example;

import org.example.models.Note;

import java.time.LocalDateTime;

public class NoteService {

    public void addNote(String text){
        Note note = new Note(text);
        FileStorage.writeInFile(note);
    }
    public void readFromFile(String dateTime){
        FileStorage.readFromFile(dateTime);
    }

    public void getStatistic(){
        FileStorage.getStatistic();
    }
}
