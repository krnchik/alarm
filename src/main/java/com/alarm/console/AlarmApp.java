package com.alarm.console;

import com.alarm.Alarm;
import com.alarm.Audio;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class AlarmApp {
    private final Alarm alarm;

    public AlarmApp(Alarm alarm) {
        this.alarm = alarm;
    }

    public static void main(String[] args) {
        AlarmApp app = new AlarmApp(new ConsoleAlarm());
        app.init();
    }

    public void init() {
        Scanner scanner = new Scanner(System.in);
        Timer timer = new Timer();
        System.out.println("Приложение \"Будильник\"");
        alarm.checkAwaken(timer);
        isPlaying(timer);
        String choice = "";
        while (!choice.equals("0")) {
            menu();
            choice = scanner.next();
            switch (choice) {
                case "0" -> System.exit(0);
                case "1" -> System.out.println(alarm.getWatch().getCurrentData());
                case "2" -> {
                    System.out.println("Введите время: hh:mm");
                    String time = scanner.next().trim();
                    if (!alarm.establishAlarm(time)) {
                        System.out.println("Время указано не корректно: " + time);
                        System.out.println("Будильник не установлен");
                    }
                    System.out.println("Будильник установлен на " + time);
                }
                case "3" -> {
                    alarm.candleAlarm();
                    System.out.println("Будильник отменен");
                }
                case "4" -> {
                    System.out.println("Введите часовой пояс GMT");
                    scanner.nextLine();
                    String zone = scanner.nextLine();
                    if (!alarm.getWatch().setTimeZone(zone))
                        System.out.println("Часовой пояс указан не корректно: " + zone);
                }
                case "5" -> System.out.println(alarm.giveRemainTime());
                case "6" -> alarm.setSignaling(false);
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
        System.out.println("2 - установить будильник");
        System.out.println("3 - отменить будильник");
        System.out.println("4 - сменить часовой пояс");
        System.out.println("5 - осталось до сигнала");
        System.out.println("0 - выход");
        System.out.print("Команда: ");
    }

    public void isPlaying(Timer timer) {
        timer.schedule(new TimerTask() {
            int count = 0;

            @Override
            public void run() {
                Audio audio = alarm.getAudio();
                if (audio != null) {
                    if (audio.isPlaying() && count == 0){
                        awakenMenu();
                        count++;
                    }
                }
            }
        }, 0, 1000);
    }

    public void awakenMenu() {
        System.out.println();
        System.out.println("The alarm clock ring!!!");
        System.out.println("6 - выключить сигнал");
        System.out.print("Команда: ");
    }
}
