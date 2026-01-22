package com.pong;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Ping Pong");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
