package org.flixel.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.FrameBuffer
import com.badlogic.gdx.math.Vector2

/**
 * Created by tian on 2016/9/27.
 */

class EarserTest : BaseScreen() {

    private var bg: Texture? = null
    private var brush: Texture? = null
    private var cachu: Texture? = null
    private var batch: SpriteBatch? = null
    private var frameBuffer: FrameBuffer? = null

    override fun initData() {

    }

    override fun initUI() {
        bg = Texture("smiley_color.png")
        //笔刷是一张20*20的透明图片
        brush = Texture("clean_brush.png")

        //需要擦除的图片
        cachu = Texture(Gdx.files.internal("spa_daoju_jiashi.png"))

        batch = SpriteBatch()
        //framebuffer
        frameBuffer = FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.width, Gdx.graphics.height, true)

        //先把被擦除的图片渲染进frameBuffer
        frameBuffer!!.begin()
        batch!!.begin()
        batch!!.draw(cachu!!, 0f, 0f)
        batch!!.end()
        frameBuffer!!.end()
    }

    internal var curr = Vector2()

    override fun update(dt: Float) {

        batch!!.begin()
        batch!!.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
        batch!!.draw(bg!!, 0f, (Gdx.graphics.height - bg!!.height).toFloat())
        batch!!.draw(frameBuffer!!.colorBufferTexture, 0f, 0f)
        batch!!.end()

    }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
        curr.set(screenX.toFloat(), screenY.toFloat())

        frameBuffer!!.begin()

        batch!!.begin()
        batch!!.setBlendFunction(GL20.GL_ZERO, GL20.GL_ONE_MINUS_SRC_ALPHA)
        batch!!.enableBlending()
        batch!!.draw(brush!!, curr.x, curr.y)
        batch!!.end()

        frameBuffer!!.end()

        return false
    }
}
