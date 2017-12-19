package com.paracorn;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class World {

    public enum GameState {
        READY, RUNNING, PAUSED, GAMEOVER
    }
    private GameState currentState = GameState.READY;

    private Acorn acorn;
    //private Accessory accessory;
    //private Ground ground;
    //private TopStick topStick;
    //private Array<Cloud> cloudArray;
    private Array<Branch> stickArray;

    WorldListener listener;

    public World(WorldListener listener) {
        this.listener = listener;
        //init all needed sprites
        reset();
    }

    public void reset() {
        currentState = GameState.READY;
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

    public void updateReady(float delta) {
        //todo:
        //if tapped: set to running
    }

    public void updateRunning(float delta) {
        //todo
        //if(TIME ALLOWS:) {
        //    acorn.setControllability(true);
        //}
        //if() {

        //}
        //note: this includes both falling in the beginning and while dead
    }

    public void updatePaused(float delta) {
        //todo
    }

    public void updateGameOver(float delta) {
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

    public boolean start() {
        if(currentState == GameState.READY) {
            currentState = GameState.RUNNING;
            return true;
        }
        else {
            return false;
        }
    }

    public boolean end() {
        if(currentState == GameState.RUNNING) {
            currentState = GameState.GAMEOVER;
            return true;
        }
        else {
            return false;
        }
    }
}
