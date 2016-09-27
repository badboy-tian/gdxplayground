package org.flixel.screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.StretchViewport

/**
 * Created by tian on 2016/9/27.
 */

abstract class BaseScreen : SuperScreen() {
    protected var stage: Stage
    protected var inputMultiplexer: InputMultiplexer

    init {
        stage = Stage(StretchViewport(WORLD_WIDTH.toFloat(), WORLD_HEIGHT.toFloat()))

        inputMultiplexer = InputMultiplexer()
        inputMultiplexer.addProcessor(stage)
        inputMultiplexer.addProcessor(this)

        Gdx.input.inputProcessor = inputMultiplexer

        initUI()
    }

    override fun show() {
        super.show()
        initData()
    }

    override fun render(delta: Float) {
        super.render(delta)

        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        stage.act(delta)
        stage.draw()
        update(delta)
    }

    abstract fun initData()
    abstract fun initUI()
    abstract fun update(dt: Float)

    override fun resize(width: Int, height: Int) {
        super.resize(width, height)
        stage.viewport.update(width, height)
    }

    companion object {
        val WORLD_HEIGHT = 640
        val WORLD_WIDTH = 960
    }
}
