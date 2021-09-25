package com.krnchik.model.watch;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class WatchModel implements Watch {

    private static WatchModel instance;
    String defaultZone = "GMT+04";
    TimeZone timeZone = TimeZone.getTimeZone(defaultZone);
    SimpleDateFormat dateFormat;

    private WatchModel() {
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy E HH:mm:ss");
    }

    public static WatchModel getInstance() {
        if (instance == null) {
            synchronized (WatchModel.class) {
                if (instance == null)
                    instance = new WatchModel();
            }
        }
        return instance;
    }

    @Override
    public synchronized Date getCurrentData() {
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
    public String giveCurrentTime() {
        Date currentData = getCurrentData();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy EEEE HH:mm");
        sdf.setTimeZone(timeZone);
        return sdf.format(currentData);
    }

    public boolean isCorrectTime(String time) {
        return time.matches("[0-2][0-9]:[0-5][0-9]");
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
    public TimeZone getTimeZone() {
        return timeZone;
    }

    public boolean isTimeZone(String timeZone) {
        return timeZone.matches("GMT[+,-][1-9]") ||
                timeZone.matches("GMT[0]") ||
                timeZone.matches("GMT[+,-][1][0-2]");
    }
}
