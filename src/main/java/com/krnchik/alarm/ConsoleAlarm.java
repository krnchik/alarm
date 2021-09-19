package com.krnchik.alarm;

import com.krnchik.audio.Audio;
import com.krnchik.watch.ConsoleWatch;
import com.krnchik.watch.Watch;

import java.io.File;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class ConsoleAlarm implements Alarm {

    private Date alarm;
    private String alarmTime;
    private final File SOUND_FILE = new File("CoolBit.wav");
    private final int SIGNAL_DURATION = 1 * 60 * 1000;
    private boolean signaling = true;
    private Audio audio = null;
    private final Watch watch;

    public ConsoleAlarm() {
        this.watch = new ConsoleWatch();
    }

    @Override
    public boolean establishAlarm(String time) {
        if (time == null)
            throw new IllegalArgumentException();

        if (!isCorrectTime(time)) {
            return false;
        }

        String currentDate = watch.convertToString(getCurrentData());
        Date date = watch.parseToDate(currentDate.substring(0, 14) + time + ":00");
        if (!setAlarm(date)) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DAY_OF_YEAR, 1);
            setAlarm(cal.getTime());
        }
        this.alarmTime = time;
        return true;
    }

    @Override
    public void candleAlarm() {
        this.alarm = null;
        this.alarmTime = null;
    }

    @Override
    public boolean awaken() {
        if (alarm == null)
            return false;

        String alarmTime = watch.convertToString(alarm);
        String currentTime = watch.convertToString(getCurrentData());
        if (alarmTime.equals(currentTime)) {
            audio = new Audio(SOUND_FILE);
            audio.signal(SIGNAL_DURATION);
            return true;
        }

        if (audio != null) {
            if (!audio.isPlaying()) {
                signaling = false;
            }

            if (!isSignaling()) {
                signaling = true;
                audio.stop();
                audio = null;
                candleAlarm();
            }
        }

        return false;
    }

    @Override
    public void checkAwaken(Timer timer) {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                awaken();
            }
        }, 0, 1000);
    }

    @Override
    public Watch getWatch() {
        return watch;
    }

    @Override
    public void setSignaling(boolean signaling) {
        this.signaling = signaling;
    }

    @Override
    public Audio getAudio() {
        return audio;
    }

    @Override
    public String giveRemainTime() {
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
    public Date getAlarm() {
        return alarm;
    }

    @Override
    public String getAlarmTime() {
        return alarmTime;
    }

    public boolean setAlarm(Date alarmDate) {
        Date currentDate = getCurrentData();
        if (alarmDate.after(currentDate)) {
            this.alarm = alarmDate;
            return true;
        }
        return false;
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
