package com.krnchik.alarm;

import java.util.Objects;

public class AlarmWrapper implements Comparable<AlarmWrapper> {

    private Alarm alarm;
    private String time;
    private Long difference;

    public AlarmWrapper(Alarm alarm, String time) {
        time = time.trim();
        if (alarm.isCorrectTime(time)) {
            this.alarm = alarm;
            setTime(time);
        }
    }

    public boolean isNull() {
        return this.alarm == null || this.time == null
                || this.difference == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AlarmWrapper that = (AlarmWrapper) o;
        return Objects.equals(time, that.time);
    }

    @Override
    public int hashCode() {
        String hash = time.substring(0,2) + time.substring(3,5);
        return Integer.parseInt(hash);
    }

    @Override
    public int compareTo(AlarmWrapper aw) {
        if (time.equals(aw.getTime()))
            return 0;
        if (difference > aw.getDifference())
            return 1;
        else
            return -1;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
        this.difference = alarm.giveRemainMinutes(alarm.giveAlarmDate(time));
    }

    public Long getDifference() {
        return difference;
    }
}
