package org.flixel.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.glutils.FrameBuffer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array

/**
 * Created by tian on 2016/9/28.
 */

class MashTest01 : BaseScreen(){

    private var mRadius: Float = 0.0f

    private var mThickness: Float = 0.0f

    private var mPoints: Array<Vector2> ? = null;

    private var bIsDragging: Boolean = false

    private var bDrawOutlines: Boolean = false

    private var bDrawConstruction: Boolean = false

    private var mWindowSize: Vector2 = Vector2.Zero

    var mFbo: FrameBuffer? = null;
    var mMask: Texture? = null;
    var mImg: Texture? = null;

    override fun initData() {
        mRadius = 5.0f;
        mThickness = 50.0f;

        mPoints = Array<Vector2>()

        bIsDragging = false;

        //
        bDrawOutlines = true;
        bDrawConstruction = true;

        mWindowSize = Vector2(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())

        mFbo = FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.width, Gdx.graphics.height, true);

        mMask = Texture("mask.png");

        mImg = Texture("sprite.png");

        mFbo!!.begin()

        stage.batch.begin();
        stage.batch.setBlendFunction(GL20.GL_ONE, GL20.GL_ZERO)
        stage.batch.draw(mMask, 0.0f, 0.0f);


        stage.batch.setBlendFunction(GL20.GL_DST_ALPHA, GL20.GL_ONE_MINUS_DST_ALPHA)
        stage.batch.draw(mImg, 0.0f, 0.0f)

        stage.batch.end()

        mFbo!!.end()
    }

    override fun initUI() {

    }

    override fun update(dt: Float) {
        stage.batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
        stage.batch.begin()
        stage.batch.draw(mFbo!!.colorBufferTexture, 0f, 0f)
        stage.batch.end()
    }


}