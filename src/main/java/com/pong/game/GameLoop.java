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
    private final Ball ball;
    private long lastSpeedIncrease = 0;
    private static final long SPEED_INTERVAL = 15_000_000_000L; // 15s
    // Tempo
    private long gameStartTime = 0;
    // IA
    private double aiError = 40;        // começa bem burra
    private double aiMinError = 5;      // limite mínimo
    private double aiSpeedMultiplier = 0.7;
    private double aiMaxSpeedMultiplier = 2.0;
    private long lastAiDecision = 0;
    private double aiTolerance = 10;

    private long aiReactionDelay = 350_000_000; // 0.35s
    private long aiMinReactionDelay = 120_000_000; // 0.12s


    public GameLoop(Pane root, InputManager input) {

        // define detector de input
        this.input = input;
        this.root = root;

        // cria a barra da esquerda
        leftPaddle = new Paddle(30, 200);
        // Cria barra da direita
        rightPaddle = new Paddle(750, 200);
        // Cria bola
        ball = new Ball(400, 300);

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

        // Obtem o centro da bola
        double ballCenterY = ball.getY() + ball.getHeight() / 2;
        double paddleCenterY = rightPaddle.getY() + rightPaddle.getHeight() / 2;

        if (gameStartTime == 0) {
            gameStartTime = now;
        }
        double elapsedSeconds = (now - gameStartTime) / 1_000_000_000.0;

        // A cada segundo a IA melhora um pouquinho
        aiError = Math.max(aiMinError, 40 - elapsedSeconds * 1.2);
        aiSpeedMultiplier = Math.min(aiMaxSpeedMultiplier, 0.45 + elapsedSeconds * 0.025);
        aiReactionDelay = (long) Math.max(
                aiMinReactionDelay,
                350_000_000 - elapsedSeconds * 18_000_000
        );


        // A IA só reage no momento em que a bola se move para a direita
        if (ball.getDx() > 0) {

            if (now - lastAiDecision > aiReactionDelay) {

                double targetY = ballCenterY + (Math.random() * aiError - aiError / 2);

                if (targetY > paddleCenterY + aiTolerance) {
                    rightPaddle.moveDown(aiSpeedMultiplier);
                }
                else if (targetY < paddleCenterY - aiTolerance) {
                    rightPaddle.moveUp(aiSpeedMultiplier);
                }

                lastAiDecision = now;
            }
        }




        // Manipula a velocidade da bola
        if (lastSpeedIncrease == 0) {
            lastSpeedIncrease = now;
        }
        if (now - lastSpeedIncrease >= SPEED_INTERVAL) {
            ball.increaseSpeed(1);
            lastSpeedIncrease = now;
        }

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
        double screenWidth = root.getWidth();

        keepPaddleInsideScreen(rightPaddle,screenHeight);
        keepPaddleInsideScreen(leftPaddle,screenHeight);

        // Bateu em cima ou embaixo
        if (ball.getY() <= 0 || ball.getY() + ball.getHeight() >= screenHeight) {
            ball.bounceVertical();
        }

        // Bateu na esquerda ou direita
        if (ball.getX() <= 0 || ball.getX() + ball.getWidth() >= screenWidth) {
            ball.bounceHorizontal();
        }

        checkBallPaddleCollision();

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

    private void checkBallPaddleCollision() {

        if (ball.getView().getBoundsInParent()
                .intersects(leftPaddle.getView().getBoundsInParent())) {
            ball.bounceHorizontal();
        }

        if (ball.getView().getBoundsInParent()
                .intersects(rightPaddle.getView().getBoundsInParent())) {
            ball.bounceHorizontal();
        }
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
