package com.krnchik.model.alarm;

import java.util.*;

public class AlarmTimeContainer implements Container {

    private final TreeSet<AlarmTime> set;
    private final Alarm alarm;

    public AlarmTimeContainer(Alarm alarm) {
        this.set = new TreeSet<>();
        this.alarm = alarm;
        checkAwaken();
        checkSkippedAlarm();
    }

    @Override
    public boolean add(String time) {
        AlarmTime aw = new AlarmTime(alarm, time);
        if (aw.isNull())
            return false;
        rebootAlarms();
        set.add(new AlarmTime(alarm, time));
        establishAlarm();
        return true;
    }

    @Override
    public boolean remove(String time) {
        AlarmTime aw = new AlarmTime(alarm, time);
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

    @Override
    public void establishAlarm() {
        if (set.isEmpty()) {
            return;
        }
        String time = set.first().getTime();
        alarm.establishAlarm(time);
    }

    public String giveRemainTime() {
        return alarm.giveRemainTime();
    }

    @Override
    public String[] showAlarms() {
        String[] alarms = new String[set.size()];
        int i = 0;
        for (AlarmTime aw : set) {
            String time = aw.getTime();
            String str = time + "  " + alarm.giveRemainTime(time);
            if (i == 0) {
                str = time + "  " + giveRemainTime();
            }
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
                        Thread.sleep(1000);
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

    public void checkSkippedAlarm() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!alarm.awaken() && isSkippedAlarm()) {
                    remove(set.first().getTime());
                }
            }
        }, 0, 10250);
    }

    public boolean isSkippedAlarm() {
        return alarm.giveRemainTime().contains("-");
    }

    @Override
    public boolean changeTimeZone(String timeZone) {
        if (alarm.getWatch().setTimeZone(timeZone)) {
            rebootAlarms();
            establishAlarm();
            return true;
        }
        return false;
    }

    private void rebootAlarms() {
        TreeSet<AlarmTime> newSet = new TreeSet<>(set);
        set.clear();
        for (AlarmTime aw : newSet) {
            add(aw.getTime());
        }
    }

    @Override
    public Alarm getAlarm() {
        return alarm;
    }
}
