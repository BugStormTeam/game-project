package com.bugstorm.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.bugstorm.game.GameProject;

import com.bugstorm.game.helpers.GameInfo;


public class MainMenu implements Screen{

    private GameProject game;
    private Texture background;
    private Texture playButtonActive;
    private Texture creditsButtonActive;
    private Texture exitButtonActive;
    private Texture playButtonInactive;
    private Texture creditsButtonInactive;
    private Texture exitButtonInactive;
    private Camera camera;
    private Viewport viewport;
    private World world;
    private Box2DDebugRenderer debugRenderer;

    public MainMenu(GameProject gameProject){


    camera = new OrthographicCamera();
    viewport = new FitViewport(GameInfo.V_WIDTH / GameInfo.PPM, GameInfo.V_HEIGHT / GameInfo.PPM, camera);
    camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

    this.game = gameProject;
    background = new Texture("menuBG.jpg");
    playButtonActive = new Texture("button_play.png");
    creditsButtonActive = new Texture("button_credits.png");
    exitButtonActive = new Texture("button_exit.png");
    playButtonInactive = new Texture("button_play_inactive.png");
    creditsButtonInactive = new Texture("button_credits_inactive.png");
    exitButtonInactive = new Texture("button_exit_inactive.png");

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.getBatch().setProjectionMatrix(camera.combined);

        game.getBatch().begin();
        game.getBatch().draw(background, 0, 0, background.getWidth() / GameInfo.PPM * 2, background.getHeight() / GameInfo.PPM * 2);

        float buttonsX = (GameInfo.V_WIDTH / 2 / GameInfo.PPM) - (playButtonActive.getWidth() / 2 / GameInfo.PPM);
        float buttonsY = (GameInfo.V_HEIGHT / 2 / GameInfo.PPM)- (playButtonActive.getHeight() / 2 / GameInfo.PPM);
        if(Gdx.input.getX() < buttonsX + playButtonActive.getWidth() && Gdx.input.getX() > buttonsX && Gdx.input.getY() < buttonsY + playButtonActive.getHeight() && Gdx.input.getY() > buttonsY){
            game.getBatch().draw(playButtonActive, buttonsX, buttonsY, playButtonActive.getWidth() / GameInfo.PPM * 2, playButtonActive.getHeight() / GameInfo.PPM * 2);
            if(Gdx.input.isTouched()){
                this.dispose();
                game.setScreen(new FirstLevelScreen(game));
            }
        } else {
            game.getBatch().draw(playButtonInactive, buttonsX, buttonsY,playButtonActive.getWidth() / GameInfo.PPM * 2, playButtonActive.getHeight() / GameInfo.PPM * 2);
        }if(Gdx.input.getX() < buttonsX + creditsButtonActive.getWidth() / GameInfo.PPM * 2 && Gdx.input.getX() > buttonsX && Gdx.input.getY() < buttonsY + creditsButtonActive.getHeight() / GameInfo.PPM * 2 + 0.2f && Gdx.input.getY() > buttonsY + 0.2f){
            game.getBatch().draw(creditsButtonActive, buttonsX, buttonsY - 0.2f, playButtonActive.getWidth() / GameInfo.PPM * 2, playButtonActive.getHeight() / GameInfo.PPM * 2);
            if(Gdx.input.isTouched()){
                game.setScreen(new CreditsScreen(game));
            }
        } else {
            game.getBatch().draw(creditsButtonInactive, buttonsX, buttonsY - 0.2f, playButtonActive.getWidth() / GameInfo.PPM * 2, playButtonActive.getHeight() / GameInfo.PPM * 2);
        }if(Gdx.input.getX() < buttonsX + exitButtonActive.getWidth() / GameInfo.PPM && Gdx.input.getX() > buttonsX && Gdx.input.getY() < buttonsY + exitButtonActive.getHeight() + 0.4f && Gdx.input.getY() > buttonsY + 0.4f){
            game.getBatch().draw(exitButtonActive, buttonsX, buttonsY - 0.4f, playButtonActive.getWidth() / GameInfo.PPM * 2, playButtonActive.getHeight() / GameInfo.PPM * 2);
            if(Gdx.input.isTouched()){
                Gdx.app.exit();
            }
        } else {
            game.getBatch().draw(exitButtonInactive, buttonsX, buttonsY - 0.4f, playButtonActive.getWidth() / GameInfo.PPM * 2, playButtonActive.getHeight() / GameInfo.PPM * 2);
        }

        game.getBatch().end();

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
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

    }
} // main menu screen
