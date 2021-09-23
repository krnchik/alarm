package com.krnchik.watch;

import com.krnchik.alarm.Alarm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ConsoleWatch implements Watch {

    private static ConsoleWatch instance;
    String defaultZone = "GMT+04";
    TimeZone timeZone = TimeZone.getTimeZone(defaultZone);
    SimpleDateFormat dateFormat;

    private ConsoleWatch() {
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy E HH:mm:ss");
    }

    public static ConsoleWatch getInstance() {
        if (instance == null) {
            synchronized (ConsoleWatch.class) {
                if (instance == null)
                    instance = new ConsoleWatch();
            }
        }
        return instance;
    }

    @Override
    public Date getCurrentData() {
        dateFormat.setTimeZone(timeZone);
        String date = convertToString(new Date());
        return parseToDate(date);
    }

    @Override
    public boolean setTimeZone(String timeZone) {
        if (isTimeZone(timeZone)) {
            this.timeZone = TimeZone.getTimeZone(timeZone);
            return true;
        }
        return false;
    }

    @Override
    public String convertToString(Date date) {
        return dateFormat.format(date);
    }

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

    @Override
    public String getDefaultZone() {
        return defaultZone;
    }

    @Override
    public TimeZone getTimeZone() {
        return timeZone;
    }

    public boolean isTimeZone(String timeZone) {
        return timeZone.matches("GMT[+,-][1-9]") ||
                timeZone.matches("GMT[0]") ||
                timeZone.matches("GMT[+,-][1][0-2]");
    }
}
