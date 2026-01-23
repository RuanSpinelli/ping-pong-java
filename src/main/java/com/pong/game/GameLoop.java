package com.pong.game;

import com.pong.entities.Ball;
import com.pong.entities.GameEntity;
import com.pong.entities.Paddle;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class GameLoop extends AnimationTimer {

    private List<GameEntity> entities = new ArrayList<>();

    public GameLoop(Pane root) {

        Paddle leftPaddle = new Paddle(30, 200);
        Paddle rightPaddle = new Paddle(750, 200);
        Ball ball = new Ball(400, 300);

        entities.add(leftPaddle);
        entities.add(rightPaddle);
        entities.add(ball);

        for (GameEntity entity : entities) {
            root.getChildren().add(entity.getView());
        }
    }

    @Override
    public void handle(long now) {
        for (GameEntity entity : entities) {
            entity.update();
        }
    }
}
