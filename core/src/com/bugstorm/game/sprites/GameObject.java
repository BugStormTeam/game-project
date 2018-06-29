package com.bugstorm.game.sprites;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.bugstorm.game.GameProject;
import com.bugstorm.game.helpers.GameInfo;
import com.bugstorm.game.screens.FirstLevelScreen;

public abstract class GameObject extends Sprite {

    protected FirstLevelScreen screen;
    protected World world;
    protected boolean isDestroyed;
    protected boolean isDestroyable;
    protected Body body;

    public GameObject(FirstLevelScreen screen, float x, float y){

        this.screen = screen;
        this.world = screen.getWorld();
        isDestroyable = false;

        setPosition(x, y);
        setBounds(getX(), getY(), 150 / GameInfo.PPM, 300 / GameInfo.PPM);

        defineObject();
    }

    public abstract void defineObject();
    public abstract void use(Player Player);


    public void update(float delta){
        if(isDestroyable && !isDestroyed){
            world.destroyBody(body);
            isDestroyed = true;
        }
    }

    public void draw(Batch batch){
        if(!isDestroyed)
            super.draw(batch);
    }

    public void destroy(){
        isDestroyable = true;
    }

}
