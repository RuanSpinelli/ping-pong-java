package com.pong.config;

import javafx.scene.paint.Color;

public final class GameConfig {

    // Valores padrão iniciais
    public static double WIDTH = 800;
    public static double HEIGHT = 600;

    public static final Color BACKGROUND_COLOR = Color.BLACK;

    private GameConfig() {
        // impede instância
    }

    /**
     * Atualiza tamanho da tela dinamicamente
     */
    public static void setResolution(double width, double height) {
        WIDTH = width;
        HEIGHT = height;
    }
}
