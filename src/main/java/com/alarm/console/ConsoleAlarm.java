package com.alarm.console;

import com.alarm.Alarm;
import com.alarm.Audio;
import com.alarm.Watch;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class ConsoleAlarm implements Alarm {

    private Date alarm;
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
        if (!isCorrectTime(time)){
            return false;
        }
        Date date = null;
        try {
            date = giveSimpleDateFormat()
                    .parse(getCurrentData().substring(0, 14) + time + ":00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date == null)
            return false;
        if (!setAlarm(date)){
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DAY_OF_YEAR, 1);
            setAlarm(cal.getTime());
        }
        return true;
    }

    @Override
    public void candleAlarm() {
        this.alarm = null;
    }

    @Override
    public boolean awaken() {
        if (alarm == null)
            return false;

        SimpleDateFormat dateFormat = giveSimpleDateFormat();
        if (dateFormat.format(alarm).equals(getCurrentData())) {
            audio = new Audio(SOUND_FILE);
            audio.signal(SIGNAL_DURATION);
            return true;
        }

        if (audio != null) {
            if (!audio.isPlaying()) {
                signaling = false;
                System.out.println();
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

        SimpleDateFormat sdf = giveSimpleDateFormat();
        try {
            Date currentDate = sdf.parse(getCurrentData());
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
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public SimpleDateFormat giveSimpleDateFormat() {
        return new SimpleDateFormat("dd/MM/yyyy E HH:mm:ss");
    }

    public boolean setAlarm(Date alarmDate) {
        Date currentDate = new Date();
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

    private String getCurrentData() {
        return watch.getCurrentData();
    }
}
