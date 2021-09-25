package com.krnchik.model.theme;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Theme {

    private File sound;
    private ImageIcon image;

    private Color background;

    private Color backgroundButton;
    private Color foregroundButton;
    private Font fontButton;

    private Color backgroundTextField;
    private Color foregroundTextField;
    private Font fontTextField;

    private Point time;
    private Color colorTime;
    private Font fontTime;

    public File getSound() {
        return sound;
    }

    public void setSound(File sound) {
        this.sound = sound;
    }

    public ImageIcon getImage() {
        return image;
    }

    public void setImage(ImageIcon image) {
        this.image = image;
    }

    public Color getBackground() {
        return background;
    }

    public void setBackground(Color background) {
        this.background = background;
    }

    public Color getBackgroundButton() {
        return backgroundButton;
    }

    public void setBackgroundButton(Color backgroundButton) {
        this.backgroundButton = backgroundButton;
    }

    public Color getForegroundButton() {
        return foregroundButton;
    }

    public void setForegroundButton(Color foregroundButton) {
        this.foregroundButton = foregroundButton;
    }

    public Font getFontButton() {
        return fontButton;
    }

    public void setFontButton(Font fontButton) {
        this.fontButton = fontButton;
    }

    public Color getBackgroundTextField() {
        return backgroundTextField;
    }

    public void setBackgroundTextField(Color backgroundTextField) {
        this.backgroundTextField = backgroundTextField;
    }

    public Color getForegroundTextField() {
        return foregroundTextField;
    }

    public void setForegroundTextField(Color foregroundTextField) {
        this.foregroundTextField = foregroundTextField;
    }

    public Font getFontTextField() {
        return fontTextField;
    }

    public void setFontTextField(Font fontTextField) {
        this.fontTextField = fontTextField;
    }

    public Point getTime() {
        return time;
    }

    public void setTime(Point time) {
        this.time = time;
    }

    public Color getColorTime() {
        return colorTime;
    }

    public void setColorTime(Color colorTime) {
        this.colorTime = colorTime;
    }

    public Font getFontTime() {
        return fontTime;
    }

    public void setFontTime(Font fontTime) {
        this.fontTime = fontTime;
    }
}
