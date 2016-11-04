package org.flixel.view;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

import net.mwplay.nativefont.NativeFont;
import net.mwplay.nativefont.NativeLabel;

/**
 * 功能:跑马灯效果
 * Created by tian on 2016/11/4.
 */

public class MyScrollLabel extends Actor {
    private NativeFont nativeFont;
    private NativeLabel label;
    private String msg = "";

    private ShapeRenderer  shapeRenderer = new ShapeRenderer();

    private float v = 4;

    public MyScrollLabel(String msg, NativeFont nativeFont) {
        this.nativeFont = nativeFont;
        this.msg = msg;

        label = new NativeLabel(msg, nativeFont);

        setSize(label.getWidth(), label.getHeight());
    }

    /**
     * 设置字
     * @param msg
     */
    public void setText(String msg){
        this.msg = msg;
        label.setText(msg);
    }

    /**
     * 设置速度
     * @param v
     */
    public void setV(float v){
        this.v = v;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (label != null) {
            if (label.getRight() > getX()) {
                label.setX(label.getX() - v);
                label.setY(getY());
            }else {
                label.setX(getRight());
            }

            batch.flush();
            if (clipBegin(getX(), getY(), getWidth(), getHeight())){
                super.draw(batch, parentAlpha);
                label.draw(batch, parentAlpha);
                batch.flush();

                clipEnd();
            }
        }
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        label.setPosition(x, y);
    }

    @Override
    public void setPosition(float x, float y, int alignment) {
        super.setPosition(x, y, alignment);
        label.setPosition(x, y, alignment);
    }

    @Override
    public void setOrigin(int alignment) {
        super.setOrigin(alignment);
        label.setOrigin(alignment);
    }

    @Override
    public void setScale(float scaleX, float scaleY) {
        super.setScale(scaleX, scaleY);
        label.setScale(scaleX, scaleY);
    }

    @Override
    public void setScale(float scaleXY) {
        super.setScale(scaleXY);
        label.setScale(scaleXY);
    }

    @Override
    public void setScaleX(float scaleX) {
        super.setScaleX(scaleX);
        label.setScaleX(scaleX);
    }

    @Override
    public void setScaleY(float scaleY) {
        super.setScaleY(scaleY);
        label.setScaleY(scaleY);
    }
}
