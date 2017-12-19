package com.paracorn;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Paracorn extends Game {

	//TODO: SETTINGS, VIEWPORT, GOOGLEPLAY, UI CLASSES

	private AssetManager manager;

	public Assets assets;
	public SpriteBatch batch;

	@Override
	public void create () {
		manager = new AssetManager();
		assets = new Assets(manager);
		batch = new SpriteBatch();
		setScreen(new LoadingScreen(manager, this));
		Gdx.gl.glClearColor(1, 0, 0, 1);
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		if(manager.getQueuedAssets() > 0) {
			setScreen(new LoadingScreen(manager, this.getScreen(), this));
		}
		super.resume();
	}

	@Override
	public void dispose () {
		super.dispose();
		batch.dispose();
		manager.dispose();
	}
}
