package com.krnchik.alarm;

import com.krnchik.audio.Audio;
import com.krnchik.watch.ConsoleWatch;
import com.krnchik.watch.Watch;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class ConsoleAlarm implements Alarm {

    private final Watch watch;
    private Audio audio;
    private long duration;
    private Date alarm;
    private boolean signaling = false;
    private boolean stop = false;

    public ConsoleAlarm() {
        this.watch = ConsoleWatch.getInstance();
    }

    public ConsoleAlarm(Watch watch, Audio audio) {
        this.watch = watch;
        this.audio = audio;
        this.duration = audio.getMelodyDuration();
    }

    @Override
    public boolean establishAlarm(String time) {
        if (time == null)
            throw new IllegalArgumentException();

        if (!isCorrectTime(time)) {
            return false;
        }

        setAlarm(giveAlarmDate(time));
        return true;
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

    @Override
    public String giveRemainTime(Date alarm) {
        if (alarm == null)
            return "Будильник не установлен";

        Date currentDate = getCurrentData();
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
    public long giveRemainMinutes(Date alarm) {
        Date currentDate = getCurrentData();
        long diff = alarm.getTime() - currentDate.getTime();
        return TimeUnit.MILLISECONDS.toMinutes(diff);
    }

    @Override
    public Date getAlarm() {
        return alarm;
    }

    @Override
    public Date giveAlarmDate(String time) {
        if (!isCorrectTime(time))
            throw new IllegalArgumentException();
        Date currentDate = getCurrentData();
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
        SimpleDateFormat sdf = watch.getDateFormat();
        String date = watch.convertToString(alarmDate);
        this.alarm = parseToAlarmDate(date);
    }

    @Override
    public boolean awaken() {
        if (alarm == null)
            return false;

        String alarmTime = watch.convertToString(alarm);
        String currentTime = watch.convertToString(getCurrentData());
        if (alarmTime.equals(currentTime)) {
            if (isSignaling()) {
                audio.restartSignal();
            }
            audio.signal();
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

    public Date parseToAlarmDate(String dateStr) {
        Date date;
        try {
            SimpleDateFormat sdf = watch.getDateFormat();
            sdf.setTimeZone(watch.getTimeZone());
            date = sdf.parse(dateStr);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException();
    }

    public boolean isSignaling() {
        return signaling;
    }

    public boolean isCorrectTime(String time) {
        return time.matches("[0-2][0-9]:[0-5][0-9]");
    }

    private Date getCurrentData() {
        return watch.getCurrentData();
    }
}
