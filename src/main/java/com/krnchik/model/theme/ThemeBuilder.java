package com.krnchik.model.theme;

public abstract class ThemeBuilder {

    protected Theme theme;

    public void create() {
        theme = new Theme();
    }

    abstract void buildImage();
    abstract void buildSound();
    abstract void buildBackground();
    abstract void buildButtonTheme();
    abstract void buildTextFieldTheme();
    abstract void buildTime();

    public Theme getTheme() {
        return theme;
    }
}
