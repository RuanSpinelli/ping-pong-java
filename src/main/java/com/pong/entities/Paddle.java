package com.pong.entities;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Paddle extends GameEntity {

    private double speed = 10;

    public Paddle(double x, double y) {
        view = new Rectangle(20, 100);
        view.setFill(Color.WHITE);
        view.setTranslateX(x);
        view.setTranslateY(y);
    }

    @Override
    public void update() {
        // Por enquanto não faz nada
        // Depois vamos ligar isso ao teclado
    }

    public void moveUp() {
        view.setTranslateY(view.getTranslateY() - speed);
    }

    public void moveDown() {
        view.setTranslateY(view.getTranslateY() + speed);
    }
}
