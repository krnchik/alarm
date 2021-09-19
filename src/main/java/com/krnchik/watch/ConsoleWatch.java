package com.krnchik.watch;

import com.krnchik.alarm.Alarm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ConsoleWatch implements Watch {

    TimeZone timeZone = TimeZone.getTimeZone("GMT+04");
    SimpleDateFormat dateFormat;

    public ConsoleWatch() {
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy E HH:mm:ss");
    }

    @Override
    public Date getCurrentData() {
        dateFormat.setTimeZone(timeZone);
        String date = convertToString(new Date());
        return parseToDate(date);
    }

    @Override
    public boolean setTimeZone(Alarm alarm, String timeZone) {
        if (isTimeZone(timeZone)) {
            this.timeZone = TimeZone.getTimeZone(timeZone);
            if (alarm.getAlarmTime() != null) {
                alarm.establishAlarm(alarm.getAlarmTime());
            }
            return true;
        }
        return false;
    }

    @Override
    public String convertToString(Date date) {
        return dateFormat.format(date);
    }

    @Override
    public Date parseToDate(String dateStr) {
        Date date;
        try {
            date = dateFormat.parse(dateStr);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException();
    }

    @Override
    public SimpleDateFormat getDateFormat() {
        return dateFormat;
    }

    public boolean isTimeZone(String timeZone) {
        return timeZone.matches("GMT[+,-][1-9]") ||
                timeZone.matches("GMT[0]") ||
                timeZone.matches("GMT[+,-][1][0-2]");
    }
}
