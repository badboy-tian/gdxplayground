package org.flixel.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by tian on 2016/10/10.
 */

public class EraserActor extends Image {
    //our mask texture
    private Texture mask;

    private DrawablePixmap drawblePixmap;
    //private boolean isClear = false;
    private float scale = 1;

    public EraserActor(Texture hair, float scale) {
        this.scale = scale;

        mask = hair;
        mask.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        mask.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        setSize(hair.getWidth(), hair.getHeight());

        drawblePixmap = new DrawablePixmap(mask);

        initListener();
    }

    public void setClear(boolean isClear) {
        //this.isClear = isClear;
    }

    private Vector2 last = null;

    private void initListener() {
        addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Vector2 curr = new Vector2(x, getHeight() - y);
                drawblePixmap.draw(curr.cpy());
                last = curr;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                Vector2 curr = new Vector2(x, getHeight() - y);
                drawblePixmap.draw(curr.cpy());
                last = null;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                super.touchDragged(event, x, y, pointer);
                Vector2 curr = new Vector2(x, getHeight() - y);
                drawblePixmap.drawLerped(last.cpy(), curr.cpy());
                last.set(curr);
            }
        });
    }

    public void maskDown(float x, float y) {
        Vector2 pos = new Vector2(x, y);
        pos.set(screenToLocalCoordinates(pos.cpy()));

        drawblePixmap.draw(pos.cpy());
        last = pos;
    }

    public void maskDrag(float x, float y) {
        Vector2 pos = new Vector2(x * scale, y);
        pos.set(screenToLocalCoordinates(pos.cpy()));

        Vector2 curr = new Vector2(x, getHeight() - y);
        drawblePixmap.drawLerped(last.cpy(), curr.cpy());
        last.set(curr);
    }

    public void maskUp(float x, float y) {
        Vector2 pos = new Vector2(x, y);
        pos.set(screenToLocalCoordinates(pos.cpy()));

        drawblePixmap.draw(pos.cpy());
        last = null;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        drawblePixmap.update();

        batch.draw(mask, getX(), getY(), getWidth(), getHeight());
    }

    public void clearMask() {
        if (drawblePixmap != null) {
            drawblePixmap.clearMask();
        }
    }

    public void fileMask() {
        if (drawblePixmap != null) {
            drawblePixmap.fillMask();
        }
    }

    private class DrawablePixmap implements Disposable {
        private float brushSize = 50;
        private Color clearColor = new Color(0f, 0f, 0f, 0f);
        private Color drawColor = new Color(1f, 1f, 1f, 1f);

        private Pixmap pixmap;
        private boolean dirty = false;
        Texture texture;

        public DrawablePixmap(Texture texture) {
            this.texture = texture;
            texture.getTextureData().prepare();
            this.pixmap = texture.getTextureData().consumePixmap();//new Pixmap(texture.getWidth(), texture
            // .getHeight(), Pixmap
            // .Format
                    //.RGBA8888);
            pixmap.setColor(drawColor);
            this.dirty = false;
        }

        public void update() {
            if (dirty) {
                //texture.draw(pixmap, 0, 0);
                dirty = false;
            }
        }

        public void clear() {
            pixmap.setColor(clearColor);
            pixmap.fill();
            pixmap.setColor(drawColor);
            dirty = true;
        }

        public void eraseCircle(int x, int y, int radius, Pixmap pixmap, Texture texture) {
            if (pixmap != null) {
                Pixmap.Blending blending = Pixmap.getBlending();
                pixmap.setColor(0, 0, 0, 0);
                Pixmap.setBlending(Pixmap.Blending.None);

                pixmap.fillCircle(x, y, radius);
                Pixmap.setBlending(blending);

                Gdx.gl.glBindTexture(GL20.GL_TEXTURE_2D, texture.getTextureObjectHandle());
                Gdx.gl.glPixelStorei(GL20.GL_UNPACK_ALIGNMENT, 1);
                Gdx.gl.glTexImage2D(GL20.GL_TEXTURE_2D, 0, pixmap.getGLInternalFormat(), pixmap.getWidth(), pixmap.getHeight(), 0,
                        pixmap.getGLFormat(), pixmap.getGLType(), pixmap.getPixels());
            }
        }

        private void drawDot(Vector2 spot) {
            eraseCircle((int) spot.x, (int) spot.y, (int) brushSize, pixmap, texture);
        }

        private void clearMask() {
            eraseCircle((int) (pixmap.getWidth() / 2f), (int) (pixmap.getHeight() / 2f), (int) (pixmap.getHeight() / 2f), pixmap, texture);
        }

        private void fillMask() {
            //isClear = true;
            pixmap.setColor(drawColor);
            pixmap.fillCircle((int) (pixmap.getWidth() / 2f), (int) (pixmap.getHeight() / 2f), (int) (pixmap.getHeight() / 2f));
            dirty = true;
        }

        public void draw(Vector2 spot) {
            drawDot(spot);
            dirty = true;
        }

        public void drawLerped(Vector2 from, Vector2 to) {
            float dist = to.dst(from);
            //计算一个alpha步骤，以大约每1/8刷子半径放一个点。 1/8是任意的，但结果是相当不错的。
            float alphaStep = brushSize / (8f * dist);

            float a = 0f;
            while (a < 1f) {
                Vector2 lerped = from.lerp(to, a);
                drawDot(lerped);
                a += alphaStep;
            }

            drawDot(to);
            dirty = true;
        }

        @Override
        public void dispose() {
            texture.dispose();
            pixmap.dispose();
        }
    }
}
