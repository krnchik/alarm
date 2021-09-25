package com.krnchik.model.alarm;

import com.krnchik.model.audio.Audio;
import com.krnchik.model.history.History;
import com.krnchik.model.watch.WatchModel;
import com.krnchik.model.watch.Watch;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class AlarmModel implements Alarm {

    private final Watch watch;
    private final Audio audio;
    private final History history;
    private Date alarm;
    private boolean signaling = false;
    private boolean stop = false;

    public AlarmModel(Audio audio, History history) {
        this.history = history;
        this.watch = WatchModel.getInstance();
        this.audio = audio;
    }

    @Override
    public void establishAlarm(String time) {
        if (time == null || !watch.isCorrectTime(time))
            throw new IllegalArgumentException(time);

        setAlarm(giveAlarmDate(time));
    }

    @Override
    public void candleAlarm() {
        this.alarm = null;
    }

    @Override
    public Watch getWatch() {
        return watch;
    }

    @Override
    public void setStop(boolean stop) {
        this.stop = stop;
    }

    @Override
    public Audio getAudio() {
        return audio;
    }

    @Override
    public String giveRemainTime() {
        return giveRemainTime(this.alarm);
    }

    @Override
    public String giveRemainTime(String time) {
        Date date = giveAlarmDate(time);
        return giveRemainTime(date);
    }

    public String giveRemainTime(Date alarm) {
        if (alarm == null)
            return "Будильник не установлен";

        Date currentDate = giveCurrentData();
        long diff = alarm.getTime() - currentDate.getTime();
        long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
        long hours = TimeUnit.MILLISECONDS.toHours(diff);

        if (minutes >= 60) {
            minutes = minutes - hours * 60;
            if (minutes == 0)
                return "Через " + hours + " ч ";

            return "Через " + hours + " ч " + minutes + " мин";
        }

        if (minutes == 0)
            return "Менее 1 мин";

        return "Через " + minutes + " мин";
    }

    @Override
    public long giveRemainMinutes(String time) {
        if (!watch.isCorrectTime(time))
            throw new IllegalArgumentException(time);
        Date alarm = giveAlarmDate(time);
        Date currentDate = giveCurrentData();
        long diff = alarm.getTime() - currentDate.getTime();
        return TimeUnit.MILLISECONDS.toMinutes(diff);
    }

    public Date giveAlarmDate(String time) {
        if (!watch.isCorrectTime(time))
            throw new IllegalArgumentException(time);
        Date currentDate = giveCurrentData();
        String current = watch.convertToString(currentDate);
        Date date = parseToAlarmDate(current.substring(0, 14) + time + ":00");
        if (!date.after(currentDate)) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DAY_OF_YEAR, 1);
            return cal.getTime();
        }
        return date;
    }

    public void setAlarm(Date alarmDate) {
        String date = watch.convertToString(alarmDate);
        this.alarm = parseToAlarmDate(date);
    }

    @Override
    public boolean awaken() {
        if (alarm == null)
            return false;

        String alarmTime = watch.convertToString(alarm);
        String currentTime = watch.convertToString(giveCurrentData());
        if (alarmTime.equals(currentTime)) {
            audio.start();
            history.write(alarm, watch.getTimeZone());
            signaling = true;
            return true;
        }

        if (audio.isEndMelody()) {
            stop = true;
        }

        if (isSignaling()) {
            if (stop ^ !audio.isPlaying()) {
                signaling = false;
                stop = false;
                audio.stop();
            }
        }

        return false;
    }

    public Date parseToAlarmDate(String date) {
        Date alarmDate;
        try {
            SimpleDateFormat sdf = watch.getDateFormat();
            sdf.setTimeZone(watch.getTimeZone());
            alarmDate = sdf.parse(date);
            return alarmDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException(date);
    }

    public boolean isSignaling() {
        return signaling;
    }

    @Override
    public History getHistory() {
        return history;
    }

    private Date giveCurrentData() {
        return watch.getCurrentData();
    }
}
