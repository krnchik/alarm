package com.krnchik.model.mode;

import org.w3c.dom.Text;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.sql.Time;

public class Mode {

    private File sound;
    private Image image;

    private Color background;

    private Color backgroundButton;
    private Color foregroundButton;
    private Font fontButton;

    private Color backgroundTextField;
    private Color foregroundTextField;
    private Font fontTextField;

    private Point time;
    private Color backgroundTime;
    private Color foregroundTime;
    private Font fontTime;

    public void setSound(File sound) {
        this.sound = sound;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setBackground(Color background) {
        this.background = background;
    }

    public void setBackgroundButton(Color backgroundButton) {
        this.backgroundButton = backgroundButton;
    }

    public void setForegroundButton(Color foregroundButton) {
        this.foregroundButton = foregroundButton;
    }

    public void setFontButton(Font fontButton) {
        this.fontButton = fontButton;
    }

    public void setBackgroundTextField(Color backgroundTextField) {
        this.backgroundTextField = backgroundTextField;
    }

    public void setForegroundTextField(Color foregroundTextField) {
        this.foregroundTextField = foregroundTextField;
    }

    public void setFontTextField(Font fontTextField) {
        this.fontTextField = fontTextField;
    }

    public void setTime(Point time) {
        this.time = time;
    }

    public void setBackgroundTime(Color backgroundTime) {
        this.backgroundTime = backgroundTime;
    }

    public void setForegroundTime(Color foregroundTime) {
        this.foregroundTime = foregroundTime;
    }

    public void setFontTime(Font fontTime) {
        this.fontTime = fontTime;
    }
}
