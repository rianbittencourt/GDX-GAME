package com.rpggame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.Actor;


public class TouchPad {
    private Stage stage;
    private Player player;
    private Touchpad touchpad;

    public TouchPad() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        touchpad = new Touchpad(10, skin);
        touchpad.setBounds(15, 15, 200, 200);

        stage.addActor(touchpad);

        // Adicione um ouvinte para responder aos eventos de toque no touchpad
        touchpad.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float deltaX = touchpad.getKnobPercentX();
                float deltaY = touchpad.getKnobPercentY();

                if (!player.isMoving()) {
                    float targetX = player.getPosition().x;
                    float targetY = player.getPosition().y;

                    if (Math.abs(deltaX) > Math.abs(deltaY)) {
                        // Movimento horizontal
                        if (deltaX > 0) {
                            targetX += player.getTileSize();
                            player.setDirection(Player.Direction.RIGHT);
                        } else if (deltaX < 0) {
                            targetX -= player.getTileSize();
                            player.setDirection(Player.Direction.LEFT);
                        }
                    } else {
                        // Movimento vertical
                        if (deltaY > 0) {
                            targetY += player.getTileSize();
                            player.setDirection(Player.Direction.UP);
                        } else if (deltaY < 0) {
                            targetY -= player.getTileSize();
                            player.setDirection(Player.Direction.DOWN);
                        }
                    }

                    // Move o jogador para a nova posição com base no moveSpeed.
                    player.moveTo(targetX, targetY);
                }
            }
        });
    }

    public void render() {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
}
