package com.rpggame.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {
    private TextureRegion[][] characterFrames;
    private TextureRegion currentCharacterFrame;
    private Vector2 position;
    private Vector2 targetPosition;
    private Rectangle bounds;
    private int tileSize; 
    private float moveSpeed = 50f;
    private boolean isMoving;
    
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private Direction currentDirection = Direction.RIGHT;
    private float animationTime = 0f;
    private float frameDuration = 0.1f;

    private PlayerMovement movement;
    public Vector2 getTargetPosition() {
        return targetPosition;
    }

    public float getMoveSpeed() {
        return moveSpeed;
    }

    public void setFrameDuration(float duration) {
        frameDuration = duration;
    }

    public float getAnimationTime() {
        return animationTime;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public float getFrameDuration() {
        return frameDuration;
    }

    public TextureRegion[][] getCharacterFrames() {
        return characterFrames;
    }
    
    public void setAnimationTime(float animationTime) {
        this.animationTime = animationTime;
    }

    public void setCurrentCharacterFrame(TextureRegion frame) {
        currentCharacterFrame = frame;
    }

    public Player() {
        isMoving = false;
        Texture characterSheet = new Texture("Lizard.png");
        int rows = 4;
        int columns = 9;
        TiledMap tiledMap = new TmxMapLoader().load("map.tmx");
        TiledMapTileSets tileSets = tiledMap.getTileSets();
        TiledMapTileSet tileSet = tileSets.getTileSet(0);

        MapProperties tileSetProperties = tileSet.getProperties();
        tileSize = tileSetProperties.get("tilewidth", Integer.class);

        TextureRegion[][] tmp = TextureRegion.split(characterSheet, characterSheet.getWidth() / columns, characterSheet.getHeight() / rows);
        characterFrames = new TextureRegion[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                characterFrames[i][j] = tmp[i][j];
            }
        }

        position = new Vector2(300, 150);
        bounds = new Rectangle(position.x, position.y, 32, 32);
        currentCharacterFrame = characterFrames[0][2];

        movement = new PlayerMovement(this);
    }
    
    public int getTileSize() {
        return tileSize;
    }
    
    public void setTargetPosition(float targetX, float targetY) {
        targetPosition = new Vector2(targetX, targetY);
    }
    
    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public boolean isMoving() {
        return isMoving;
    }

    
    
    public void moveTo(float targetX, float targetY) {
        movement.moveTo(targetX, targetY);
    }


    public void render(SpriteBatch batch) {
        batch.draw(currentCharacterFrame, position.x, position.y, 32, 32);
        // O terceiro e o quarto argumento (32, 32) são as dimensões do jogador (largura e altura).
    }
   

    public void update(float delta) {
        movement.update(delta);
    }
  
    	

    public Rectangle getBounds() {
        return bounds;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void dispose() {
        // Dispose da textura, se necessário.
    }

  
    public void setDirection(Direction direction) {
        currentDirection = direction;
    }

	
}