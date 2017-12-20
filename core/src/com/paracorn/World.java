package com.paracorn;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;

import java.util.Iterator;
import java.util.Random;

public class World {

    public enum GameState {
        READY, RUNNING, PAUSED, GAMEOVER
    }
    private enum SubState {
        FALLING_BEGIN, FALLING_CONTROLLABLE, FALLING_END, NOT_FALLING
    }
    private GameState currentState = GameState.READY;
    public SubState currentSubState = SubState.NOT_FALLING;

    //todo: move to own classes?
    private int stickDistanceApart = 50;
    private int stickMoveDistance = 90;
    private int BRANCH_Y_DISTANCE = 125;
    private int stickSpeed = 100;
    private int topSpeed = 240;
    private int chuteSpeed = 60;
    //todo: Branches class? <- would need acorn values somehow
    private Acorn acorn;
    //private Accessory accessory;
    //private Ground ground;
    //private TopStick topStick;
    //private Array<Cloud> cloudArray;
    private Array<Branch> branchArray;
    private final Pool<Branch> branchPool;
    private WorldListener listener;
    Assets assets;
    SpriteBatch batch;
    private double runningTime;
    private Random random;
    private boolean touchDown;

    public World(WorldListener listener, final Assets assets, SpriteBatch batch) {
        this.listener = listener;
        this.assets = assets;
        this.batch = batch;
        branchPool = new Pool<Branch>() {
            @Override
            protected Branch newObject() {
                return new Branch(assets.stickRegion);
            }
        };
        reset();
    }

    public void reset() {
        currentState = GameState.READY;
        runningTime = 0;
        acorn = new Acorn(assets.acornRegion);
        branchArray = new Array<Branch>();
        touchDown = false;
    }

    public void draw() {
        batch.begin();
        acorn.draw(batch);
        for (Branch branch : branchArray) {
            branch.draw(batch);
        }
        batch.end();
    }

    private void scrollClouds(float delta) {
        //scroll delta * acorn.xSpeed + cloud.xSpeed etc..
    }

    private void insertBranches() {
        if(branchArray.size == 0) {
            createBranch(-10);
        }
        if(branchArray.peek().getY() > 0) {
            createBranch(branchArray.peek().getY() - BRANCH_Y_DISTANCE);
        }
    }

    private void createBranch(float y) {
        System.out.println("create");
        Branch newBranch = branchPool.obtain();
        newBranch.setPosition(-20,y); //not 20 - setup other values too
        branchArray.add(newBranch);
    }

    private void scrollBranches(float delta) {
        for(Iterator<Branch> i = branchArray.iterator(); i.hasNext();) {
            Branch branch = i.next();
            branch.update(delta, acorn.getVelocity());
            System.out.println(acorn.getVelocity());
            if (branch.isOffScreen(200)) { //not 12, screenheight! <- find
                branchPool.free(branch);
                i.remove();
            }
            if (branch.neverHit && branch.collides(acorn.getBoundingCircle())) {
                if(currentState == GameState.RUNNING && currentSubState == SubState.FALLING_CONTROLLABLE) {
                    //currentSubState = SubState.FALLING_END;
                }
                branch.hit();
                acorn.hit();
            }
        }
    }

    /*
    private void screenShake() {
        float radius = Math.abs(shakeForce)/300;
        //System.out.println(radius);
        float newAngle = lastShakeDirection;
        newAngle += (180 + MathUtils.random(-60, 60));
        Camera camera = viewport.getCamera();
        if(shakeForce < 10) {
            shakeForce = 0;
            camera.position.x = game.width/2;
            camera.position.y = game.height - viewport.getWorldHeight()/2 + cameraMoved;
            camera.update();
        }
        else {
            camera.position.x = (float) (game.width/2 + Math.sin(newAngle)*radius);
            camera.position.y = (float) (game.height - viewport.getWorldHeight()/2 + cameraMoved + Math.cos(newAngle)*radius);
            camera.update();
        }
        lastShakeDirection = (int) newAngle;
        shakeForce *= 0.9;
    }
    */

    public void act(float delta) {
        switch (currentState) {
            case READY:
                updateReady(delta);
                break;

            case RUNNING:
                updateRunning(delta);
                break;

            case PAUSED:
                updatePaused(delta);
                break;

            case GAMEOVER:
                updateGameOver(delta);
                break;

            default:
                break;
        }
    }

    private void updateReady(float delta) {
        //todo:
        //if tapped: set to running (tell UI somehow)
        Gdx.gl.glClearColor(0f, 1f, 1f, 1);
        scrollClouds(delta);
        if(touchDown) {
            currentSubState = SubState.FALLING_BEGIN;
            start();
        }
    }

    private void updateRunning(float delta) {
        runningTime += delta;
        switch (currentSubState) {
            case FALLING_BEGIN:
                Gdx.gl.glClearColor(1f, 0f, 0f, 1);
                if (runningTime >= 0.6) {
                    currentSubState = SubState.FALLING_CONTROLLABLE;
                }
                acorn.touchDown = false;
                break;
            case FALLING_CONTROLLABLE:
                Gdx.gl.glClearColor(0f, 1f, 0f, 1);
                acorn.touchDown = touchDown;
                insertBranches();
                break;
            case FALLING_END:
                Gdx.gl.glClearColor(0f, 0f, 0f, 1);
                acorn.touchDown = false;
                break;
            case NOT_FALLING:
                Gdx.gl.glClearColor(1f, 1f, 1f, 1);
                acorn.touchDown = false;
                break;
        }
        acorn.update(delta);
        scrollClouds(delta);
        scrollBranches(delta);
    }

    private void updatePaused(float delta) {
        Gdx.gl.glClearColor(1f, 0f, 1f, 1);
    }

    private void updateGameOver(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 0f, 1);
    }

    public void touchDown() {
        touchDown = true;
    }

    public void touchUp() {
        touchDown = false;
    }

    public boolean isReady() {
        return currentState == GameState.READY;
    }

    public boolean isRunning() {
        return currentState == GameState.RUNNING;
    }

    public boolean isPaused() {
        return currentState == GameState.PAUSED;
    }

    public boolean isGameOver() {
        return currentState == GameState.GAMEOVER;
    }

    public boolean pause() {
        if(currentState == GameState.RUNNING) {
            currentState = GameState.PAUSED;
            return true;
        }
        else {
            return false;
        }
    }

    public boolean resume() {
        if(currentState == GameState.PAUSED) {
            currentState = GameState.RUNNING;
            return true;
        }
        else {
            return false;
        }
    }

    private boolean start() {
        if(currentState == GameState.READY) {
            currentState = GameState.RUNNING;
            currentSubState = SubState.FALLING_BEGIN;
            return true;
        }
        else {
            return false;
        }
    }

    private boolean end() {
        if(currentState == GameState.RUNNING) {
            currentState = GameState.GAMEOVER;
            currentSubState = SubState.NOT_FALLING;
            return true;
        }
        else {
            return false;
        }
    }
}
