/*
 * Copyright (C) 2015 fax
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mygdx.gui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 *
 * @author fax
 */
public abstract class Element {
    
    private float x;
    private float y;
    private float width;
    private float height;
    
    Sprite sprite;

    public Element(String sprite, float x, float y, float width, float height) {
        this.sprite = GUIUtils.GUI_ATLAS.createSprite(sprite);
        this.sprite.setFlip(false, true);
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
    }

    public abstract void tap();

    public void setSprite() {
    }

    public void act(float delta) {
    }

    public void draw(Batch batch, float parentAlpha) {
        sprite.setCenterX(x);
        sprite.setCenterY(y);
        sprite.setSize(width, height);
        sprite.draw(batch, parentAlpha);
    }

    /**
     * @return the x
     */
    public float getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public float getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * @return the width
     */
    public float getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(float width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    public float getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(float height) {
        this.height = height;
    }
}
