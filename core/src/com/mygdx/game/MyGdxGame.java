package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class MyGdxGame extends ApplicationAdapter {
    private OrthographicCamera camera;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private SpriteBatch batch;
    private Player player;
    private InputHandler inputHandler;
    private TouchPad touchpad;
    
    
    
    

    @Override
    public void create() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        float zoom = 0.2f;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);

		camera.zoom = zoom;
      
        map = new TmxMapLoader().load("map.tmx"); // Substitua "meu_mapa.tmx" pelo nome do seu arquivo .tmx.
        renderer = new OrthogonalTiledMapRenderer(map);
        touchpad = new TouchPad();
        batch = new SpriteBatch();
        player = new Player();

        inputHandler = new InputHandler(player);
    }

    @Override
    public void render() {
    	float delta = Gdx.graphics.getDeltaTime();
        inputHandler.handleInput();
        // Remova qualquer chamada adicional de update do jogador que possa estar aqui.

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // Ajuste a posição da câmera para seguir o jogador.
        camera.position.set(player.getPosition().x + player.getTileSize() / 2,
                           player.getPosition().y + player.getTileSize() / 2, 0);
        camera.update();
        renderer.setView(camera);
        renderer.render();
        touchpad.render();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        player.render(batch);
        batch.end();

        // Certifique-se de que o método update do jogador seja chamado apenas uma vez por quadro.
        player.update(delta);

        // Atualize o jogador.
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        batch.dispose();
        player.dispose();
    }
}
