package com.krnchik.model.theme;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Domestic extends ThemeBuilder {

    @Override
    void buildImage() {
        theme.setImage(new ImageIcon("domestic.png"));
    }

    @Override
    void buildSound() {
        theme.setSound(new File("domestic.wav"));
    }

    @Override
    void buildBackground() {
        theme.setBackground(Color.DARK_GRAY);
    }

    @Override
    void buildButtonTheme() {
        theme.setBackgroundButton(Color.BLACK);
        theme.setForegroundButton(Color.WHITE);
        theme.setFontButton(new Font("MV Boli", Font.ITALIC, 10));
    }

    @Override
    void buildTextFieldTheme() {
        theme.setBackgroundTextField(Color.BLACK);
        theme.setForegroundTextField(Color.GREEN);
        theme.setFontTextField(new Font("ROMAN", Font.PLAIN, 10));
    }

    @Override
    void buildTime() {
        theme.setColorTime(Color.WHITE);
        theme.setTime(new Point(100, 100));
        theme.setFontTime(new Font("ROMAN", Font.PLAIN, 30));
    }
}
