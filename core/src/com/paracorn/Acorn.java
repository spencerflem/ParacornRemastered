package com.paracorn;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Disposable;

class Acorn extends DynamicSprite {

    private Circle boundingCircle;
    private final float BOUNDING_RADIUS = 5;
    private Vector2 velocity;

    public Acorn() {
        boundingCircle = new Circle(0,0,BOUNDING_RADIUS);
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public Circle getBoundingCircle() {
        boundingCircle.setX(this.getOriginX());
        boundingCircle.setY(this.getOriginY());
        return boundingCircle;
    }

    @Override
    public void update(float delta, Vector2 worldVelocity) {

    }

    public void hit() {

    }
}
