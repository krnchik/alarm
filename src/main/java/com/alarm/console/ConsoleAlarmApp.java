package com.alarm.console;

import com.alarm.Alarm;

public class ConsoleAlarmApp {
    public static void main(String[] args) {
        Alarm alarm = new ConsoleAlarm();
        alarm.init();
    }
}
