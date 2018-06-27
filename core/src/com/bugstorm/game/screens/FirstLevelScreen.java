package com.bugstorm.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bugstorm.game.GameProject;
import com.bugstorm.game.helpers.GameInfo;
import com.bugstorm.game.sprites.Player;
import com.bugstorm.game.world.FirstWorld;

public class FirstLevelScreen implements Screen{

    private GameProject game;
    private OrthographicCamera gameCamera;
    private Viewport viewPort;
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private Player player;
    private Texture ground;
    private Sprite groundSprite;
    private Sprite flipedGroundSprite;
    private AssetManager manager;
    private FirstWorld firstWorld;
    private static final int virtualWidth = GameInfo.V_WIDTH;
    private static final int virtualHeight = GameInfo.V_HEIGHT;
    private static final float pixelsPerMeter = GameInfo.PPM;
    private float groundSpriteWidth;
    private float groundSpriteHeight;




    public FirstLevelScreen (GameProject game) {
        this.manager = new AssetManager();
        manager.load("ground.png", Texture.class);
        manager.finishLoading();
        this.ground = manager.get("ground.png", Texture.class);
        this.groundSprite = new Sprite(this.ground);
        this.flipedGroundSprite = new Sprite(this.ground);
        flipedGroundSprite.flip(true, false);
        this.groundSpriteWidth = groundSprite.getWidth();
        this.groundSpriteHeight = groundSprite.getHeight();

        this.game = game;
        this.gameCamera = new OrthographicCamera();
        this.viewPort = new FitViewport(virtualWidth / pixelsPerMeter , virtualHeight / pixelsPerMeter, gameCamera);

        gameCamera.position.set(viewPort.getWorldWidth() / 2, viewPort.getWorldHeight() / 2, 0);

        this.world = new World(new Vector2(0,-10), true);

        this.debugRenderer = new Box2DDebugRenderer();

        this.firstWorld = new FirstWorld(this);
        firstWorld.generateGround();

        this.player = new Player(this);
    }

    @Override
    public void show() {

    }

    public void update(float delta){
        handleInput(delta);

        world.step(1 / 60f, 6, 2);

        player.update(delta);

        gameCamera.position.x = player.b2body.getPosition().x + 0.2f;
        gameCamera.update();
    }

    @Override
    public void render(float delta) {
        update(delta);


        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        debugRenderer.render(world, gameCamera.combined);
        game.batch.setProjectionMatrix(gameCamera.combined);
        game.batch.begin();

        game.batch.draw(flipedGroundSprite, -(groundSpriteWidth / pixelsPerMeter)*2.0F, 0.0F, (groundSpriteWidth / pixelsPerMeter)*2, groundSpriteHeight / pixelsPerMeter);
        game.batch.draw(groundSprite, 0.0F, 0.0F, (groundSpriteWidth / pixelsPerMeter)*2, groundSpriteHeight / pixelsPerMeter);
        game.batch.draw(flipedGroundSprite, (groundSpriteWidth / pixelsPerMeter)*2.0F, 0.0F, (groundSpriteWidth / pixelsPerMeter)*2, groundSpriteHeight / pixelsPerMeter);
        game.batch.draw(groundSprite, ((groundSpriteWidth / pixelsPerMeter) * 2.0F) * 2.0F, 0.0F, (groundSpriteWidth / pixelsPerMeter) * 2, groundSpriteHeight / pixelsPerMeter);
        player.draw(game.batch);

        game.batch.end();

    }

    @Override
    public void resize(int width, int height) {
        viewPort.update(width, height);

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
        manager.dispose();
        ground.dispose();
        game.dispose();

    }

    public void handleInput(float delta){
        if (Gdx.input.isTouched() ) {
                if (Gdx.input.getX() >= virtualWidth / 2 && player.b2body.getLinearVelocity().x <= 1.5f) {
                    player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
                } else if (Gdx.input.getX() < virtualWidth / 2 && player.b2body.getLinearVelocity().x >= -1.5f){
                    player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
                }
        }
    }

    public World getWorld(){
        return world;
    }
}
