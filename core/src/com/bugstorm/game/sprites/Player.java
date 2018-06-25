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
    private static final float pixelsPerMeter = GameProject.PPM;
    private int textureWidth;
    private int textureHeight;

    public Player (FirstLevelScreen screen){
        this.screen = screen;
        this.world = screen.getWorld();
        this.texture = new Texture("badlogic.jpg");
        this.textureWidth = texture.getWidth();
        this.textureHeight = texture.getHeight();

        playerStand = new TextureRegion(texture, 0, 0, textureWidth, textureHeight);

        PlayerDefinition();

        setBounds(0, 0, textureWidth / pixelsPerMeter, textureHeight / pixelsPerMeter);
        setRegion(playerStand);
    }

    private void PlayerDefinition(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set((textureWidth / 2) / pixelsPerMeter, (textureHeight / 2) / pixelsPerMeter);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(140 / pixelsPerMeter);
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
