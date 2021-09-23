package com.krnchik.model.audio;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioModel implements Audio {

    private Clip clip = null;
    private boolean playing = false;
    private boolean released = false;
    private long melodyDuration = 0;

    public AudioModel(File f) {
        try (AudioInputStream stream = AudioSystem.getAudioInputStream(f)) {
            clip = AudioSystem.getClip();
            clip.open(stream);
            melodyDuration = clip.getMicrosecondLength();
            released = true;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start() {
        if (isReleased()) {
            clip.setMicrosecondPosition(0);
            clip.start();
            playing = true;
        }
    }

    @Override
    public void stop() {
        if (isReleased() && isPlaying()) {
            clip.stop();
            playing = false;
        }
    }

    @Override
    public void setMelodyDuration(long melodyDuration) {
        this.melodyDuration = melodyDuration;
    }

    @Override
    public boolean isEndMelody() {
        return clip.getMicrosecondPosition() >= melodyDuration;
    }

    @Override
    public boolean isPlaying() {
        return playing;
    }

    public boolean isReleased() {
        return released;
    }
}
