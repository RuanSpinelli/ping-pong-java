package com.pong.game;

import com.pong.entities.Ball;
import com.pong.entities.GameEntity;
import com.pong.entities.Paddle;
import com.pong.input.InputManager;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class GameLoop extends AnimationTimer {

    private List<GameEntity> entities = new ArrayList<>();


    private final InputManager input;
    private final Paddle leftPaddle;
    private final Paddle rightPaddle;
    private final Pane root;


    public GameLoop(Pane root, InputManager input) {

        // define detector de input
        this.input = input;
        this.root = root;

        // cria a barra da esquerda
        leftPaddle = new Paddle(30, 200);
        // Cria barra da direita
        rightPaddle = new Paddle(750, 200);
        // Cria bola
        Ball ball = new Ball(400, 300);

        // adiciona as entidades na lista de entidades
        entities.add(leftPaddle);
        entities.add(rightPaddle);
        entities.add(ball);

        // Adiciona as entidades na tela de uma a uma
        for (GameEntity entity : entities) {
            root.getChildren().add(entity.getView());
        }
    }

    @Override
    public void handle(long now) {
        // Detecta input cima
        if (input.isPressed(KeyCode.W)) {
            leftPaddle.moveUp();
        }

        // Detecta input baixo
        if (input.isPressed(KeyCode.S)) {
            leftPaddle.moveDown();
        }

        // Obtem a altura atual da janela aonde se passa o jogo
        double screenHeight = root.getHeight();

        keepPaddleInsideScreen(rightPaddle,screenHeight);
        keepPaddleInsideScreen(leftPaddle,screenHeight);

        // Atualiza o que vai ser apresentado na tela
        for (GameEntity entity : entities) {
            entity.update();
        }

    }

    public void updatePositions(double width, double height) {
        // Atualiza barras proporcionalmente
        leftPaddle.setX(width * 0.03);
        rightPaddle.setX(width * 0.95);

        // Mantém Y atual das barras
        leftPaddle.setY(leftPaddle.getY());
        rightPaddle.setY(rightPaddle.getY());

        // Bola (opcional, por enquanto não reposiciona)
        // ball.setX(width / 2);
        // ball.setY(height / 2);
    }


    private void keepPaddleInsideScreen(Paddle paddle, double screenHeight) {
        if (paddle.getY() < 0) {
            paddle.setY(0);
        }

        if (paddle.getY() + paddle.getHeight() > screenHeight) {
            paddle.setY(screenHeight- paddle.getHeight());
        }

    }
}
