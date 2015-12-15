/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game.objects;

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
    Sprite right, left, top;

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
        setWalls();
    }

    public void setWalls() {

        if (getStage().getRoom((int) getX(), (int) getY() + 1) == null) {
            top = getStage().getAtlas().createSprite("roof_center");
        }

        if (getStage().getRoom((int) getX() - 1, (int) getY()) == null) {
            left = getStage().getAtlas().createSprite("wall_left");
            if (getStage().getRoom((int) getX(), (int) getY() + 1) == null) {
                //top = getStage().getAtlas().createSprite("roof_left");
            }
        }
        if (getStage().getRoom((int) getX() + 1, (int) getY()) == null) {
            right = getStage().getAtlas().createSprite("wall_right");
            if (getStage().getRoom((int) getX(), (int) getY() + 1) == null) {
                //top = getStage().getAtlas().createSprite("roof_right");
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlfa) {
        sprite.setPosition(getX() * 125 + getStage().getCameraX(), getY() * 125 + getStage().getCameraY());
        sprite.draw(batch);
        if (right != null) {
            right.setPosition(sprite.getX() + 125, sprite.getY());
            right.draw(batch);
        }
        if (left != null) {
            left.setPosition(sprite.getX() - left.getWidth(), sprite.getY());
            left.draw(batch);
        }
        if (top != null) {
            top.setPosition(sprite.getX(), sprite.getY() - top.getHeight());
            top.draw(batch);
        }

    }
}
