package org.flixel.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Disposable

/**
 * Created by tian on 2016/9/27.
 */

class MaskHairTest : BaseScreen() {
    private var batch: SpriteBatch? = null
    private val bg: Texture? = null
    private var img: Texture? = null
    private var mask: Texture? = null

    private var maskPixmap: Pixmap? = null

    internal var drawablePixmap: DrawablePixmap? = null

    override fun initData() {

    }

    override fun initUI() {
        val pixmap = Pixmap(Gdx.graphics.width.toInt(),
                Gdx.graphics.height.toInt(), Pixmap.Format.RGBA8888)
        pixmap.setColor(Color.GRAY)
        pixmap.fill()

        //bg = new Texture(pixmap);


        maskPixmap = Pixmap(Gdx.graphics.width, Gdx.graphics.height, Pixmap.Format.Alpha)
        //mask = new Texture("mask.png");
        mask = Texture(maskPixmap)
        mask!!.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)

        img = Texture(Gdx.files.internal("sprite.png"))
        img!!.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)

        batch = SpriteBatch()


        drawablePixmap = DrawablePixmap(mask!!)

        inputMultiplexer.addProcessor(DrawingInput())
    }

    override fun update(dt: Float) {

        batch!!.begin()
        drawBackground(batch!!)

        //画遮罩
        drawAlphaMask(batch!!)

        //画前景色
        drawForeground(batch!!, 0, 0, mask!!.width, mask!!.height)

        drawablePixmap!!.update()

        batch!!.end()
    }

    private fun drawForeground(batch: SpriteBatch, clipX: Int, clipY: Int, clipWidth: Int, clipHeight: Int) {
        Gdx.gl.glColorMask(true, true, true, true)
        batch.setBlendFunction(GL20.GL_DST_ALPHA, GL20.GL_ONE_MINUS_DST_ALPHA)

        Gdx.gl.glEnable(GL20.GL_SCISSOR_TEST)
        Gdx.gl.glScissor(clipX, clipY, clipWidth, clipHeight)

        batch.draw(img!!, 0.0f, 0.0f)

        batch.flush()

        Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST)
    }

    private fun drawAlphaMask(batch: SpriteBatch) {
        Gdx.gl.glColorMask(false, false, false, true)

        //change the blending function for our alpha map
        batch.setBlendFunction(GL20.GL_ONE, GL20.GL_ZERO)

        //draw alpha mask sprite(s)
        batch.draw(mask, 0f, 0f, mask!!.width.toFloat(), mask!!.height.toFloat())

        //flush the batch to the GPU
        batch.flush()
    }

    private fun drawBackground(batch: SpriteBatch) {
        batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
        //... draw background entities/tiles here ...
        //batch.draw(bg, 0, 0);

        batch.flush()
    }


    class DrawablePixmap(private val texture: Texture) : Disposable {

        private val brushSize = 20
        private val clearColor = Color(0f, 0f, 0f, 0f)
        private val drawColor = Color(1f, 1f, 1f, 1f)

        private val pixmap: Pixmap
        private var dirty: Boolean = false

        init {
            this.pixmap = Pixmap(texture.width, texture.height, Pixmap.Format.Alpha)
            pixmap.setColor(drawColor)
            this.dirty = false
        }

        fun update() {
            if (dirty) {
                texture.draw(pixmap, 0, 0)
                dirty = false
            }
        }

        fun clear() {
            pixmap.setColor(clearColor)
            pixmap.fill()
            pixmap.setColor(drawColor)
            dirty = true
        }

        private fun drawDot(spot: Vector2) {
            pixmap.fillCircle(spot.x.toInt(), spot.y.toInt(), brushSize)
        }

        fun draw(spot: Vector2) {
            drawDot(spot)
            dirty = true
        }

        fun drawLerped(from: Vector2, to: Vector2) {
            val dist = to.dst(from)
            /* Calc an alpha step to put one dot roughly every 1/8 of the brush
			 * radius. 1/8 is arbitrary, but the results are fairly nice. */
            val alphaStep = brushSize / (8f * dist)

            var a = 0f
            while (a < 1f) {
                val lerped = from.lerp(to, a)
                drawDot(lerped)
                a += alphaStep
            }

            drawDot(to)
            dirty = true
        }

        override fun dispose() {
            texture.dispose()
            pixmap.dispose()
        }
    }

    private inner class DrawingInput : InputAdapter() {

        private var last: Vector2? = null
        private var leftDown = false

        override fun touchDown(screenX: Int, screenY: Int, pointer: Int,
                               button: Int): Boolean {
            if (button == Input.Buttons.LEFT) {
                val curr = Vector2(screenX.toFloat(), screenY.toFloat())
                drawablePixmap!!.draw(curr)
                last = curr
                leftDown = true
                return true
            } else {
                return false
            }
        }

        override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean {
            if (leftDown) {
                val curr = Vector2(screenX.toFloat(), screenY.toFloat())
                drawablePixmap!!.drawLerped(last!!, curr)
                last = curr
                return true
            } else {
                return false
            }
        }

        override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
            if (button == Input.Buttons.LEFT) {
                drawablePixmap!!.draw(Vector2(screenX.toFloat(), screenY.toFloat()))
                last = null
                leftDown = false
                return true
            } else {
                return false
            }
        }

        override fun keyDown(keycode: Int): Boolean {
            when (keycode) {
                Input.Keys.ESCAPE, Input.Keys.F5 -> return true
                else -> return false
            }
        }

        override fun keyUp(keycode: Int): Boolean {
            when (keycode) {
                Input.Keys.ESCAPE -> {
                    Gdx.app.exit()
                    return true
                }

                Input.Keys.F5 -> {
                    drawablePixmap!!.clear()
                    return true
                }
                else -> return false
            }
        }
    }
}
