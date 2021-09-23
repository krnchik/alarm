package com.krnchik.model.watch;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public interface Watch {
    Date getCurrentData();
    String giveCurrentTime();
    TimeZone getTimeZone();
    boolean isCorrectTime(String time);
    boolean setTimeZone(String timeZone);
    String convertToString(Date date);
    SimpleDateFormat getDateFormat();
}
