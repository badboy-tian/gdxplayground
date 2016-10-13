package org.flixel

import com.badlogic.gdx.Game
import org.flixel.screens.MashTest01
import org.flixel.screens.MaskHairTest
import org.flixel.screens.MeshLine
import org.flixel.screens.StencilMask

class Flixel : Game() {

    override fun create() {
        //setScreen(new EarserTest());
        //setScreen(new MaskTest());
        //setScreen(MaskHairTest())
        //setScreen(MashTest01())
        //setScreen(MeshLine())
        setScreen(StencilMask())
    }
}
