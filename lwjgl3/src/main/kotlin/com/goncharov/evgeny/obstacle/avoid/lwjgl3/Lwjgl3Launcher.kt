@file:JvmName("Lwjgl3Launcher")

package com.goncharov.evgeny.obstacle.avoid.lwjgl3

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.goncharov.evgeny.obstacle.avoid.App

/** Launches the desktop (LWJGL3) application. */
fun main() {
    Lwjgl3Application(App(), Lwjgl3ApplicationConfiguration().apply {
        setTitle("ObstacleAvoidScene2d")
        setWindowedMode(640, 480)
        setWindowIcon(*(arrayOf(128, 64, 32, 16).map { "libgdx$it.png" }.toTypedArray()))
    })
}
