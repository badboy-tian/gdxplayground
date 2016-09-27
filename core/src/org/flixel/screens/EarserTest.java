package org.flixel.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by tian on 2016/9/27.
 */

public class EarserTest extends BaseScreen {

    private Texture bg;
    private Texture brush;
    private Texture cachu;
    private SpriteBatch batch;
    private FrameBuffer frameBuffer;

    @Override
    public void initData() {

    }

    @Override
    public void initUI() {
        bg = new Texture("smiley_color.png");
        //笔刷是一张20*20的透明图片
        brush = new Texture("clean_brush.png");

        //需要擦除的图片
        cachu = new Texture(Gdx.files.internal("spa_daoju_jiashi.png"));

        batch = new SpriteBatch();
        //framebuffer
        frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

        //先把被擦除的图片渲染进frameBuffer
        frameBuffer.begin();
        batch.begin();
        batch.draw(cachu, 0, 0);
        batch.end();
        frameBuffer.end();
    }

    Vector2 curr = new Vector2();

    @Override
    public void update(float dt) {

        batch.begin();
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        batch.draw(bg, 0, Gdx.graphics.getHeight() - bg.getHeight());
        batch.draw(frameBuffer.getColorBufferTexture(), 0, 0);
        batch.end();

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        curr.set(screenX, screenY);

        frameBuffer.begin();

        batch.begin();
        batch.setBlendFunction(GL20.GL_ZERO, GL20.GL_ONE_MINUS_SRC_ALPHA);
        batch.enableBlending();
        batch.draw(brush, curr.x, curr.y);
        batch.end();

        frameBuffer.end();

        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
