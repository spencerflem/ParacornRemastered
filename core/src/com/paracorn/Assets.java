package com.paracorn;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
    private TextureAtlas gameAtlas;
    public TextureRegion stickRegion;
    public TextureRegion acornRegion;
    public TextureRegion acornFrownRegion;
    public TextureRegion groundRegion;
    public TextureRegion topStickRegion;
    public TextureRegion cloudRegion;
    public TextureRegion restartButtonRegion;
    public TextureRegion sidegroundRegion;
    public Sound deploySound;
    public Sound undeploySound;
    public Sound hitSound;
    public Sound hitStickSound;
    public Sound hitGroundSound;
    public Sound passSound;
    public Sound buttonSound;
    public Sound resetSound;
    public Music backgroundMusicLoop;
    public Music backgroundMusicIntro;

    public Assets(AssetManager manager) {
        load(manager);
    }

    public void load(AssetManager manager) {
        manager.load("data/game.atlas", TextureAtlas.class);
        manager.load("sound/deploy.wav", Sound.class);
        manager.load("sound/undeploy.wav", Sound.class);
        manager.load("sound/hit.wav", Sound.class);
        manager.load("sound/hitstick.wav", Sound.class);
        manager.load("sound/hitground.wav", Sound.class);
        manager.load("sound/pass.wav", Sound.class);
        manager.load("sound/button.wav", Sound.class);
        manager.load("sound/reset.wav", Sound.class);
        manager.load("music/walkagainintro.wav", Music.class);
        manager.load("music/walkagainloop.wav", Music.class);
    }

    public void assign(AssetManager manager) {
        gameAtlas = manager.get("data/game.atlas", TextureAtlas.class);
        stickRegion = gameAtlas.findRegion("stick");
        acornRegion = gameAtlas.findRegion("acorn");
        acornFrownRegion = gameAtlas.findRegion("acornfrown");
        groundRegion = gameAtlas.findRegion("ground");
        topStickRegion = gameAtlas.findRegion("topstick");
        cloudRegion = gameAtlas.findRegion("cloud");
        restartButtonRegion = gameAtlas.findRegion("restartbutton");
        sidegroundRegion = gameAtlas.findRegion("sideground");
        deploySound = manager.get("sound/deploy.wav", Sound.class);
        undeploySound = manager.get("sound/undeploy.wav", Sound.class);
        hitSound = manager.get("sound/hit.wav", Sound.class);
        hitStickSound = manager.get("sound/hitstick.wav", Sound.class);
        hitGroundSound = manager.get("sound/hitground.wav", Sound.class);
        passSound = manager.get("sound/pass.wav", Sound.class);
        buttonSound = manager.get("sound/button.wav", Sound.class);
        resetSound = manager.get("sound/reset.wav", Sound.class);
        backgroundMusicIntro = manager.get("music/walkagainintro.wav", Music.class);
        backgroundMusicLoop = manager.get("music/walkagainloop.wav", Music.class);
    }
}
