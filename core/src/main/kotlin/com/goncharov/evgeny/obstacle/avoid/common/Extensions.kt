package com.goncharov.evgeny.obstacle.avoid.common

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener

inline fun Actor.addListenerKtx(crossinline action: () -> Unit) {
    this.addListener(object : ChangeListener() {
        override fun changed(event: ChangeEvent?, actor: Actor?) {
            action()
        }
    })
}