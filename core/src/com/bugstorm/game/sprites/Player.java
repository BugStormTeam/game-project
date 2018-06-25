package com.bugstorm.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.bugstorm.game.GameProject;
import com.bugstorm.game.screens.FirstLevelScreen;


public class Player extends Sprite {

    public enum State { FALLING, JUMPING, STANDING, RUNNING };
    public World world;
    public Body b2body;
    private TextureRegion playerStand;
    private Texture texture;
    private FirstLevelScreen screen;

    public Player (FirstLevelScreen screen){
        this.screen = screen;
        this.world = screen.getWorld();
        this.texture = new Texture("badlogic.jpg");

        playerStand = new TextureRegion(texture, 0, 0, texture.getWidth(), texture.getHeight());

        PlayerDefinition();

        setBounds(0, 0, texture.getWidth() / GameProject.PPM, texture.getHeight() / GameProject.PPM);
        setRegion(playerStand);
    }

    private void PlayerDefinition(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set((texture.getWidth() / 2) / GameProject.PPM, (texture.getHeight() / 2) / GameProject.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(140 / GameProject.PPM);
        fixtureDef.shape = shape;
        b2body.createFixture(fixtureDef).setUserData(this);

    }

    public void update(float delta){
        setPosition(b2body.getPosition().x - getWidth() / 2 , b2body.getPosition().y - getHeight() / 2);
    }

    @Override
    public void draw(Batch batch){
        super.draw(batch);
    }

}
