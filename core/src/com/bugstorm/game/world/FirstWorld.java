package com.bugstorm.game.world;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.bugstorm.game.GameProject;
import com.bugstorm.game.helpers.GameInfo;
import com.bugstorm.game.screens.FirstLevelScreen;

public class FirstWorld {
    private World world;
    private BodyDef bodyDef;
    private PolygonShape shape;
    private FixtureDef fixtureDef;
    Body body;

    public FirstWorld(FirstLevelScreen screen) {
        this.world = screen.getWorld();
        this.bodyDef = new BodyDef();
        this.shape = new PolygonShape();
        this.fixtureDef = new FixtureDef();


    }

    public void generateGround(){
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0 / GameInfo.PPM, 20 / GameInfo.PPM);
        body = world.createBody(bodyDef);

        shape.setAsBox(1000000 / GameInfo.PPM, 10 / GameInfo.PPM);
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
    }

}
