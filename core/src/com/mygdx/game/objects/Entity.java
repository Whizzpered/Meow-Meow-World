/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.GameStage;
import java.util.Random;

/**
 *
 * @author Whizzpered
 */
public abstract class Entity extends Actor {

    protected Sprite sprite;
    float ex, ey;
    protected float velocityx=3, velocityy=3, accelerationy=1;
    Random r = new Random();

    @Override
    public GameStage getStage() {
        return (GameStage) (super.getStage());
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Entity(float x, float y) {
        setX(x);
        setY(y);
        setName("Cat");
    }

    public void goTo(int directionx, int directiony) {
        setX(getX() + velocityx * directionx);
        setY(getY() + velocityy * directiony);
        velocityy += accelerationy;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        Room r = getStage().getRooms()[(int) ((getX() + 10) / 125)][(int) (getY() / 125)];
        if (ey == 0 && r != null && getY() == (r.getY()) * 125 + 58) {
            goTo(1, 0);
        } else {
            setY(getY() + 1);
        }
    }

    public void setSprite() {
        sprite = getStage().getAtlas().createSprite(getName());
        sprite.setFlip(false, true);
        setWidth(sprite.getWidth());
        setHeight(sprite.getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlfa) {
        sprite.setCenter(getX() + getStage().getCameraX(), getY() + getStage().getCameraY());
        sprite.draw(batch);
    }

}
