package com.goncharov.evgeny.obstacle.avoid.utils

import com.badlogic.gdx.Gdx

object LogDebugUtils {

    fun debug(tag: String, message: String) {
        Gdx.app.debug(tag, message)
    }
}