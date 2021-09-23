package com.krnchik.alarm;

import com.krnchik.watch.ConsoleWatch;
import com.krnchik.watch.Watch;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ConsoleWatchTest {

    private Watch watch;
    private Alarm alarm;

    @Before
    public void setUp() {
        watch = ConsoleWatch.getInstance();
        alarm = new ConsoleAlarm();
    }

    @Test
    public void isTimeZone_correct_input() {
        assertThat(watch.setTimeZone("GMT+9")).isTrue();
        assertThat(watch.setTimeZone("GMT0")).isTrue();
        assertThat(watch.setTimeZone("GMT-9")).isTrue();
        assertThat(watch.setTimeZone("GMT+4")).isTrue();
        assertThat(watch.setTimeZone("GMT-7")).isTrue();
    }

    @Test
    public void isTimeZone_incorrect_input() {
        assertThat(watch.setTimeZone("GFT +9")).isFalse();
        assertThat(watch.setTimeZone("MMT +0")).isFalse();
        assertThat(watch.setTimeZone("GMT +0")).isFalse();
        assertThat(watch.setTimeZone("GMG -9")).isFalse();
        assertThat(watch.setTimeZone("GMT +11")).isFalse();
        assertThat(watch.setTimeZone("GMT -10")).isFalse();
    }
}