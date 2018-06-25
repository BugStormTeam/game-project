package com.bugstorm.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import scenes.MainMenu;

public class GameProject extends Game {
	SpriteBatch batch;

	public SpriteBatch getBatch() {

		return this.batch;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new MainMenu(this));

	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
