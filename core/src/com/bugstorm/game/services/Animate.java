/*
*
* // This service is inactive for now
*
* */
package com.bugstorm.game.services;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bugstorm.game.helpers.GameInfo;

public class Animate extends Sprite{
    public static enum State {
        IDLE, WALK
    }

    private float stateTime;
    private TextureAtlas atlas;
    private State currentState;
    private boolean loop;
    public Animation<TextureRegion> runningAnimation;
    private static final float pixelsPerMeter = GameInfo.PPM;


    public Animate(String path, State state, boolean loop) {
        super();

        this.currentState = state;
        this.loop = loop;
        stateTime = 0;

        atlas = new TextureAtlas(Gdx.files.internal(path));
        runningAnimation =
                new Animation<TextureRegion>(0.016f, atlas.findRegions("walk"), Animation.PlayMode.LOOP);
    }

    public void  DrawAnimate(SpriteBatch batch, float dt) {
        TextureRegion currentFrame = runningAnimation.getKeyFrame(dt, true);
        batch.draw(currentFrame, 0.0F, 0.0F, 271 / pixelsPerMeter, 452 / pixelsPerMeter);
    }
}
