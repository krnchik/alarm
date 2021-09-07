package com.alarm;

import java.util.Date;

public interface Alarm {
    boolean awaken();
    boolean setAlarm(Date date);
    void candleAlarm();
    void init();
}
