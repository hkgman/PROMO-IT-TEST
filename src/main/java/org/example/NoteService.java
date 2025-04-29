package org.example;

import org.example.models.Note;

public class NoteService {

    public void addNote(String text){
        Note note = new Note(text);
        FileStorage.writeInFile(note);
    }
    public void readFromFile(){
        FileStorage.readFromFile();
    }
}
