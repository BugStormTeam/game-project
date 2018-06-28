package com.bugstorm.game.entities;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bugstorm.game.helpers.GameInfo;


public class Bullet {

    public static final int SPEED = 5;
    private static Texture texture;

    float x, y;
    public boolean remove = false;

    public Bullet (float x,float y) {
        this.x = x;
        this.y = y;

        if (texture == null)
            texture = new Texture("bullet.jpg");
    }

    public void update (float deltaTime,float playerPositionX) {
        x += SPEED * deltaTime;
        if (x > playerPositionX + GameInfo.V_WIDTH / 2 / GameInfo.PPM)
            remove = true;
    }

    public void render (SpriteBatch batch) {
        batch.draw(texture, x, y,0.14f,0.10f);
    }

}