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
import static com.mygdx.game.Condition.*;
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
    private float points, cameraX = - 1880, cameraY = - 7640, bgcamX = 0, bgcamY = 0, lcamX = 0, lcamY = 0;
    public float pointCoef = 5000f, lastx, lasty;
    public float gold, food;
    AssetManager asset;
    private TextureAtlas atlas;
    private BitmapFont font;
    private Sprite bg, trees;
    public boolean changeEventColor = false;
    private boolean gameOver = false;
    private ArrayList<Entity> entities = new ArrayList<Entity>(32);
    private Room[][] rooms = new Room[32][64];
    private Room buildHand;
    private float buildx, buildy;
    private float buildDeltaX, buildDeltaY;
    private Condition condition = USUALLY;

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

    public Room getRoom(int x, int y) {
        if (x >= 0 && x < 32 && y < 64 && y >= 0) {
            return (getRooms()[x][y]);
        } else {
            return null;
        }
    }

    public void initialize() {
        initAssets();
        initGUI();
        addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int we) {
                lastx = x;
                lasty = y;
                if (condition == BUILDING && Math.abs(x - buildx) < buildHand.getWidth() / 2
                        && Math.abs(y - buildy) < buildHand.getHeight() / 2) {
                    buildDeltaX = buildx - x;
                    buildDeltaY = buildy - y;
                }
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int we) {
                lastx = 0;
                lasty = 0;
                layer.tapHandleCrutch_up(event, x, y, pointer, we);
                if (condition == BUILDING) {
                    buildx = (int) ((buildx-cameraX) / 125) * 125 + cameraX;
                    buildy = (int) ((buildy-cameraY) / 125) * 125 + cameraY;
                    buildHand.setX((int) ((buildx-cameraX) / 125));
                    buildHand.setY((int) ((buildy-cameraY) / 125));
                    if(addRoom(buildHand))condition = USUALLY;
                }
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {

                if (condition == BUILDING && Math.abs(x - buildx) < buildHand.getWidth() / 2
                        && Math.abs(y - buildy) < buildHand.getHeight() / 2) {
                    buildx = x + buildDeltaX;
                    buildy = y + buildDeltaY;
                } else {
                    if (cameraX + x - lastx > - 1880 - 150 && cameraX + x - lastx < - 1880 + 120) {
                        cameraX += x - lastx;
                        bgcamX += (x - lastx) / 3;
                        lcamX += (x - lastx) / 2;
                    }
                    if (cameraY + y - lasty > - 7640 && cameraY + y - lasty < - 7640 + 100) {
                        cameraY += y - lasty;
                        bgcamY += (y - lasty) / 2;
                    }

                    lastx = x;
                    lasty = y;
                }
            }
        });
        addEntity(new Entity(16 * 125, 61 * 125) {
        });
        addRoom(new Room(16, 63));
        addRoom(new Room(16, 62));
        addRoom(new Room(15, 62));
        addRoom(new Room(17, 62));
        addRoom(new Room(15, 63));
        addRoom(new Room(17, 63));
    }

    public void addEntity(Entity ent) {
        addActor(ent);
        ent.setSprite();
        entities.add(ent);
    }

    public boolean addRoom(Room ent) {
        boolean ass=false;
        addActor(ent);
        ent.setSprite();
        if (rooms[(int) ent.getX()][(int) ent.getY()] == null) {
            rooms[(int) ent.getX()][(int) ent.getY()] = ent;
            if(ent==buildHand){
                buildHand.getSprite().setAlpha(2f);
                buildHand = null;
            }
            ass = true;
        }
        for (Room[] room : rooms) {
            for (Room r : room) {
                if (r != null) {
                    r.setWalls();
                }
            }
        }
        return ass;
    }

    private void initAssets() {
        asset = menu.asset;
        atlas = asset.get("sprites.pack");
        bg = atlas.createSprite("sky");
        bg.setFlip(false, true);
        trees = atlas.createSprite("layer");
        trees.setFlip(false, true);
        font = new BitmapFont(true);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        layer.act(delta);
    }

    public void initGUI() {
        layer.add(new Element("Options", 240, 13, 52, 29) {
            @Override
            public void tap() {
                menu.gdxGame.stage = menu;
                Gdx.input.setInputProcessor(menu);
            }
        });
        layer.add(new Element("build", 240, 53, 52, 29) {
            @Override
            public void tap() {
                condition = BUILDING;
                buildHand = new Room(0, 0);
                addActor(buildHand);
                buildx = getViewport().getCamera().viewportWidth/2;
                buildy = getViewport().getCamera().viewportHeight/2;
                buildHand.setSprite();
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
            bg.setPosition(0, 0);
            bg.draw(getBatch());
            trees.setPosition(lcamX, lcamY);
            trees.draw(getBatch());

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
            if (buildHand != null) {
                buildHand.getSprite().setAlpha(0.5f);
                buildHand.getSprite().setCenter(buildx, buildy);
                buildHand.getSprite().draw(getBatch());
            }
        }
        getBatch().end();
    }
}
