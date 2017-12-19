package com.paracorn;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.concurrent.TimeUnit;

public class WorldRenderer {

    private Assets assets;
    private SpriteBatch batch;
    private World world;

    public WorldRenderer(Assets assets, SpriteBatch batch, World world) {
        this.assets = assets;
        this.batch = batch;
        this.world = world;
    }

    public void draw() {
        /* use this for anything with no transparacy: eg large backround etc..
        batch.disableBlending();
            //stuff would go here
        batch.enableBlending(); */
        //batch.draw(stuff);
    }
}