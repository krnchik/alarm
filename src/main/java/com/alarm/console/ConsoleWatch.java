package com.alarm.console;

import com.alarm.Watch;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ConsoleWatch implements Watch {

    TimeZone timeZone = TimeZone.getTimeZone("GMT+04");

    @Override
    public String getCurrentData() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy E HH:mm:ss");
        dateFormat.setTimeZone(timeZone);
        return dateFormat.format(new Date());
    }

    @Override
    public boolean setTimeZone(String timeZone) {
        if (isTimeZone(timeZone)) {
            this.timeZone = TimeZone.getTimeZone(timeZone);
            return true;
        }
        return false;
    }

    public boolean isTimeZone(String timeZone) {
        return timeZone.matches("GMT[+,-][1-9]") ||
                timeZone.matches("GMT");
    }
}
