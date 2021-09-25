package com.krnchik.model.history;

import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public interface History {
    void write(Date date, TimeZone timeZone);

    List<String> giveHistory();
}
