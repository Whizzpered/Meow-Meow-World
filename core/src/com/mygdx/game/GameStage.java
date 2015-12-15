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
import com.mygdx.game.objects.Entity;
import com.mygdx.game.objects.Room;
import com.mygdx.gui.Element;
import com.mygdx.gui.GUILayer;
import java.util.ArrayList;

/**
 *
 * @author Whizzpered
 */
public class GameStage extends Stage {

    public MenuStage menu;
    private GUILayer layer = new GUILayer();
    private float points, cameraX = - 1880, cameraY = - 7640, bgcamX = 0, bgcamY = 0;
    public float pointCoef = 5000f, lastx, lasty;
    public float gold, food;
    AssetManager asset;
    private TextureAtlas atlas;
    private BitmapFont font;
    private Sprite bg;
    public boolean changeEventColor = false;
    private boolean gameOver = false;
    private ArrayList<Entity> entities = new ArrayList<Entity>(32);
    private Room[][] rooms = new Room[32][64];
    
    public Room[][] getRooms() {
        return rooms;
    }

    public float getCameraX() {
        return cameraX;
    }

    public float getCameraY() {
        return cameraY;
    }

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
                lastx = x;
                lasty = y;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int we) {
                lastx = 0;
                lasty = 0;
                layer.tapHandleCrutch_up(event, x, y, pointer, we);
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                if (cameraX + x - lastx > - 1880-90 && cameraX + x - lastx < - 1880) {
                cameraX += x - lastx;
                bgcamX += (x - lastx) / 2;
                }
                /*if (cameraY + y - lasty > -100 && cameraY + y - lasty < 0) {
                 cameraY += y - lasty;
                 bgcamY += (y - lasty) / 2;
                 }*/

                lastx = x;
                lasty = y;
            }
        });
        addEntity(new Entity(16 * 125, 61 * 125) {
        });
        addRoom(new Room(16, 63));
        addRoom(new Room(16, 62));
    }

    public void addEntity(Entity ent) {
        addActor(ent);
        ent.setSprite();
        entities.add(ent);
    }

    public void addRoom(Room ent) {
        addActor(ent);
        ent.setSprite();
        if (rooms[(int) ent.getX()][(int) ent.getY()] == null) {
            rooms[(int) ent.getX()][(int) ent.getY()] = ent;
        }
    }

    private void initAssets() {
        asset = menu.asset;
        atlas = asset.get("sprites.pack");
        bg = atlas.createSprite("sky");
        bg.setFlip(false, true);
        font = new BitmapFont(true);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        layer.act(delta);
    }

    public void initGUI() {
        layer.add(new Element("Pause", 240, 13, 52, 29) {
            @Override
            public void tap() {
                menu.gdxGame.stage = menu;
                Gdx.input.setInputProcessor(menu);
            }
        });
    }

    @Override
    public void draw() {
        Camera camera = getViewport().getCamera();
        camera.update();
        Gdx.gl.glClearColor(0.4f, 0.8f, 0.8f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        getBatch().begin();
        {
            getBatch().setProjectionMatrix(camera.combined);
            bg.setX(bgcamX);    //setting position of BackGround srpite with paralax
            bg.setY(bgcamY);
            bg.draw(getBatch());

            for (Room[] room : rooms) {
                for (Room r : room) {
                    if (r != null) {
                        r.draw(getBatch(), 1f);
                    }
                }
            }

            for (Entity ent : entities) {
                ent.draw(getBatch(), 1f);
            }
            layer.draw(getBatch(), 1f);
            font.draw(getBatch(), "Gold: " + gold, 5f, 10f);
            font.draw(getBatch(), "Food: " + food, 5f, 25f);
        }
        getBatch().end();
    }
}
