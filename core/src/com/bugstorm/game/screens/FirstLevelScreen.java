package com.bugstorm.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bugstorm.game.GameProject;
import com.bugstorm.game.entities.Bullet;
import com.bugstorm.game.helpers.GameInfo;
import com.bugstorm.game.services.Animate;
import com.bugstorm.game.sprites.MovementExplainer;
import com.bugstorm.game.sprites.Player;
import com.bugstorm.game.world.FirstWorld;

import java.util.ArrayList;


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
    private float dt;
    private MovementExplainer explainer;
    private MovementExplainer explainer2;
    private Animation<TextureRegion> dialogBox;
    private TextureAtlas atlas;
    private float stateTime = 0f;
    int count = 0;
    private Texture texture;
    private TextureRegion frame;
    private ArrayList<Bullet> bullets;
    private int shootTimer = 5;
    private int shootWaitTime = 5;
    private static boolean shooting;

    public FirstLevelScreen (GameProject game) {
        this.manager = new AssetManager();
        manager.load("firstLevelGraphic.png", Texture.class);
        manager.finishLoading();
        this.ground = manager.get("firstLevelGraphic.png", Texture.class);
        this.groundSprite = new Sprite(this.ground);
        this.flipedGroundSprite = new Sprite(this.ground);
        flipedGroundSprite.flip(true, false);
        this.groundSpriteWidth = groundSprite.getWidth();
        this.groundSpriteHeight = groundSprite.getHeight();
        bullets = new ArrayList<Bullet>();
        this.game = game;
        this.gameCamera = new OrthographicCamera();
        this.viewPort = new FitViewport((virtualWidth / pixelsPerMeter) * 1.3f , (virtualHeight / pixelsPerMeter) * 1.3f, gameCamera);

        gameCamera.position.set(viewPort.getWorldWidth() / 2, viewPort.getWorldHeight() / 2, 0);

        this.world = new World(new Vector2(0,-10), true);

        this.debugRenderer = new Box2DDebugRenderer();

        this.firstWorld = new FirstWorld(this);
        firstWorld.generateGround();

        this.player = new Player(this);

        this.explainer = new MovementExplainer(this, 900 / GameInfo.PPM, 250 / GameInfo.PPM);
        this.explainer2 = new MovementExplainer(this, 2900 / GameInfo.PPM, 250 / GameInfo.PPM);

        this.atlas = new TextureAtlas(Gdx.files.internal("sheets/dymek.atlas"));
        this.dialogBox = new Animation<TextureRegion>(0.066f, atlas.findRegions("dymek"));
        this.texture = new Texture("dymek2.png");


    }

    @Override
    public void show() {

    }

    public void update(float delta){
        handleInput(delta);

        world.step(1 / 60f, 6, 2);

        player.update(delta);
        explainer.update(delta);
        explainer2.update(delta);

        gameCamera.position.x = player.b2body.getPosition().x + 0.5f;

        gameCamera.update();
    }

    @Override
    public void render(float delta) {
        update(delta);

        stateTime += Gdx.graphics.getDeltaTime();

        ArrayList<Bullet> bulletsToRemove = new ArrayList<Bullet>();
        for(Bullet bullet : bullets){
            bullet.update(delta,player.b2body.getPosition().x);
            if(bullet.remove){
                bulletsToRemove.add(bullet);
            }
        }
        if(!bulletsToRemove.isEmpty())
        {
            bullets.removeAll(bulletsToRemove);
            System.out.println("Bullets removed");
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        debugRenderer.render(world, gameCamera.combined);
        game.batch.setProjectionMatrix(gameCamera.combined);

        frame = dialogBox.getKeyFrame(stateTime, false);

        game.batch.begin();

        game.batch.draw(flipedGroundSprite, -(groundSpriteWidth / pixelsPerMeter) * 2.0F, 0.0F, (groundSpriteWidth / pixelsPerMeter) * 2, (groundSpriteHeight / pixelsPerMeter) * 2);
        game.batch.draw(groundSprite, 0.0F, 0.0F, (groundSpriteWidth / pixelsPerMeter) * 2, (groundSpriteHeight / pixelsPerMeter) * 2);
        game.batch.draw(flipedGroundSprite, (groundSpriteWidth / pixelsPerMeter) * 2.0F, 0.0F, (groundSpriteWidth / pixelsPerMeter) * 2, (groundSpriteHeight / pixelsPerMeter) * 2);
        game.batch.draw(groundSprite, ((groundSpriteWidth / pixelsPerMeter) * 2.0F) * 2.0F, 0.0F, (groundSpriteWidth / pixelsPerMeter) * 2, (groundSpriteHeight / pixelsPerMeter) * 2);
        explainer.draw(game.batch);
        explainer2.draw(game.batch);
        player.draw(game.batch);

        if (player.getX() <= explainer.getBoundingRectangle().getX() + 300 / pixelsPerMeter && player.getX() >= explainer.getBoundingRectangle().getX() - 500 / pixelsPerMeter){
            game.batch.draw(frame, 1000 / GameInfo.PPM, 500 / GameInfo.PPM, 500 / pixelsPerMeter, 500 / pixelsPerMeter);
        }

        if (player.getX() <= explainer2.getBoundingRectangle().getX() + 300 / pixelsPerMeter && player.getX() >= explainer2.getBoundingRectangle().getX() - 500 / pixelsPerMeter){
            game.batch.draw(texture, 3000 / GameInfo.PPM, 500 / GameInfo.PPM, 500 / pixelsPerMeter, 300 / pixelsPerMeter);
        }

        for (Bullet bullet : bullets) {
            bullet.render(game.getBatch());
        }

        this.shootTimer += 1;
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
        game.batch.dispose();
        atlas.dispose();
        texture.dispose();

    }

    public void handleInput(float delta){
        if (Gdx.input.isTouched() ) {
                if (Gdx.input.getX() >= virtualWidth / 2 + 900f && player.b2body.getLinearVelocity().x <= 1.3f) {
                    player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
                    shooting = false;
                } else if (Gdx.input.getX() < virtualWidth / 2 + 100f && player.b2body.getLinearVelocity().x >= -1.3f){
                    player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
                    shooting = false;
                }
                else if ((player.getrunningRight() && Gdx.input.getX() > player.getX() + 1100) && (Gdx.input.getX() < player.getX() + 1300f) &&  shootTimer > this.shootWaitTime)
                {
                    bullets.add(new Bullet(player.b2body.getPosition().x + 0.2f,player.b2body.getPosition().y + 0.2f, player.getrunningRight()));
                    System.out.println("shooted bullet");
                    shootTimer = 0;
                    shooting = true;
                } else if ((!player.getrunningRight() && Gdx.input.getX() > player.getX() + 1550f) && (Gdx.input.getX() < player.getX() + 1800f) &&  shootTimer > this.shootWaitTime){
                    bullets.add(new Bullet(player.b2body.getPosition().x + 0.2f,player.b2body.getPosition().y + 0.2f, player.getrunningRight()));
                    System.out.println("shooted bullet");
                    shootTimer = 0;
                    shooting = true;
                }

        }
        else{
            shooting = false;
        }
    }

    public World getWorld(){
        return world;
    }
    public static boolean getShooting(){
        return shooting;
    }
}
