package com.pong;

import com.pong.config.GameConfig;
import com.pong.game.GameLoop;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Pane root = new Pane();
        root.setStyle("-fx-background-color: black;");

        Scene scene = new Scene(
                root,
                GameConfig.WIDTH,
                GameConfig.HEIGHT
        );

        stage.setTitle("Ping Pong com Java");
        stage.setScene(scene);
        stage.show();

        GameLoop gameLoop = new GameLoop();
        gameLoop.start(); // AQUI O JOGO COMEÇA
    }

    public static void main(String[] args) {
        launch(args);
    }
}
