package com.paracorn;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;

class Acorn extends Sprite {

    private Circle boundingCircle;
    private final float BOUNDING_RADIUS = 5;
    private Vector2 velocity;
    public boolean touchDown;

    public Acorn(TextureRegion region) {
        super(region);
        boundingCircle = new Circle(0,0,BOUNDING_RADIUS);
        velocity = new Vector2(0,0);
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public Circle getBoundingCircle() {
        boundingCircle.setX(this.getOriginX());
        boundingCircle.setY(this.getOriginY());
        return boundingCircle;
    }

    public void update(float delta) {
        if (touchDown) {
            velocity.y = 240;
        }
        else {
            velocity.y = 60;
        }
    }

    public void hit() {

    }
}
