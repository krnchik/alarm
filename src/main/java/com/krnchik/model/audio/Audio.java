package com.krnchik.model.audio;

import java.io.File;

public interface Audio {
    void start();

    void stop();

    void setMelodyDuration(long melodyDuration);

    boolean isEndMelody();

    boolean isPlaying();

    void changeTrek(File file);

    void setVolume(String volume);

    String getVolume();
}
