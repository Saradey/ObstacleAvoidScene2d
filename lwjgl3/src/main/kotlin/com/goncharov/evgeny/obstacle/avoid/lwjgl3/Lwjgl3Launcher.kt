@file:JvmName("Lwjgl3Launcher")

package com.goncharov.evgeny.obstacle.avoid.lwjgl3

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.goncharov.evgeny.obstacle.avoid.App
import com.goncharov.evgeny.obstacle.avoid.consts.WINDOW_HEIGHT
import com.goncharov.evgeny.obstacle.avoid.consts.WINDOW_WIDTH

/** Launches the desktop (LWJGL3) application. */
fun main() {
    Lwjgl3Application(App(), Lwjgl3ApplicationConfiguration().apply {
        setTitle("ObstacleAvoidScene2d")
        setWindowedMode(WINDOW_WIDTH, WINDOW_HEIGHT)
        setWindowIcon(*(arrayOf(128, 64, 32, 16).map { "libgdx$it.png" }.toTypedArray()))
        useVsync(true)
        setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate)
        setResizable(false)
    })
}
