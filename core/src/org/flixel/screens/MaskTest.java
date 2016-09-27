package org.flixel.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/**
 * Created by tian on 2016/9/27.
 */

public class MaskTest extends BaseScreen {
    private SpriteBatch batch;
    private Texture bg, img, mask;

    @Override
    public void initData() {

    }

    @Override
    public void initUI() {
        Pixmap pixmap = new Pixmap((int)Gdx.graphics.getWidth(),
                (int)Gdx.graphics.getHeight(), Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GRAY);
        pixmap.fill();

        bg = new Texture(pixmap);

        mask = new Texture("mask.png");
        mask.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        img = new Texture(Gdx.files.internal("sprite.png"));
        img.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        batch = new SpriteBatch();
    }

    @Override
    public void update(float dt) {

        batch.begin();
        drawBackground(batch);

        //画遮罩
        drawAlphaMask(batch);

        //画前景色
        drawForeground(batch, 0, 0, mask.getWidth(), mask.getHeight());

        batch.end();
    }

    private void drawForeground(SpriteBatch batch, int clipX, int clipY, int clipWidth, int clipHeight) {
        Gdx.gl.glColorMask(true, true, true, true);
        batch.setBlendFunction(GL20.GL_DST_ALPHA, GL20.GL_ONE_MINUS_DST_ALPHA);

        Gdx.gl.glEnable(GL20.GL_SCISSOR_TEST);
        Gdx.gl.glScissor(clipX, clipY, clipWidth , clipHeight);

        batch.draw(img, 240 - img.getWidth() / 2f, 160 - img.getHeight() / 2f);

        batch.flush();

        Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST);
    }

    private void drawAlphaMask(SpriteBatch batch) {
        Gdx.gl.glColorMask(false, false, false, true);

        //change the blending function for our alpha map
        batch.setBlendFunction(GL20.GL_ONE, GL20.GL_ZERO);

        //draw alpha mask sprite(s)
        batch.draw(mask, 0, 0, mask.getWidth(), mask.getHeight());

        //flush the batch to the GPU
        batch.flush();
    }

    private void drawBackground(SpriteBatch batch) {
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        //... draw background entities/tiles here ...
        batch.draw(bg, 0, 0);

        batch.flush();
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
