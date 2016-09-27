package org.flixel.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import org.flixel.Flixel

/**
 * Created by tian on 2016/9/28.
 */

object DesktopLauncher {
    @JvmStatic fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration()

        config.width = 960
        config.height = 640

        LwjglApplication(Flixel(), config)
    }
}