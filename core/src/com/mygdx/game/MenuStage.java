/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.gui.Element;
import com.mygdx.gui.GUILayer;
import com.mygdx.gui.GUIUtils;

/**
 *
 * @author Whizzpered
 */
public class MenuStage extends Stage {

    private GameStage gameStage;
    public MyGdxGame gdxGame;
    private MenuStage thisClass = this;
    private GUILayer layer = new GUILayer();
    AssetManager asset;
    private TextureAtlas atlas;
    private BitmapFont font;
    private Texture logoTexture;

    public BitmapFont getFont() {
        return font;
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    public MenuStage(Viewport vp, MyGdxGame game) {
        super(vp);
        this.gdxGame = game;
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
        });
    }

    private void initAssets() {
        asset = new AssetManager();
        asset.load("gui.pack", TextureAtlas.class);
        asset.load("sprites.pack", TextureAtlas.class);
        asset.finishLoading();
        GUIUtils.GUI_ATLAS = asset.get("gui.pack");
        font = new BitmapFont(true);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        layer.act(delta);
    }

    public void initGUI() {
        layer.add(new Element("Start", 130, 150, 104, 58) {
            @Override
            public void tap() {
                gameStage = new GameStage(getViewport(), thisClass);
                Gdx.input.setInputProcessor(gameStage);
                gdxGame.stage = gameStage;
            }
        });
        layer.add(new Element("Exit", 130, 210, 104, 58) {
            @Override
            public void tap() {
                System.exit(0);
            }
        });
    }

    @Override
    public void draw() {
        Gdx.gl.glClearColor(0f, 0.3f, 0.3f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.draw();
        getBatch().begin();
        layer.draw(getBatch(), 1);
        getBatch().end();
    }

}
