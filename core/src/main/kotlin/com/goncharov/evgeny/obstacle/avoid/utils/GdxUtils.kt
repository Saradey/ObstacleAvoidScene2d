package com.goncharov.evgeny.obstacle.avoid.utils

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20

object GdxUtils {

    fun clearScreen() {
        Gdx.gl.glClearColor(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, Color.BLACK.a)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
    }
}