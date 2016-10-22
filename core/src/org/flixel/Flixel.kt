package org.flixel

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import org.flixel.levelhelper.Utilities.LHUtils
import org.flixel.screens.LHTest
import org.flixel.screens.StencilMask
import org.flixel.stagebuilder.SBuilder

class Flixel : Game() {

    override fun create() {
        //setScreen(new EarserTest());
        //setScreen(new MaskTest());
        //setScreen(MaskHairTest())
        //setScreen(MashTest01())
        //setScreen(MeshLine())
        setScreen(StencilMask())
        //setScreen(PixmapTest())
        //setScreen(TestCocos())
        //setScreen(LHTest())
        //setScreen(SBuilder())

        /*Gdx.app.log("", LHUtils.filenameExtension("Assets/image.png"))
        Gdx.app.log("", LHUtils.stripExtension("Assets/image.png"))
        Gdx.app.log("", LHUtils.getPathFromFilename("Assets/image.png"))
        Gdx.app.log("", LHUtils.getFileFromFilename("Assets/image.png"))
        Gdx.app.log("", LHUtils.replaceOccuranceOfStringWithString("Assets/image.png", "png", "JPG"))
        Gdx.app.log("", LHUtils.pointFromString("{500, 400}").toString())
        Gdx.app.log("", LHUtils.rectFromString("{{500, 400},{100,100}}").toString())*/
    }
}
