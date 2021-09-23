package com.krnchik.alarm;

import com.krnchik.audio.Audio;

import java.util.Date;

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
    Audio getAudio();
}
