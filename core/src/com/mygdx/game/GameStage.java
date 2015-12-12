/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.gui.Element;
import com.mygdx.gui.GUILayer;

/**
 *
 * @author Whizzpered
 */
public class GameStage extends Stage {

    public MenuStage menu;
    private GUILayer layer = new GUILayer();
    private float points, cameraX = 0, cameraY = 0;
    public float pointCoef = 5000f;
    AssetManager asset;
    private TextureAtlas atlas;
    private BitmapFont font;
    private Sprite bg;
    public boolean changeEventColor = false;
    private boolean gameOver = false;

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameover) {
        gameOver = gameover;
    }

    //list of Events that are currently working
    public BitmapFont getFont() {
        return font;
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public int getPoints() {
        return (int) points;
    }

    public GameStage(Viewport vp, MenuStage menu) {
        super(vp);
        this.menu = menu;
        initialize();
    }

    public void initialize() {
        initAssets();
        initGUI();
        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int we) {
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int we) {
                layer.tapHandleCrutch_up(event, x, y, pointer, we);
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                cameraX = cameraX + x - 135;
                cameraY = cameraY + y - 180;
            }
        });
    }

    private void initAssets() {
        asset = menu.asset;
        bg = new Sprite((Texture) asset.get("bg.png"));
        bg.setFlip(false, true);
        font = new BitmapFont(true);
    }

    @Override
    public void act(float delta) {
        layer.act(delta);
    }

    public void initGUI() {
        layer.add(new Element("Pause", 135, 13, 52, 29) {
            @Override
            public void tap() {
                menu.gdxGame.stage = menu;
                Gdx.input.setInputProcessor(menu);
            }
        });
    }

    @Override
    public void draw() {
        float cx = cameraX, cy = cameraY;
        getBatch().getTransformMatrix().translate(cx, cy, 0);
        Gdx.gl.glClearColor(0.4f, 0.8f, 0.8f, 1f);//204.204.0
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.draw();
        getBatch().begin();

        bg.setCenterX(135);
        bg.setCenterY(180);
        bg.draw(getBatch());
        layer.draw(getBatch(), 1f);
        getBatch().end();
        getBatch().getTransformMatrix().translate(-cx, -cy, 0);
    }
}
