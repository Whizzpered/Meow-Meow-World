/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
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

    @Override
    public void act(float delta) {
        super.act(delta);
        Room r = getStage().getRooms()[(int) ((getX() + 10) / 125)][(int) (getY() / 125)];

        if (ey == 0 && r != null && getY() == (r.getY()) * 125 + 58) {
            setX(getX() + 1);
            Gdx.app.log((int) (getX() / 125) + "", (r == null) + "");
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
