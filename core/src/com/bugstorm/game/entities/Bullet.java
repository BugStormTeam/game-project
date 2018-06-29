package com.bugstorm.game.entities;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bugstorm.game.helpers.GameInfo;


public class Bullet {

    public static final int SPEED = 7;
    private static final Texture bulletTextureRight = new Texture("bullet.png");
    private static final Texture bulletTextureLeft = new Texture("bulletLeft.png");

    private final boolean gameDirectionRight;
    float x, y;
    public boolean remove = false;

    public Bullet (float x,float y, boolean gameDirectionRight) {
        this.x = x;
        this.y = y;
        this.gameDirectionRight = gameDirectionRight;

    }

    public void update (float deltaTime,float playerPositionX) {
        if (gameDirectionRight == true) {
            x += SPEED * deltaTime;
            if (x > playerPositionX + GameInfo.V_WIDTH / 2 / GameInfo.PPM + 1.f)
                remove = true;
        }
        else {
            x -= SPEED * deltaTime;
            if (x > playerPositionX + GameInfo.V_WIDTH / 2 / GameInfo.PPM + 1.f)
                remove = true;
        }
    }

    public void render (SpriteBatch batch) {
        if (gameDirectionRight) {
            batch.draw(bulletTextureRight, x, y, 0.14f, 0.10f);
        } else {
            batch.draw(bulletTextureLeft, x, y,0.14f,0.10f);
        }
    }

}