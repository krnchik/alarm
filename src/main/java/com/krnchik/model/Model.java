package com.krnchik.model;

import com.krnchik.model.alarm.Alarm;
import com.krnchik.model.alarm.Container;
import com.krnchik.model.audio.Audio;
import com.krnchik.model.history.History;
import com.krnchik.model.theme.Theme;
import com.krnchik.model.theme.ThemeBuilder;

public interface Model {
    Audio getAudio();

    History getHistory();

    Alarm getAlarm();

    Container getContainer();

    Theme getMode();

    void setMode(ThemeBuilder builder);
}
