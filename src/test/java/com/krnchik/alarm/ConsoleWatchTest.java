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
        watch = new ConsoleWatch();
    }

    @Test
    public void isTimeZone_correct_input() {
        assertThat(watch.setTimeZone(alarm,"GMT +9")).isTrue();
        assertThat(watch.setTimeZone(alarm,"GMT 0")).isTrue();
        assertThat(watch.setTimeZone(alarm,"GMT -9")).isTrue();
        assertThat(watch.setTimeZone(alarm,"GMT +4")).isTrue();
        assertThat(watch.setTimeZone(alarm,"GMT -7")).isTrue();
    }

    @Test
    public void isTimeZone_incorrect_input() {
        assertThat(watch.setTimeZone(alarm,"GFT +9")).isFalse();
        assertThat(watch.setTimeZone(alarm,"MMT +0")).isFalse();
        assertThat(watch.setTimeZone(alarm,"GMT +0")).isFalse();
        assertThat(watch.setTimeZone(alarm,"GMG -9")).isFalse();
        assertThat(watch.setTimeZone(alarm,"GMT +11")).isFalse();
        assertThat(watch.setTimeZone(alarm,"GMT -10")).isFalse();
    }
}