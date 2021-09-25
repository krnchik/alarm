package com.krnchik.model.alarm;

import com.krnchik.model.audio.Audio;
import com.krnchik.model.history.History;
import com.krnchik.model.watch.Watch;

public interface Alarm {
    void establishAlarm(String time);

    boolean awaken();

    void setStop(boolean stop);

    void candleAlarm();

    String giveRemainTime();

    String giveRemainTime(String time);

    long giveRemainMinutes(String time);

    Audio getAudio();

    Watch getWatch();

    History getHistory();
}
