package com.krnchik.model.theme;

public class ThemeModel {

    private ThemeBuilder builder;

    public ThemeModel(ThemeBuilder builder) {
        this.builder = builder;
    }

    public Theme buildTheme() {
        builder.create();
        builder.buildSound();
        builder.buildImage();
        builder.buildBackground();
        builder.buildTextFieldTheme();
        builder.buildButtonTheme();
        builder.buildTime();
        return builder.getTheme();
    }

    public void setBuilder(ThemeBuilder builder) {
        this.builder = builder;
    }
}
