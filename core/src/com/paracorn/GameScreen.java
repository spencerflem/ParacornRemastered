package com.paracorn;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.HdpiUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

public class GameScreen implements Screen {
    final Paracorn game;
    private Stage UIstage;
    private World world;
    private WorldListener worldListener;
    private WorldRenderer renderer;
    private InputMultiplexer multiplexer;
    private InputHandler inputHandler;
    private OrthographicCamera camera;
    private ExtendViewport viewport;
    private MusicPlayer musicPlayer;

    private TiledDrawable sidegroundDrawable;

    private int WORLD_MIN_WIDTH = 100;
    private int WORLD_MIN_HEIGHT = 100;
    private int WORLD_MAX_WIDTH = 100;
    private int WORLD_MAX_HEIGHT = 600;

    private float accumulator = 0;
    private static final float STEP_SIZE_MAX = 1/4f;
    private static final float STEP_SIZE = 1/60f;

    public GameScreen(final Paracorn game) {
        this.game = game;
        worldListener = new WorldListener();
        world = new World(worldListener);
        renderer = new WorldRenderer(game.assets, game.batch, world);
        viewport = new ExtendViewport(WORLD_MIN_WIDTH, WORLD_MIN_HEIGHT, WORLD_MAX_WIDTH, WORLD_MAX_HEIGHT);
        UIstage = new Stage(viewport, game.batch);
        multiplexer = new InputMultiplexer();
        inputHandler = new InputHandler();
        multiplexer.addProcessor(UIstage);
        multiplexer.addProcessor(inputHandler);
        Gdx.input.setInputProcessor(multiplexer);
        camera = new OrthographicCamera();
        viewport.setCamera(camera);
        viewport.apply();
        updateCamera();
        musicPlayer = new MusicPlayer(game.assets.backgroundMusicIntro, game.assets.backgroundMusicLoop);
        //musicPlayer.play();
        sidegroundDrawable = new TiledDrawable(game.assets.sidegroundRegion);
    }

    private void updateCamera() {
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
    }

    @Override
    public void render(float delta) {
        act(delta);
        draw();
    }

    private void act(float delta) {
        float frameTime = Math.min(delta, STEP_SIZE_MAX);
        accumulator += frameTime;
        while (accumulator >= STEP_SIZE) {
            world.act(STEP_SIZE);
            accumulator -= STEP_SIZE;
        }
        UIstage.act(frameTime);
    }

    private void draw() {
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClearColor(1f, 0f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        renderer.draw();
        game.batch.draw(game.assets.restartButtonRegion, 0,0,100, 600);
        game.batch.end();
        UIstage.draw();

        /* TODO: DRAW IN SIDES OF VIEWPORT!

        float coord300 = viewport.getWorldHeight();
        //System.out.println("coord300: " + Float.toString(coord300));
        //System.out.println("ww:");
        //System.out.println(viewport.getRightGutterX());

        //copypasta
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        HdpiUtils.glViewport(0, 0, screenWidth, screenHeight);
        //game.batch.getProjectionMatrix().idt().setToOrtho2D(0, 0, screenWidth, screenHeight);
        //game.batch.getTransformMatrix().idt();
        float leftGutterWidth = viewport.getLeftGutterWidth();
        game.batch.begin();
        //Each sideground should be 160 world units wide
        System.out.println(viewport.unproject(new Vector2(viewport.getRightGutterX(),0)).x);
        game.batch.setColor(Color.CORAL);
        game.batch.draw(game.assets.restartButtonRegion, 0, 0, (viewport.unproject(new Vector2(0, 0)).x*-1), 10);
        //sidegroundDrawable.draw(game.batch, leftGutterWidth-WORLD_MAX_HEIGHT/2, viewport.getBottomGutterHeight(), WORLD_MAX_HEIGHT/2+WORLD_MAX_WIDTH/10, screenHeight-2*viewport.getBottomGutterHeight());
        //sidegroundDrawable.draw(game.batch, viewport.getRightGutterX(), 0, viewport.getRightGutterWidth(), screenHeight);
        game.batch.end();
        viewport.update(screenWidth, screenHeight, true); // Restore viewport.

        */
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        updateCamera();
    }

    @Override
    public void pause() {
        world.pause();
    }

    @Override
    public void resume() {
        world.resume();
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
        world.pause();
    }

    @Override
    public void dispose() {
        UIstage.dispose();
    }
}
