package com.bugstorm.game.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public class Bullet implements Pool.Poolable {

    public Vector2 getPosition() {
        return position;
    }

    private Vector2 position;

    public boolean isAlive() {
        return alive;
    }

    private boolean alive;


    public Bullet() {
        this.position = new Vector2();
        this.alive = false;
    }


    public void init(float posX, float posY) {
        position.set(posX,  posY);
        alive = true;
    }


    @Override
    public void reset() {
        position.set(0,0);
        alive = false;
    }


    public void update (float delta) {

        // update bullet position
        position.add(1*delta*60, 1*delta*60);

        // if bullet is out of screen, set it to dead

    }
}