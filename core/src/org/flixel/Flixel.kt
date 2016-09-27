package org.flixel

import com.badlogic.gdx.Game

import org.flixel.screens.MaskHairTest

class Flixel : Game() {

    override fun create() {
        //setScreen(new EarserTest());
        //setScreen(new MaskTest());
        setScreen(MaskHairTest())
    }

}
