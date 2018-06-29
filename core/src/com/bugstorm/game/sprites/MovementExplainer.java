package com.bugstorm.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.bugstorm.game.GameProject;
import com.bugstorm.game.helpers.GameInfo;
import com.bugstorm.game.screens.FirstLevelScreen;

public class MovementExplainer extends GameObject {
    private Texture texture;
    private TextureRegion textRegion;


    public MovementExplainer(FirstLevelScreen screen, float x, float y){
        super(screen, x, y);
        this.texture = new Texture("signal.png");
        this.textRegion = new TextureRegion(texture, 0, 0, texture.getWidth() , texture.getHeight());

        setRegion(textRegion);


    }

    @Override
    public void defineObject() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.StaticBody;
        body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(600 / GameInfo.PPM, 350 / GameInfo.PPM);
        fdef.filter.categoryBits = GameProject.SIGN_BIT;
        fdef.filter.maskBits = GameProject.GROUND_BIT;
        fdef.shape = shape;

        body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void use(Player Player) {

    }

    public void update(float delta){
        super.update(delta);
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
    }
}
