package org.example;
import org.example.models.Note;

public class NoteService {
    private final FileStorage fileStorage;

    public NoteService(FileStorage fileStorage) {
        this.fileStorage = fileStorage;
    }
    public void addNote(String text){
        Note note = new Note(text);
        fileStorage.writeInFile(note);
    }
    public void readFromFile(String dateTime){
        fileStorage.readFromFile(dateTime);
    }

    public void getStatistic(){
        fileStorage.getStatistic();
    }
}
