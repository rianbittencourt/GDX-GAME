package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class InputHandler {
    private Player player;

    public InputHandler(Player player) {
        this.player = player;
    }

    public void handleInput() {
        // Verifique se o jogador não está em movimento antes de aceitar um novo input.
        if (!player.isMoving()) {
            float targetX = player.getPosition().x;
            float targetY = player.getPosition().y;

            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                targetY += player.getTileSize();
                player.setDirection(Player.Direction.UP); // Altera a direção para cima.
            } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                targetY -= player.getTileSize();
                player.setDirection(Player.Direction.DOWN); // Altera a direção para baixo.
            } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                targetX -= player.getTileSize();
                player.setDirection(Player.Direction.LEFT); // Altera a direção para a esquerda.
            } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                targetX += player.getTileSize();
                player.setDirection(Player.Direction.RIGHT); // Altera a direção para a direita.
            }

            // Move o jogador para a nova posição com base no moveSpeed.
            player.moveTo(targetX, targetY);
        }
    }
}