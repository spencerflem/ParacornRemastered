package com.paracorn;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
        FALLING_BEGIN, FALLING_CONTROLLABLE, FALLING_GAMEPLAY, FALLING_END, NOT_FALLING
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

    public World(WorldListener listener, Assets assets, SpriteBatch batch) {
        this.listener = listener;
        this.assets = assets;
        this.batch = batch;
        branchPool = new Pool<Branch>() {
            @Override
            protected Branch newObject() {
                return new Branch();
            }
        };
        reset();
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
        if(branchArray.peek().getY() > 0) {
            createBranch(branchArray.peek().getY() - BRANCH_Y_DISTANCE);
        }
    }

    private void createBranch(float y) {
        Branch newBranch = branchPool.obtain();
        newBranch.setPosition(20,y); //not 20 - setup other values too
        branchArray.add(newBranch);
    }

    private void scrollBranches(float delta) {
        for(Iterator<Branch> i = branchArray.iterator(); i.hasNext();) {
            Branch branch = i.next();
            branch.update(delta, acorn.getVelocity());
            if (branch.isOffScreen(12)) { //not 12, screenheight! <- find
                branchPool.free(branch);
                i.remove();
            }
            if (branch.neverHit && branch.collides(acorn.getBoundingCircle())) {
                if(currentState == GameState.RUNNING && currentSubState == SubState.FALLING_GAMEPLAY) {
                    currentSubState = SubState.FALLING_END;
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

    public void reset() {
        currentState = GameState.READY;
        runningTime = 0;
        //put them in the correct locations
    }

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
        scrollClouds(delta);
    }

    private void updateRunning(float delta) {
        runningTime += delta;
        if(runningTime >= 600000000) {
            currentSubState = SubState.FALLING_CONTROLLABLE;
        }
        if(currentSubState == SubState.FALLING_GAMEPLAY) {
            insertBranches();
        }
        scrollClouds(delta);
        scrollBranches(delta);
    }

    private void updatePaused(float delta) {
        //todo
    }

    private void updateGameOver(float delta) {
        //todo
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
