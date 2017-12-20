package com.paracorn;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;

public class LoadingScreen implements Screen {

    private AssetManager manager;
    private Screen nextScreen;
    private final Paracorn game;

    public LoadingScreen(AssetManager manager, Paracorn game) {
        this.manager = manager;
        this.game = game;
    }

    public LoadingScreen(AssetManager manager, Screen nextScreen, Paracorn game) {
        this.manager = manager;
        this.nextScreen = nextScreen;
        this.game = game;
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        //draw loading stuffs here
        game.batch.end();
        if(manager.update()){
            game.assets.assign(manager);
            if(nextScreen == null) {
                game.setScreen(new GameScreen(game));
            }
            else {
                game.setScreen(nextScreen);
            }
            this.dispose();
        }
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
}
