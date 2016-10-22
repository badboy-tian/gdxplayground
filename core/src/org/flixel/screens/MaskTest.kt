package org.flixel.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch

/**
 * Created by tian on 2016/9/27.
 */

class MaskTest : BaseScreen() {
    private var batch: SpriteBatch? = null
    private var bg: Texture? = null
    private var img: Texture? = null
    private var mask: Texture? = null
    private var font: BitmapFont? = null

    override fun initData() {
        font = BitmapFont()
    }

    override fun initUI() {
        val pixmap = Pixmap(Gdx.graphics.width.toInt(),
                Gdx.graphics.height.toInt(), Pixmap.Format.RGBA8888)
        pixmap.setColor(Color.GRAY)
        pixmap.fill()

        bg = Texture(pixmap)

        mask = Texture("mask.png")
        mask!!.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)

        img = Texture(Gdx.files.internal("sprite.png"))
        img!!.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)

        batch = SpriteBatch()
    }

    override fun update(dt: Float) {
        if (batch == null)
             return;

        batch!!.begin()
        drawBackground(batch!!)

        //画遮罩
        drawAlphaMask(batch!!)

        //画前景色
        drawForeground(batch!!, 0, 0, mask!!.width, mask!!.height)

        batch!!.end()
    }

    private fun drawForeground(batchPara: SpriteBatch, clipX: Int, clipY: Int, clipWidth: Int, clipHeight: Int) {
        Gdx.gl.glColorMask(true, true, true, true)
        batchPara.setBlendFunction(GL20.GL_DST_ALPHA, GL20.GL_ONE_MINUS_DST_ALPHA)

        Gdx.gl.glEnable(GL20.GL_SCISSOR_TEST)
        Gdx.gl.glScissor(clipX, clipY, clipWidth, clipHeight)

        batchPara.draw(img!!, 240 - img!!.width / 2f, 160 - img!!.height / 2f)

        batchPara.flush()

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
        batch.draw(bg!!, 0f, 0f)

        batch.flush()
    }
}
