package com.krnchik.watch;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public interface Watch {
    Date getCurrentData();
    TimeZone getTimeZone();
    boolean setTimeZone(String timeZone);
    String convertToString(Date date);
    SimpleDateFormat getDateFormat();
}
