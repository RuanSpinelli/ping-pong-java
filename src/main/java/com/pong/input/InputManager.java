package com.pong.input;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.util.HashSet;
import java.util.Set;

public class InputManager {

    private final Set<KeyCode> pressdkeys = new HashSet<>();

    public InputManager(Scene scene){

        scene.setOnKeyPressed(event ->
                pressdkeys.add(event.getCode())
        );
        scene.setOnKeyReleased(event ->
                pressdkeys.remove(event.getCode())
        );
    }

    public boolean isPressed(KeyCode key) {
        return pressdkeys.contains(key);
    }
}
