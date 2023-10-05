package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

public class PlayerMovement {
    private Player player;

    public PlayerMovement(Player player) {
        this.player = player;
    }

    public void moveTo(float targetX, float targetY) {
        player.setTargetPosition(targetX, targetY);
        player.setMoving(true);
    }
    

    public void update(float delta) {
        if (player.isMoving()) {
            Vector2 direction = player.getTargetPosition().cpy().sub(player.getPosition());
            float distance = direction.len();

            if (distance <= 2 * player.getMoveSpeed() * delta) {
                player.getPosition().set(player.getTargetPosition());
                player.setMoving(false);
                player.setFrameDuration(0f);
                snapToTileCenter();
            } else {
                direction.nor().scl(player.getMoveSpeed());
                player.getPosition().add(direction.scl(delta));
                player.setFrameDuration(0.1f);
            }

            // Atualize a animação com base na direção do movimento.
            player.setAnimationTime(player.getAnimationTime() + delta);
            int row = 0; // A linha da spritesheet é a mesma para todas as direções.

            switch (player.getCurrentDirection()) {
                case UP:
                    row = 0;
                    break;
                case DOWN:
                    row = 2;
                    break;
                case LEFT:
                    row = 1;
                    break;
                case RIGHT:
                    row = 3;
                    break;
            }

            // Continuar a reprodução da animação de andar.
            int column = (int) (player.getAnimationTime() / player.getFrameDuration()) % 9;
            if (!player.isMoving()) {
                column = 0; // Define a coluna como 0 quando a animação não está em movimento.
            }
            player.setCurrentCharacterFrame(player.getCharacterFrames()[row][column]);
        }
    }

    private void snapToTileCenter() {
        int centerX = (int) (player.getTargetPosition().x / player.getTileSize()) * player.getTileSize() + 0;
        int centerY = (int) (player.getTargetPosition().y / player.getTileSize()) * player.getTileSize() + 10;

        switch (player.getCurrentDirection()) {
            case UP:
                centerX += 0;
                break;
            case DOWN:
                centerX += 0;
                break;
            case LEFT:
                centerX += 0;
                break;
            case RIGHT:
                centerX += 0;
                break;
        }

        player.getPosition().set(centerX, centerY);
    }
}
