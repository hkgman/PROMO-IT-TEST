package org.example;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        FileStorage fileStorage = new FileStorage();
        NoteService service = new NoteService(fileStorage);
        Scanner console = new Scanner(System.in);
        while (true) {
            System.out.println("Введите команду:");
            String command = console.nextLine();
            if (command.equals("#write")) {
                System.out.print("Введите ваши планы на сегодня: ");
                String text = console.nextLine();
                service.addNote(text);
            } else if (command.equals("#read")) {
                service.readFromFile(null);
            } else if (command.equals("#search")) {
                System.out.print("Введите дату по которой хотите осуществить поиск (формат : год-месяц-день): ");
                String date = console.nextLine();
                if (!date.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
                    System.out.println("Неверный формат даты. Используйте формат: год-месяц-день (например, 2025-04-29)");
                    continue;
                }
                service.readFromFile(date);
            } else if (command.equals("#statistics")) {
                service.getStatistic();
            } else if(command.equals("#exit")){
                break;
            }else {
                System.out.println("Такой команды нет!");
            }
        }
    }
}