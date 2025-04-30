package org.example;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import org.example.models.Note;

public class FileStorage {
    private static final String FILE_PATH = "notes.txt";

    public void writeInFile(Note note)
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            String encryptedStr = CryptUtil.encryptText(note.serialize());
            writer.write(encryptedStr);
            writer.newLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readFromFile(String dateSearch)
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String text = CryptUtil.decryptText(line);
                if (text.isEmpty()) {
                    continue;
                }
                Note note = Note.deserialize(text);
                LocalDateTime date = note.getDateTime();
                if (dateSearch == null || date.toString().contains(dateSearch)) {
                    System.out.println(note);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void getStatistic(){
        try{
            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
                String line;
                int allCount = 0;
                int contentSizeAll = 0;
                HashMap<LocalDate,Integer> activeDates = new HashMap<>();
                while ((line = reader.readLine()) != null) {
                    String text = CryptUtil.decryptText(line);
                    if (text.isEmpty()) {
                        continue;
                    }
                    Note note = Note.deserialize(text);
                    LocalDate dateTime = note.getDateTime().toLocalDate();
                    String content = note.getText();
                    if(activeDates.containsKey(dateTime))
                    {
                        int cur =activeDates.get(dateTime);
                        activeDates.put(dateTime,cur+1);
                    }
                    else{
                        activeDates.put(dateTime,1);
                    }
                    allCount+=1;
                    contentSizeAll+=content.length();
                }
                int max = 0;
                LocalDate maxDate = null;
                for(var cur: activeDates.entrySet()){
                    if(cur.getValue()>max){
                        max = cur.getValue();
                        maxDate = cur.getKey();
                    }
                }
                if(maxDate!=null){
                    System.out.println(String.format("Общее кол-во записей: %d, Кол-во символов в контенте: %d, Самый активный день: %s и его кол-во записей: %d",allCount,contentSizeAll,maxDate,max));
                }
                else{
                    System.out.println(String.format("Общее кол-во записей: %d, Кол-во символов в контенте: %d",allCount,contentSizeAll));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
