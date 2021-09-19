package com.krnchik.alarm;

import com.krnchik.audio.Audio;
import com.krnchik.watch.Watch;

import java.util.Date;
import java.util.Timer;

public interface Alarm {
    boolean awaken();
    boolean establishAlarm(String time);
    void setSignaling(boolean signaling);
    void candleAlarm();
    String giveRemainTime();
    void checkAwaken(Timer timer);
    String getAlarmTime();
    Watch getWatch();
    Audio getAudio();
    Date getAlarm();
}
