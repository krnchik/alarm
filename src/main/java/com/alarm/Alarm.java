package com.alarm;

import java.util.Timer;

public interface Alarm {
    boolean awaken();
    boolean establishAlarm(String time);
    void setSignaling(boolean signaling);
    void candleAlarm();
    String giveRemainTime();
    void checkAwaken(Timer timer);
    Watch getWatch();
    Audio getAudio();
}
