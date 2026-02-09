package com.pong.game;

import com.pong.entities.Ball;
import com.pong.entities.GameEntity;
import com.pong.entities.Paddle;
import com.pong.input.InputManager;
import javafx.animation.AnimationTimer;
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
    private double aiErrorStart = 40;      // Erro inicial (IA burra)
    private double aiErrorMin = 5;         // Erro mínimo (IA mais precisa)
    private double aiError;

    private long aiReactionStart = 500_000_000; // 0.5s inicial
    private long aiReactionMin = 80_000_000;   // 0.8s mínima
    private long aiReactionDelay;

    private double aiTolerance = 10;      // Tolerância para não ficar tremendo
    private double aiSpeed = 15;          // Velocidade fixa da IA

    private long lastAiDecision = 0;

    public GameLoop(Pane root, InputManager input) {
        this.input = input;
        this.root = root;

        leftPaddle = new Paddle(30, 200);
        rightPaddle = new Paddle(750, 200);
        ball = new Ball(400, 300);

        entities.add(leftPaddle);
        entities.add(rightPaddle);
        entities.add(ball);

        for (GameEntity entity : entities) {
            root.getChildren().add(entity.getView());
        }

        // Inicializa IA com valores iniciais
        aiError = aiErrorStart;
        aiReactionDelay = aiReactionStart;
    }

    @Override
    public void handle(long now) {

        double ballCenterY = ball.getY() + ball.getHeight() / 2;
        double paddleCenterY = rightPaddle.getY() + rightPaddle.getHeight() / 2;

        if (gameStartTime == 0) {
            gameStartTime = now;
        }

        double elapsedSeconds = (now - gameStartTime) / 1_000_000_000.0;

        // IA melhora ao longo do tempo
        aiError = Math.max(aiErrorMin, aiErrorStart - elapsedSeconds * 1.2);
        aiReactionDelay = Math.max(aiReactionMin, aiReactionStart - (long)(elapsedSeconds * 10_000_000));

        // Movimento da IA apenas quando a bola vai para a direita
        if (ball.getDx() > 0) {

            if (now - lastAiDecision > aiReactionDelay) {

                // Alvo com erro aleatório
                double targetY = ballCenterY + (Math.random() * aiError - aiError / 2);

                // Diferença entre centro da barra e alvo
                double diff = targetY - paddleCenterY;

                // Move a barra com velocidade fixa de 15
                if (Math.abs(diff) > aiTolerance) {
                    if (diff > 0) {
                        // move para baixo
                        rightPaddle.setY(rightPaddle.getY() + Math.min(aiSpeed, diff));
                    } else {
                        // move para cima
                        rightPaddle.setY(rightPaddle.getY() - Math.min(aiSpeed, -diff));
                    }
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

        // Controle do jogador
        if (input.isPressed(KeyCode.W)) leftPaddle.moveUp();
        if (input.isPressed(KeyCode.S)) leftPaddle.moveDown();

        double screenHeight = root.getHeight();
        double screenWidth = root.getWidth();

        keepPaddleInsideScreen(rightPaddle, screenHeight);
        keepPaddleInsideScreen(leftPaddle, screenHeight);

        // Colisão com paredes
        if (ball.getY() <= 0 || ball.getY() + ball.getHeight() >= screenHeight)
            ball.bounceVertical();

        if (ball.getX() <= 0 || ball.getX() + ball.getWidth() >= screenWidth)
            ball.bounceHorizontal();

        checkBallPaddleCollision();

        // Atualiza entidades
        for (GameEntity entity : entities) {
            entity.update();
        }
    }

    public void updatePositions(double width, double height) {
        leftPaddle.setX(width * 0.03);
        rightPaddle.setX(width * 0.95);

        leftPaddle.setY(leftPaddle.getY());
        rightPaddle.setY(rightPaddle.getY());
    }

    private void checkBallPaddleCollision() {
        if (ball.getView().getBoundsInParent().intersects(leftPaddle.getView().getBoundsInParent()))
            ball.bounceHorizontal();
        if (ball.getView().getBoundsInParent().intersects(rightPaddle.getView().getBoundsInParent()))
            ball.bounceHorizontal();
    }

    private void keepPaddleInsideScreen(Paddle paddle, double screenHeight) {
        if (paddle.getY() < 0) paddle.setY(0);
        if (paddle.getY() + paddle.getHeight() > screenHeight)
            paddle.setY(screenHeight - paddle.getHeight());
    }
}
