package com.alarm;

import java.util.Date;

public interface Alarm {
    String getCurrentData();
    boolean giveSignal();
    boolean setAlarmDate(Date date);
    void candleAlarmDate();
    void init();
}
