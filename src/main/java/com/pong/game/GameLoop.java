package com.pong.game;

import javafx.animation.AnimationTimer;

public class GameLoop extends AnimationTimer {

    @Override
    public void handle(long l) {

    }
    private int counter = 0;

    private void update() {
        counter++;
        System.out.println("Frame: " + counter);
    }

    private void render() {
        // desenhar coisas na tela (por enquanto vazio)
    }



}
