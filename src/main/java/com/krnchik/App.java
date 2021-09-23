package com.krnchik;

import com.krnchik.model.alarm.Alarm;
import com.krnchik.model.alarm.AlarmContainer;
import com.krnchik.model.alarm.AlarmModel;
import com.krnchik.model.alarm.Container;
import com.krnchik.model.audio.Audio;

import java.io.File;
import java.util.Scanner;

public class App {

    private final Container container;

    public App(Container container) {
        this.container = container;
    }

    public static void main(String[] args) {
        File file = new File("CoolBit.wav");
        Alarm alarm = new AlarmModel(new Audio(file));
        Container container = new AlarmContainer(alarm);
        App app = new App(container);
        app.init();
    }

    public void init() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Приложение \"Будильник\"");
        String choice = "";
        while (!choice.equals("0")) {
            menu();
            choice = scanner.next();
            switch (choice) {
                case "0" -> System.exit(0);
                case "1" -> System.out.println(container.getAlarm()
                        .getWatch()
                        .giveCurrentTime());
                case "2" -> {
                    System.out.println("Введите время: hh:mm");
                    String time = scanner.next().trim();
                    if (!container.add(time)) {
                        System.out.println("Время указано не корректно: " + time);
                        System.out.println("Будильник не установлен");
                    } else
                        System.out.println("Будильник установлен на " + time);
                }
                case "3" -> {
                    System.out.println("Введите время: hh:mm");
                    String time = scanner.next().trim();
                    if (container.remove(time)) {
                        System.out.println("Будильник на " + time + " удален");
                    } else {
                        System.out.println("Будильник на " + time + " не удален");
                    }
                }
                case "4" -> {
                    System.out.println("Введите часовой пояс GMT");
                    scanner.nextLine();
                    String zone = scanner.nextLine();
                    if (!container.changeTimeZone(zone))
                        System.out.println("Часовой пояс указан не корректно: " + zone);
                }
                case "5" -> System.out.println(container.getAlarm().giveRemainTime());
                case "6" -> {
                    String[] alarms = container.showAlarms();
                    for (String a : alarms) {
                        System.out.println();
                        System.out.println(a);
                    }
                }
                case "7" -> container.getAlarm().setStop(true);
                default -> System.out.println("Неверная команда.");
            }
        }
    }

    public void menu() {
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("1 - узнать текущее время и дату");
        System.out.println("2 - добавить будильник");
        System.out.println("3 - удалить будильник");
        System.out.println("4 - сменить часовой пояс");
        System.out.println("5 - осталось до сигнала");
        System.out.println("6 - показать будильники");
        System.out.println("7 - выключить будильник");
        System.out.println("0 - выход");
        System.out.print("Команда: ");
    }

}
