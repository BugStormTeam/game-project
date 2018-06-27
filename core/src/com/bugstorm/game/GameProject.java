package com.bugstorm.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bugstorm.game.screens.MainMenu;
import com.bugstorm.game.screens.TestScreen;


public class GameProject extends Game {


	public SpriteBatch batch;

	public SpriteBatch getBatch() {
		return batch;
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
		super.dispose();
		batch.dispose();
	}
}
