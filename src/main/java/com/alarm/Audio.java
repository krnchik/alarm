package com.alarm;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Audio {

    private AudioInputStream stream = null;
    private Clip clip = null;
    private boolean playing = false;
    private boolean released = false;
    private int duration = 0;

    public Audio(File f) {
        try {
            stream = AudioSystem.getAudioInputStream(f);
            clip = AudioSystem.getClip();
            clip.open(stream);
            released = true;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void signal(int milliseconds) {
        if (isReleased()) {
            Timer timer = new Timer();
            clip.setMicrosecondPosition(0);
            clip.start();
            playing = true;
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    duration++;
                    if (duration >= milliseconds && isPlaying()) {
                        playing = false;
                        duration = 0;
                        clip.stop();
                        clip.close();
                        timer.cancel();
                    }
                }
            }, 0, 1);
        }
    }

    public void stop() {
        if (isReleased() && isPlaying()) {
            clip.stop();
            playing = false;
        }
    }

    public boolean isReleased() {
        return released;
    }

    public boolean isPlaying() {
        return playing;

    }
}
