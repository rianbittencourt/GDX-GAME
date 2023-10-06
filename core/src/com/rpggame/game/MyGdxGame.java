package com.rpggame.game;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
    private int fps;
    private BitmapFont font;
    private SpriteBatch spriteBatch;
    private CameraManager cameraManager;
    private Monster monster;
    
    

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

        map = new TmxMapLoader().load("map.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        font = new BitmapFont();
        batch = new SpriteBatch();
        player = new Player();
        spriteBatch = new SpriteBatch();
        monster = new Monster();

        spriteBatch = new SpriteBatch();
        inputHandler = new InputHandler(player);

        // Inicialize o CameraManager
        cameraManager = new CameraManager(camera, player);
    }

    @Override
    public void render() {
    	 float delta = Gdx.graphics.getDeltaTime();
    	    fps = Gdx.graphics.getFramesPerSecond();
    	    inputHandler.handleInput();

    	    // Atualize a câmera usando o CameraManager
    	    cameraManager.update(delta);

    	    renderer.setView(camera);
    	    
    	    // Limpe o buffer de tela apenas uma vez fora do batch
    	    Gdx.gl.glClearColor(0, 0, 0, 1);
    	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); 
    	    player.update(delta);
    	    
            monster.update(delta, player);

    	    batch.setProjectionMatrix(camera.combined);
    	    
    	    batch.begin();
    	    // Renderize o mapa e o jogador
    	    renderer.render();
    	    player.render(batch);
    	    monster.render(batch);
    	    batch.end();

    	    // Renderize o texto fora do batch de mapa
    	    spriteBatch.begin();
    	    
    	    // Defina as coordenadas x e y para o canto superior direito da tela
    

    	    // Desenhe o texto no canto superior direito
    	    font.draw(spriteBatch, "FPS: " + fps, 20, 20);

    	    spriteBatch.end();

    	
    	  
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
