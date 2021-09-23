package com.krnchik.audio;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Audio {

    private Clip clip = null;
    private boolean playing = false;
    private boolean released = false;
    private long melodyDuration = 0;
    private long duration = 0;

    public Audio(File f) {
        try (AudioInputStream stream = AudioSystem.getAudioInputStream(f)) {
            clip = AudioSystem.getClip();
            clip.open(stream);
            melodyDuration = clip.getMicrosecondLength();
            released = true;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void signal() {
        if (isReleased()) {
            clip.setMicrosecondPosition(0);
            clip.start();
            playing = true;
        }
    }

    public void stop() {
        if (isReleased() && isPlaying()) {
            clip.stop();
            playing = false;
        }
    }

    public long getMelodyDuration() {
        return melodyDuration;
    }

    public void setMelodyDuration(long melodyDuration) {
        this.melodyDuration = melodyDuration;
    }

    public boolean isEndMelody() {
        return clip.getMicrosecondPosition() >= melodyDuration;
    }

    public void restartSignal() {
        if (isReleased()) {
            clip.stop();
            clip.setMicrosecondPosition(0);
            clip.start();
        }
    }

    public boolean isReleased() {
        return released;
    }

    public boolean isPlaying() {
        return playing;
    }
}
