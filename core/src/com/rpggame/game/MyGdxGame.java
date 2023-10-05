package com.rpggame.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.Application;

public class MyGdxGame extends ApplicationAdapter {
    private OrthographicCamera camera;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private SpriteBatch batch;
    private Player player;
    private InputHandler inputHandler;
    private TouchPad touchpad;
    int fps;
    BitmapFont font;
    SpriteBatch spriteBatch;
    
    
    
    
    

    @Override
    public void create() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        float zoom;
        
        

        
        if (Gdx.app.getType() == Application.ApplicationType.Desktop) {
            zoom = 0.4f;
        } else {
            zoom = 0.2f;
        }

        camera = new OrthographicCamera();
        camera.setToOrtho(false, w, h);

		camera.zoom = zoom;
      
        map = new TmxMapLoader().load("map.tmx"); // Substitua "meu_mapa.tmx" pelo nome do seu arquivo .tmx.
        renderer = new OrthogonalTiledMapRenderer(map);
        font = new BitmapFont();
        batch = new SpriteBatch();
        player = new Player();
        spriteBatch = new SpriteBatch();
       
        inputHandler = new InputHandler(player);
    }

    @Override
    public void render() {
   
    	 float delta = Gdx.graphics.getDeltaTime();
    	 fps = Gdx.graphics.getFramesPerSecond();
    	    inputHandler.handleInput();
    	    
    	    Gdx.gl.glClearColor(0, 0, 0, 1);
    	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    	    // Ajuste a posição da câmera para seguir o jogador.
    	    camera.position.set(player.getPosition().x + player.getTileSize() / 2,
    	                       player.getPosition().y + player.getTileSize() / 2, 0);
    	    camera.update();
    	    renderer.setView(camera);
    	    renderer.render();

    	    // Inicie o batch antes de desenhar
    	    batch.setProjectionMatrix(camera.combined);
    	    batch.begin();

    	    // Desenhe o jogador
    	    player.render(batch);

    	    // Desenhe o texto dentro do batch
    	    font.draw(batch, "FPS: " + fps,  100, 200);

    	    // Encerre o batch após desenhar
    	    batch.end();

    	    // Certifique-se de que o método update do jogador seja chamado apenas uma vez por quadro.
    	    player.update(delta);
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        batch.dispose();
        player.dispose();
        font.dispose();
    }
}
