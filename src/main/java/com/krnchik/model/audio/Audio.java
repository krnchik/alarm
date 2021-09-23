package com.krnchik.model.audio;

public interface Audio {
    void start();
    void stop();
    void setMelodyDuration(long melodyDuration);
    boolean isEndMelody();
    boolean isPlaying();
}
