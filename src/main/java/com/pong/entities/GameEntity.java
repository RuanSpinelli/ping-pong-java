package com.pong.entities;

import javafx.scene.shape.Rectangle;

public abstract class GameEntity {

    protected Rectangle view;

    public Rectangle getView() {
        return view;
    }

    public abstract void update();
}
