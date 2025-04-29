package org.example;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import org.example.models.Note;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class FileStorage {
    private static final String FILE_PATH = "notes.txt";
    private static final String SECRET_KEY = System.getenv("MY_SECRET_KEY");
    public static void writeInFile(Note note)
    {
        try{
            SecretKey myDesKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            String str = note.serialize();
            Cipher desCipher;
            desCipher = Cipher.getInstance("AES");
            desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);
            byte[] encryptedBytes = desCipher.doFinal(str.getBytes());
            String encryptedStr = Base64.getEncoder().encodeToString(encryptedBytes);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
                writer.write(encryptedStr);
                writer.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readFromFile(String dateSearch)
    {
        try{
            SecretKey myDesKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            Cipher desCipher;
            desCipher = Cipher.getInstance("AES");
            desCipher.init(Cipher.DECRYPT_MODE, myDesKey);
            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    byte[] encryptedBytes = Base64.getDecoder().decode(line);
                    byte[] textDecrypted  = desCipher.doFinal(encryptedBytes);
                    String text = new String(textDecrypted);
                    if(text.isEmpty()){
                        continue;
                    }
                    Note note = Note.deserialize(text);
                    LocalDateTime date = note.getDateTime();
                    if (dateSearch == null || date.toString().contains(dateSearch)) {
                        System.out.println(note);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void getStatistic(){
        try{
            SecretKey myDesKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            Cipher desCipher;
            desCipher = Cipher.getInstance("AES");
            desCipher.init(Cipher.DECRYPT_MODE, myDesKey);
            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
                String line;
                Integer allCount = 0;
                Integer contentSizeAll = 0;
                HashMap<LocalDate,Integer> activeDates = new HashMap<>();
                while ((line = reader.readLine()) != null) {
                    byte[] encryptedBytes = Base64.getDecoder().decode(line);
                    byte[] textDecrypted  = desCipher.doFinal(encryptedBytes);
                    String text = new String(textDecrypted);
                    if(text.isEmpty()){
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
