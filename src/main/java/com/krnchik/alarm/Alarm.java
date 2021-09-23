package com.krnchik.alarm;

import com.krnchik.audio.Audio;
import com.krnchik.watch.Watch;

import java.util.Date;
import java.util.Timer;

public interface Alarm {
    boolean establishAlarm(String time);
    boolean isCorrectTime(String time);
    boolean awaken();
    void setStop(boolean stop);
    void candleAlarm();
    String giveRemainTime();
    String giveRemainTime(Date alarm);
    String giveRemainTime(String time);
    long giveRemainMinutes(Date alarm);
    Date giveAlarmDate(String time);
    Watch getWatch();
    Audio getAudio();
    Date getAlarm();
}
