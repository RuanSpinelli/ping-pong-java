package com.pong;

import com.pong.config.GameConfig;
import com.pong.game.GameLoop;
import com.pong.input.InputManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Pane root = new Pane();
        root.setStyle("-fx-background-color: black;");

        Scene scene = new Scene(root, GameConfig.WIDTH, GameConfig.HEIGHT);

        // Gerenciador de inputs
        InputManager input = new InputManager(scene);

        stage.setTitle("Ping Pong com Java");
        stage.setScene(scene);
        stage.show();


        GameLoop gameLoop = new GameLoop(root, input);
        gameLoop.start(); // AQUI O JOGO COMEÇA

        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            GameConfig.setResolution(stage.getWidth(), stage.getHeight());
            gameLoop.updatePositions(GameConfig.WIDTH, GameConfig.HEIGHT);
        });

        stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            GameConfig.setResolution(stage.getWidth(), stage.getHeight());
            gameLoop.updatePositions(GameConfig.WIDTH, GameConfig.HEIGHT);
        });

    }

    public static void main(String[] args) {
        launch(args);
    }
}
