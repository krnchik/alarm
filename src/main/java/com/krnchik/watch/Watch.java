package com.krnchik.watch;

import com.krnchik.alarm.Alarm;

import java.text.SimpleDateFormat;
import java.util.Date;

public interface Watch {
    Date getCurrentData();
    boolean setTimeZone(Alarm alarm,  String timeZone);
    String convertToString(Date date);
    SimpleDateFormat getDateFormat();
    Date parseToDate(String dateStr);
}
