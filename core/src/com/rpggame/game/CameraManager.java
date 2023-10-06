package com.rpggame.game;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class CameraManager {
    private OrthographicCamera camera;
    private Player player;

    public CameraManager(OrthographicCamera camera, Player player) {
        this.camera = camera;
        this.player = player;
    }

    public void update(float delta) {
        // Ajuste a posição da câmera para seguir o jogador.
        camera.position.set(
            player.getPosition().x + player.getTileSize() / 2,
            player.getPosition().y + player.getTileSize() / 2,
            0
        );
        camera.update();
    }
}
