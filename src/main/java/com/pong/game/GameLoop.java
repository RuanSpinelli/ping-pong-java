package com.pong.game;

import javafx.animation.AnimationTimer;

public class GameLoop extends AnimationTimer {

    @Override
    public void handle(long now) {
        update();
    }

    private void update() {
        // por enquanto vazio
        // depois: entidades, colisões, etc
    }
}
