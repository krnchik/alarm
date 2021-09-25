package com.krnchik.model.mode;

public abstract class ModeBuilder {

    protected Mode mode;

    public void create() {
        mode = new Mode();
    }

    abstract void buildImage();
    abstract void buildFile();
    abstract void buildBackground();
    abstract void buildButtonTheme();
    abstract void buildTextFieldTheme();
    abstract void buildTime();

    public Mode getMode() {
        return mode;
    }
}
