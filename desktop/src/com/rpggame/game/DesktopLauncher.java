package com.rpggame.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

        // Define a largura e a altura da janela para 1280x720
        config.setWindowedMode(1280, 720);

        config.setForegroundFPS(61);
        config.setTitle("RPGGAME");
        
        new Lwjgl3Application(new MyGdxGame(), config);
    }
}
