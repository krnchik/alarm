package com.krnchik.model.alarm;

public interface Container {
    boolean add(String time);

    boolean remove(String time);

    void establishAlarm();

    String[] showAlarms();

    boolean changeTimeZone(String timeZone);

    Alarm getAlarm();
}
