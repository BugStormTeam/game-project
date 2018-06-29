package com.bugstorm.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.bugstorm.game.GameProject;
import com.bugstorm.game.helpers.GameInfo;
import com.bugstorm.game.screens.FirstLevelScreen;


public class Player extends Sprite {

    public enum State { STANDING, RUNNING };
    private World world;
    public Body b2body;
    private TextureRegion playerStand;
    private Texture texture;
    private FirstLevelScreen screen;
    private static final float pixelsPerMeter = GameInfo.PPM;
    private int textureWidth;
    private int textureHeight;
    private State currentState;
    private State previousState;
    private Animation<TextureRegion> walkingAnimation;
    private TextureAtlas atlas;
    private Float stateTimer;
    private boolean runningRight;

    public Player (FirstLevelScreen screen){
        this.screen = screen;
        this.world = screen.getWorld();
        this.texture = new Texture("walk_005.png");
        this.textureWidth = texture.getWidth();
        this.textureHeight = texture.getHeight();
        this.currentState = State.STANDING;
        this.previousState = State.STANDING;
        this.stateTimer = 0f;
        this.runningRight = true;

        playerStand = new TextureRegion(texture, 0, 0, textureWidth , textureHeight);
        atlas = new TextureAtlas(Gdx.files.internal("sheets/walk.atlas"));
        walkingAnimation = new Animation<TextureRegion>(0.016f, atlas.findRegions("walk"));

        PlayerDefinition();

        setBounds(0, 0, textureWidth / pixelsPerMeter, textureHeight / pixelsPerMeter);
        setRegion(playerStand);
    }

    private void PlayerDefinition(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set((textureWidth / 2) / pixelsPerMeter, (textureWidth / 2) / pixelsPerMeter);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(140 / pixelsPerMeter);
        fixtureDef.filter.categoryBits = GameProject.PLAYER_BIT;
        fixtureDef.filter.maskBits = GameProject.GROUND_BIT;
        fixtureDef.shape = shape;
        b2body.createFixture(fixtureDef).setUserData(this);


    }

    public void update(float delta){
        setPosition(b2body.getPosition().x - getWidth() / 2 , b2body.getPosition().y - getHeight() / 2);

        setRegion(getFrame(delta));
    }

    @Override
    public void draw(Batch batch){
        super.draw(batch);
    }


    public State getState(){
        if (b2body.getLinearVelocity().x != 0){
            return State.RUNNING;
        } else {
            return State.STANDING;
        }
    }

    public TextureRegion getFrame(float delta){
        currentState = getState();

        TextureRegion region;

        switch (currentState){
            case RUNNING:
                region = walkingAnimation.getKeyFrame(stateTimer, true);
                break;
            case STANDING:
            default:
                region = playerStand;
                break;
        }

        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()){
            region.flip(true, false);
            runningRight = false;
        } else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()){
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + delta : 0f;

        previousState = currentState;

        return region;
    }

    public boolean getrunningRight(){
        return this.runningRight;
    }
}
