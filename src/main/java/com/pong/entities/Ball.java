package com.pong.entities;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Ball extends GameEntity {

    Random aleatorio = new Random();

    private double dx = aleatorio.nextBoolean() ? 3 : -3;
    private double dy = aleatorio.nextBoolean() ? 3 : -3;

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

// Métodos para cuidar da física
    public void bounceVertical() {
        dy = -dy;
    }
    public void bounceHorizontal() {
        dx = -dx;
    }
// Getters úteis

    public double getWidth() {
        return ((Rectangle) view).getWidth();
    }

    public double getHeight() {
        return ((Rectangle) view).getHeight();
    }

    public double getX() {
        return view.getTranslateX();
    }

    public double getY() {
        return view.getTranslateY();
    }
}
