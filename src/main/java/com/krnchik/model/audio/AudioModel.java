package com.krnchik.model.audio;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioModel implements Audio {

    private Clip clip = null;
    private boolean playing = false;
    private boolean released = false;
    private long melodyDuration = 0;
    private FloatControl volumeControl = null;

    public AudioModel(File file) {
        try (AudioInputStream stream = AudioSystem.getAudioInputStream(file)) {
            clip = AudioSystem.getClip();
            clip.open(stream);
            melodyDuration = clip.getMicrosecondLength();
            volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
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
    public void changeTrek(File file) {
        clip.close();
        released = false;
        try (AudioInputStream stream = AudioSystem.getAudioInputStream(file)) {
            clip = AudioSystem.getClip();
            clip.open(stream);
            melodyDuration = clip.getMicrosecondLength();
            released = true;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setVolume(String volume) {
        volume = "0." + volume;
        if (isVolume(volume))
            throw new IllegalArgumentException(volume);
        float x = Float.parseFloat(volume);
        if (x < 0) x = 0;
        if (x > 1) x = 1;
        float min = volumeControl.getMinimum();
        float max = volumeControl.getMaximum();
        volumeControl.setValue((max - min) * x + min);
    }

    public boolean isVolume(String volume) {
        return volume.matches("[0.][0-9][0-9]") ||
                volume.matches("[0.][0-9]");
    }

    @Override
    public String getVolume() {
        float v = volumeControl.getValue();
        float min = volumeControl.getMinimum();
        float max = volumeControl.getMaximum();
        float volume = (v - min) / (max - min);
        return String.valueOf(volume * 100)
                .replaceAll("[.][0-9]+", "");
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
