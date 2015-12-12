package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import java.util.Random;

public class MyGdxGame extends ApplicationAdapter {

    public static final Random RANDOM = new Random();
    public Stage stage;
    public StretchViewport vp;
    private MenuStage menu;

    @Override
    public void create() {
        OrthographicCamera cam = new OrthographicCamera();
        cam.setToOrtho(true);
        vp = new StretchViewport(270, 360, cam);
        menu = new MenuStage(vp, this);
        stage = menu;
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render() {
        stage.draw();
        stage.act();
    }

    @Override
    public void dispose() {
        stage.dispose();
        super.dispose();
    }
}
