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
import java.awt.Point;
import java.util.Stack;

/**
 *
 * @author Whizzpered
 */
public abstract class Entity extends Actor {

    protected Sprite sprite;
    protected float velocityx = 1, velocityy = 1;
    private int counter = 0;
    Stack<Point> path = new Stack<Point>();
    private Point target;

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

    public void moveTo(int endx, int endy) {
        int dx = (int) getX() / 125 - endx;
        int dy = (int) getY() / 125 - endy;
        Gdx.app.log("Volvo", dx + " " + dy);
        for (int i = 1; i <= dx; i++) {
            path.push(new Point(endx + i, endy));
        }
        for (int i = -1; i >= dx; i--) {
            path.push(new Point(endx + i, endy));
        }
        for (int i = 1; i <= dy; i++) {
            path.push(new Point(endx, endy + i));
        }
        for (int i = -1; i >= dy; i--) {
            path.push(new Point(endx, endy + i));
        }
    }

    public void goTo() {
        if (target == null) {
            if (path.size() > 0) {
                target = path.pop();
                Gdx.app.log("Valva", "->" + target.x + " " + target.y);
            } else {
                counter++;
                if (counter == 30) {
                    counter = 0;
                    moveTo(15 + getStage().r.nextInt(1), 60 + getStage().r.nextInt(4));
                }
            }
        }
        if (target != null) {
            int rx = (int) getX() / 125, ry = (int) getY() / 125;
            if (rx == target.x && ry == target.y) {
                target = null;
            } else {
                int directionx = 0, directiony = 0;
                if (target.x != rx) {
                    directionx = (target.x - rx) / Math.abs(target.x - rx);
                }
                if (target.y != ry) {
                    directiony = (target.y - ry) / Math.abs(target.y - ry);
                }
                setX(getX() + velocityx * directionx);
                setY(getY() + velocityy * directiony);
            }
        }

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        goTo();
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
