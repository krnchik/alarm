package com.alarm.console;

import com.alarm.Alarm;
import com.alarm.Audio;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ConsoleAlarm implements Alarm {

    private Date alarmDate;
    private final File SOUND_FILE = new File("CoolBit.wav");
    private final int SIGNAL_DURATION = 1 * 60 * 1000;
    private boolean signaling = true;
    private Audio audio = null;

    @Override
    public void init() {
        Scanner scanner = new Scanner(System.in);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                awaken();
            }
        }, 0, 1000);
        System.out.println("Приложение \"Будильник\"");
        int choice = Integer.MAX_VALUE;
        while (choice != 0) {
            menu();
            choice = scanner.nextInt();
            switch (choice) {
                case 0:
                    timer.cancel();
                    break;
                case 1:
//                    System.out.println(getCurrentData());
                    break;
                case 2:
                    try {
                        System.out.println("Введите время: hh:mm");
                        String time = scanner.next().trim();
                        if (!establishAlarm(time))
                            System.out.println("Будильник не установлен");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    candleAlarm();
                    break;
                case 4:
                    signaling = false;
                    break;
                default:
                    System.out.println("Неверная команда.");
                    break;
            }
        }
    }

    public boolean establishAlarm(String time) throws ParseException {
//        if (!isCorrectTime(time)){
//            System.out.println("Время указано не корректно: " + time);
//            return false;
//        }
//        Date date = new SimpleDateFormat("dd/MM/yyyy E HH:mm:ss")
////                .parse(getCurrentData().substring(0, 14) + time + ":00");
//        if (!setAlarm(date)){
//            Calendar cal = Calendar.getInstance();
//            cal.setTime(date);
//            cal.add(Calendar.DAY_OF_YEAR, 1);
//            setAlarm(cal.getTime());
//        }
        return true;
    }

    public boolean isCorrectTime(String time) {
        return time.matches("[0-2][0-9]:[0-5][0-9]");
    }

    public void menu() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("1 - узнать текущее время и дату");
        System.out.println("2 - установить будильник");
        System.out.println("3 - отменить будильник");
        System.out.println("0 - выход");
        System.out.print("Команда: ");
    }

    @Override
    public boolean awaken() {
        if (alarmDate == null)
            return false;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy E HH:mm:ss");
//        if (dateFormat.format(alarmDate).equals(getCurrentData())) {
//            signalMenu();
//            audio = new Audio(SOUND_FILE);
//            audio.signal(SIGNAL_DURATION);
//            return true;
//        }

        if (audio != null) {
            if (!audio.isPlaying()) {
                signaling = false;
                System.out.println();
                menu();
            }

            if (!isSignaling()) {
                signaling = true;
                audio.stop();
                audio = null;
                candleAlarm();
            }
        }

        return false;
    }

    public void signalMenu() {
        System.out.println();
        System.out.println("The alarm clock ring!!!");
        System.out.println("4 - выключить сигнал");
        System.out.print("Команда: ");
    }

    @Override
    public boolean setAlarm(Date alarmDate) {
        Date currentDate = new Date();
        if (alarmDate.after(currentDate)) {
            this.alarmDate = alarmDate;
            return true;
        }
        return false;
    }

    @Override
    public void candleAlarm() {
        this.alarmDate = null;
    }

    public boolean isSignaling() {
        return signaling;
    }
}
