package org.flixel.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by tian on 2016/11/6.
 */

public class CircleMask extends Actor {
    Texture texture;
    ShapeRenderer shapeRenderer;

    public CircleMask(Texture texture){
        this.texture = texture;
        shapeRenderer = new ShapeRenderer();
        setSize(texture.getWidth(), texture.getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.end();

        Gdx.gl.glClearDepthf(1f);
        Gdx.gl.glClear(GL20.GL_DEPTH_BUFFER_BIT);

        Gdx.gl.glDepthFunc(GL20.GL_LESS);

        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);

        Gdx.gl.glDepthMask(true);
        Gdx.gl.glColorMask(false, false, false, false);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.circle(getX() + getWidth() / 2f, getY() + getWidth() / 2f, getWidth() / 2f);
        shapeRenderer.end();

        batch.begin();

        Gdx.gl.glColorMask(true, true, true, true);
        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
        Gdx.gl.glDepthFunc(GL20.GL_EQUAL);
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
        batch.end();

        batch.begin();
    }
}
