package org.flixel

import com.badlogic.gdx.Game
import org.flixel.screens.*

class Flixel : Game() {

    override fun create() {
        //setScreen(new EarserTest());
        //setScreen(new MaskTest());
        //setScreen(MaskHairTest())
        //setScreen(MashTest01())
        //setScreen(MeshLine())
        setScreen(StencilMask())
        //setScreen(PixmapTest())
    }
}
