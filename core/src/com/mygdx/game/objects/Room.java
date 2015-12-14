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

/**
 *
 * @author Whizzpered
 */
public class Room extends Actor {

    protected Sprite sprite;

    @Override
    public GameStage getStage() {
        return (GameStage) (super.getStage());
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Room(float x, float y) {
        setX(x);
        setY(y);
        setName("room");
    }

    @Override
    public void act(float delta) {
        super.act(delta);

    }

    public void setSprite() {
        sprite = getStage().getAtlas().createSprite(getName());
        sprite.setFlip(false, true);
        setWidth(sprite.getWidth());
        setHeight(sprite.getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlfa) {
        sprite.setCenter(getX() * 125 + getStage().getCameraX() - 7865, getY() * 125 + getStage().getCameraY() - 15575);
        sprite.draw(batch);
    }
}
