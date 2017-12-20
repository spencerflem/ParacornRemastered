package com.paracorn;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public class Branch extends Sprite implements Pool.Poolable {
    private Vector2 velocity;
    public boolean neverHit;

    public Branch(TextureRegion region) {
        super(region);
        reset();
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public boolean collides(Circle circle) {
        return this.getBoundingRectangle().contains(circle);
    }

    public boolean isOffScreen(int screenHeight) {
        //System.out.println(getY() > screenHeight);
        return getY() > screenHeight;
    }

    public void update(float delta, Vector2 worldVelocity) {
        System.out.println(delta * (worldVelocity.y + velocity.y));
        this.setPosition(this.getX() + delta * (worldVelocity.x + velocity.x), this.getY() + delta * (worldVelocity.y + velocity.y));
    }

    public void hit() {

    }

    @Override
    public void reset() {
        neverHit = true;
        setPosition(10,10);
        velocity = new Vector2(0,0);
    }
}
