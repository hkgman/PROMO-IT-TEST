package org.example.models;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Note {
    private final String text;
    private final LocalDateTime dateTime;

    public Note(String text){
        this.text = text;
        this.dateTime = LocalDateTime.now();
    }

    public Note(String text,LocalDateTime dateTime){
        this.text = text;
        this.dateTime = dateTime;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String toString(){
        return this.dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + " " + this.text;
    }

    public String serialize(){
        return this.text + "|" + this.dateTime;
    }

    public static Note deserialize(String text){
        String[] parts = text.split("\\|");
        return new Note(parts[0],LocalDateTime.parse(parts[1]));
    }
}