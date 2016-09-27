package org.flixel.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * Created by tian on 2016/9/27.
 */

public abstract class BaseScreen extends ScreenAdapter implements InputProcessor{
    public static final int WORLD_HEIGHT = 640;
    public static final int WORLD_WIDTH = 960;
    protected Stage stage;

    public BaseScreen() {
        stage = new Stage(new StretchViewport(WORLD_WIDTH, WORLD_HEIGHT));

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(this);

        Gdx.input.setInputProcessor(inputMultiplexer);

        initUI();
    }

    @Override
    public void show() {
        super.show();
        initData();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
        update(delta);
    }

    public abstract void initData();
    public abstract void initUI();
    public abstract void update(float dt);

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        stage.getViewport().update(width, height);
    }
}
