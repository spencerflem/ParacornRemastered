package com.paracorn;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public class Branch extends DynamicSprite implements Pool.Poolable {
    private Vector2 velocity;
    public boolean neverHit = true;

    public Branch() {
        reset();
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public boolean collides(Circle circle) {
        return this.getBoundingRectangle().contains(circle);
    }

    public boolean isOffScreen(int screenHeight) {
        return getX() > screenHeight;
    }

    @Override
    public void update(float delta, Vector2 worldVelocity) {
        this.scroll(delta * (worldVelocity.x + velocity.x) , delta * (worldVelocity.y + velocity.y));
    }

    public void hit() {

    }

    @Override
    public void reset() {
        setPosition(-10,-10);
        velocity = new Vector2(0,0);
    }
}
