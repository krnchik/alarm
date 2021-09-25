package com.krnchik.model;

import com.krnchik.model.alarm.Alarm;
import com.krnchik.model.alarm.AlarmModel;
import com.krnchik.model.alarm.AlarmTimeContainer;
import com.krnchik.model.alarm.Container;
import com.krnchik.model.audio.Audio;
import com.krnchik.model.audio.AudioModel;
import com.krnchik.model.history.History;
import com.krnchik.model.history.HistoryModel;
import com.krnchik.model.theme.Domestic;
import com.krnchik.model.theme.Theme;
import com.krnchik.model.theme.ThemeBuilder;
import com.krnchik.model.theme.ThemeModel;

public class DefaultModel {

    private Audio audio;
    private History history;
    private Alarm alarm;
    private Container container;
    private Theme theme;
    private ThemeModel themeModel;
    private ThemeBuilder defaultTheme = new Domestic();


    public DefaultModel() {
        themeModel = new ThemeModel(defaultTheme);
        theme = themeModel.buildTheme();
        audio = new AudioModel(theme.getSound());
        history = new HistoryModel();
        alarm = new AlarmModel(audio, history);
        container = new AlarmTimeContainer(alarm);
    }

    public Audio getAudio() {
        return audio;
    }

    public History getHistory() {
        return history;
    }

    public Alarm getAlarm() {
        return alarm;
    }

    public Container getContainer() {
        return container;
    }

    public Theme getMode() {
        return theme;
    }

    public void setMode(ThemeBuilder builder) {
        themeModel.setBuilder(builder);
        this.theme = themeModel.buildTheme();
    }
}
