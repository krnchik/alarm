package com;

import com.alarm.console.ConsoleAlarm;

import java.util.Date;

public class Test {
    public static void main(String[] args) throws InterruptedException {
//        ConsoleAlarm alarm = new ConsoleAlarm();
//        System.out.println(alarm.getCurrentData());
//        alarm.setAlarmDate(new Date(123456));
//        Thread.sleep(100);
//        alarm.giveSignal();
        System.out.println(doSomething());
    }

    public static String doSomething() {
        try {
            int x = 10/0;
            return "try";
        } catch (Exception e) {
            e.printStackTrace();
            return "catch";
        } finally {
            return "finally";
        }
    }
}
