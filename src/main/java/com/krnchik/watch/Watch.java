package com.krnchik.watch;

import com.krnchik.alarm.Alarm;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public interface Watch {
    Date getCurrentData();
    String getDefaultZone();
    TimeZone getTimeZone();
    boolean setTimeZone(String timeZone);
    String convertToString(Date date);
    SimpleDateFormat getDateFormat();
}
