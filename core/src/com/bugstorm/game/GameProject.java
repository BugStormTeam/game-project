package com.bugstorm.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bugstorm.game.screens.MainMenu;
import com.bugstorm.game.screens.TestScreen;


public class GameProject extends Game {

	public static final short GROUND_BIT = 2;
	public static final short PLAYER_BIT = 4;
	public static final short SIGN_BIT = 8;



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
