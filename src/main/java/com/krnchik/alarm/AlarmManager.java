package com.krnchik.alarm;

import com.krnchik.audio.Audio;
import com.krnchik.watch.ConsoleWatch;
import com.krnchik.watch.Watch;

import java.io.File;
import java.util.*;

public class AlarmManager {

    private TreeSet<AlarmWrapper> set;
    private Watch watch;
    private Audio audio;
    private Alarm alarm;

    public AlarmManager(Alarm alarm, File file) {
        this.set = new TreeSet<>();
        this.watch = ConsoleWatch.getInstance();
        this.audio = new Audio(file);
        this.alarm = new ConsoleAlarm(watch, audio);
        checkAwaken();
    }

    public boolean add(String time) {
        AlarmWrapper aw = new AlarmWrapper(alarm, time);
        if (aw.isNull())
            return false;
        rebootAlarms();
        set.add(new AlarmWrapper(alarm, time));
        establishAlarm();
        return true;
    }

    public boolean remove(String time) {
        AlarmWrapper aw = new AlarmWrapper(alarm, time);
        if (aw.isNull())
            return false;
        if (aw.equals(set.first())) {
            alarm.candleAlarm();
            set.remove(aw);
            establishAlarm();
            return true;
        }
        return set.remove(aw);
    }

    public boolean establishAlarm() {
        if (set.isEmpty()) {
            return false;
        }
        String time = set.first().getTime();
        return alarm.establishAlarm(time);
    }

    public String giveRemainTime() {
        return alarm.giveRemainTime();
    }

    public String[] showAlarms() {
        String[] alarms = new String[set.size()];
        int i = 0;
        for (AlarmWrapper aw : set) {
            String time = aw.getTime();
            String str = time + "  " + alarm.giveRemainTime(time);
            alarms[i++] = str;
        }
        return alarms;
    }

    public void checkAwaken() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (alarm.awaken()) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    set.remove(set.first());
                    establishAlarm();
                } else {
                    if (set.isEmpty() && !alarm.getAudio().isPlaying()) {
                        alarm.candleAlarm();
                    }
                }
            }
        }, 0, 1000);
    }

    public boolean changeTimeZone(String timeZone) {
        if (watch.setTimeZone(timeZone)) {
            rebootAlarms();
            establishAlarm();
            return true;
        }
        return false;
    }

    public String giveCurrentTime() {
        Date currentData = watch.getCurrentData();
        return watch.convertToString(currentData);
    }

    private void rebootAlarms() {
        TreeSet<AlarmWrapper> newSet = new TreeSet<>(set);
        set.clear();
        for (AlarmWrapper aw : newSet) {
            add(aw.getTime());
        }
    }

    public Alarm getAlarm() {
        return alarm;
    }
}
