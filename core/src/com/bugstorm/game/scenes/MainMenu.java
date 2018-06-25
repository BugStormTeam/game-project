package com.bugstorm.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.bugstorm.game.GameProject;
import com.bugstorm.game.screens.FirstLevelScreen;

import com.bugstorm.game.helpers.GameInfo;
import sun.applet.Main;


public class MainMenu implements Screen{

    private GameProject game;
    private Texture background;
    private Texture playButtonActive;
    private Texture creditsButtonActive;
    private Texture exitButtonActive;
    private Texture playButtonInactive;
    private Texture creditsButtonInactive;
    private Texture exitButtonInactive;



    public MainMenu(GameProject gameProject){

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

        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().begin();
        game.getBatch().draw(background, 0, 0);

        int buttonsX = GameInfo.WIDTH / 2 - playButtonActive.getWidth() / 2;
        int buttonsY = GameInfo.HEIGHT / 2 - playButtonActive.getHeight() / 2;
        if(Gdx.input.getX() < buttonsX + playButtonActive.getWidth() && Gdx.input.getX() > buttonsX && Gdx.input.getY() < buttonsY + playButtonActive.getHeight() && Gdx.input.getY() > buttonsY){
            game.getBatch().draw(playButtonActive, buttonsX, buttonsY);
            if(Gdx.input.isTouched()){
                this.dispose();
                game.setScreen(new FirstLevelScreen(game));
            }
        } else {
            game.getBatch().draw(playButtonInactive, buttonsX, buttonsY);
        }if(Gdx.input.getX() < buttonsX + creditsButtonActive.getWidth() && Gdx.input.getX() > buttonsX && Gdx.input.getY() < buttonsY + creditsButtonActive.getHeight() + 50 && Gdx.input.getY() > buttonsY + 50){
            game.getBatch().draw(creditsButtonActive, buttonsX, buttonsY - 50);
            if(Gdx.input.isTouched()){
                game.setScreen(new CreditsScreen(game));
            }
        } else {
            game.getBatch().draw(creditsButtonInactive, buttonsX, buttonsY - 50);
        }if(Gdx.input.getX() < buttonsX + exitButtonActive.getWidth() && Gdx.input.getX() > buttonsX && Gdx.input.getY() < buttonsY + exitButtonActive.getHeight() + 100 && Gdx.input.getY() > buttonsY + 100){
            game.getBatch().draw(exitButtonActive, buttonsX, buttonsY - 100);
            if(Gdx.input.isTouched()){
                Gdx.app.exit();
            }
        } else {
            game.getBatch().draw(exitButtonInactive, buttonsX, buttonsY - 100);
        }

        game.getBatch().end();

    }

    @Override
    public void resize(int width, int height) {

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
