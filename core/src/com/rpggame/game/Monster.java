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

import java.util.Random;

public class Monster {
    private TextureRegion[][] characterFrames;
    private TextureRegion currentCharacterFrame;
    private Vector2 position;
    private Rectangle bounds;
    private boolean isMoving;
    private Vector2 targetPosition;
    private float movementDelay = 3f; // Delay entre movimentos aleatórios (3 segundos)
    private float timeSinceLastMovement = 0f;
    private Random random;
    private TiledMap tiledMap;
    private int tileSize; // Adicionado tileSize aqui

    public Monster() {
        isMoving = false;
        Texture characterSheet = new Texture("Lizard.png"); // Substitua pelo caminho da textura do monstro.
        int rows = 4;
        int columns = 9;

        tiledMap = new TmxMapLoader().load("map.tmx");
        TiledMapTileSets tileSets = tiledMap.getTileSets();
        TiledMapTileSet tileSet = tileSets.getTileSet(0);

        MapProperties tileSetProperties = tileSet.getProperties();
        tileSize = tileSetProperties.get("tilewidth", Integer.class); // Obtém tileSize aqui

        TextureRegion[][] tmp = TextureRegion.split(characterSheet, characterSheet.getWidth() / columns, characterSheet.getHeight() / rows);
        characterFrames = new TextureRegion[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                characterFrames[i][j] = tmp[i][j];
            }
        }

        random = new Random();
        position = new Vector2(200, 250); // Posição inicial do monstro.
        targetPosition = new Vector2(position); // Inicialize targetPosition com a posição inicial
        bounds = new Rectangle(position.x, position.y, 32, 32); // Tamanho do monstro.

        currentCharacterFrame = characterFrames[0][0]; // Frame inicial do monstro.
    }

    public void render(SpriteBatch batch) {
        batch.draw(currentCharacterFrame, position.x, position.y, 32, 32);
        // O terceiro e o quarto argumento (32, 32) são as dimensões do monstro (largura e altura).
    }

    public void update(float delta, Player player) {
        timeSinceLastMovement += delta;

        if (timeSinceLastMovement >= movementDelay) {
            // Escolha uma nova posição de destino aleatória dentro de um intervalo
            float minX = 100;
            float maxX = 400;
            float minY = 100;
            float maxY = 400;

            float randomX = random.nextFloat() * (maxX - minX) + minX;
            float randomY = random.nextFloat() * (maxY - minY) + minY;

            setTargetPosition(randomX, randomY);

            timeSinceLastMovement = 0f;
        }

        // Implemente a lógica de movimento em direção à posição de destino.
        moveTowardsTarget(delta);
    }

    private void moveTowardsTarget(float delta) {
        float speed = 50f; // Velocidade de movimento do monstro

        Vector2 direction = targetPosition.cpy().sub(position);
        float distance = direction.len();

        if (distance <= speed * delta) {
            position.set(targetPosition);
        } else {
            direction.nor();

            float nextX = position.x + direction.x * speed * delta;
            float nextY = position.y + direction.y * speed * delta;

            // Calcule a posição do centro do tile mais próximo
            int tileX = Math.round(nextX / tileSize);
            int tileY = Math.round(nextY / tileSize);
            float tileCenterX = tileX * tileSize + tileSize / 2;
            float tileCenterY = tileY * tileSize + tileSize / 2;

            position.set(tileCenterX - 16, tileCenterY - 5); // Subtraia metade do tamanho do monstro para centralizá-lo
        }

        // Atualize a animação do monstro com base na direção do movimento (você pode adicionar isso aqui)
        // ...

        // Atualize a posição dos bounds (caixa de colisão) do monstro
        bounds.setPosition(position.x, position.y);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setTargetPosition(float x, float y) {
        targetPosition.set(x, y); // Defina a posição de destino usando set()
    }

    public void dispose() {
        // Dispose da textura, se necessário.
    }
}
