package com.pong.entities;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Ball extends GameEntity {

    private double dx = 3;
    private double dy = 3;

    public Ball(double x, double y) {
        view = new Rectangle(15, 15);
        view.setFill(Color.WHITE);
        view.setTranslateX(x);
        view.setTranslateY(y);
    }

    @Override
    public void update() {
        view.setTranslateX(view.getTranslateX() + dx);
        view.setTranslateY(view.getTranslateY() + dy);
    }
}
