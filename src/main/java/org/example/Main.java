package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        NoteService service = new NoteService();
        Scanner console = new Scanner(System.in);
        while (true) {
            System.out.println("Введите команду:");
            String command = console.nextLine();
            if (command.equals("#write")) {
                System.out.print("Введите ваши планы на сегодня: ");
                String text = console.nextLine();
                service.addNote(text);
            } else if (command.equals("#read")) {
                service.readFromFile();
            } else if(command.equals("#exit")){
                break;
            }else {
                System.out.println("Такой команды нет!");
            }
        }
    }
}