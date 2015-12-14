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
public class Entity extends Actor{
    
    protected Sprite sprite;
    
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
    }
    
    public void setSprite() {
        sprite = getStage().getAtlas().createSprite(getName());
        sprite.setFlip(false, true);
        setWidth(sprite.getWidth());
        setHeight(sprite.getHeight());
    }
    
    @Override
    public void draw(Batch batch, float parentAlfa) {
        sprite.setCenter(getX()+ getStage().getCameraX(), getY()+ getStage().getCameraY());
        sprite.draw(batch);
    }
    
}
