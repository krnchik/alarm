package com.alarm.console;

import com.alarm.Alarm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ConsoleAlarm implements Alarm {

    private Date alarmDate;

    @Override
    public void init() {
        Scanner scanner = new Scanner(System.in);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                giveSignal();
            }
        }, 1000, 300);
        System.out.println("Приложение \"Будильник\"");
        int choice = Integer.MAX_VALUE;
        while (choice != 0) {
            menu();
            System.out.print("Команда: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 0:
                    timer.cancel();
                    break;
                case 1:
                    System.out.println(getCurrentData());
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
                    candleAlarmDate();
                    break;
                default:
                    System.out.println("Неверная команда.");
                    break;
            }
        }
    }

    public boolean establishAlarm(String time) throws ParseException {
        if (!isCorrectTime(time)){
            System.out.println("Время указано не корректно: " + time);
            return false;
        }
        Date date = new SimpleDateFormat("dd/MM/yyyy E HH:mm:ss")
                .parse(getCurrentData().substring(0, 14) + time + ":00");
        if (!setAlarmDate(date)){
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DAY_OF_YEAR, 1);
            setAlarmDate(cal.getTime());
        }
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
    }

    @Override
    public String getCurrentData() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy E HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+04"));
        return dateFormat.format(new Date());
    }

    @Override
    public boolean giveSignal() {
        if (alarmDate == null)
            return false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy E HH:mm:ss");
        if (dateFormat.format(alarmDate).equals(getCurrentData())) {
            System.out.println();
            System.out.println("The alarm clock ring!!!");
            return true;
        }
        return false;
    }

    @Override
    public boolean setAlarmDate(Date alarmDate) {
        Date currentDate = new Date();
        if (alarmDate.after(currentDate)) {
            this.alarmDate = alarmDate;
            return true;
        }
        return false;
    }

    @Override
    public void candleAlarmDate() {
        this.alarmDate = null;
    }
}
