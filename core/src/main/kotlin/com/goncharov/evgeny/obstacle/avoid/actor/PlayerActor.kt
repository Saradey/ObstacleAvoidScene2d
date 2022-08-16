package com.goncharov.evgeny.obstacle.avoid.actor

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.MathUtils
import com.goncharov.evgeny.obstacle.avoid.consts.MAX_PLAYER_X_SPEED
import com.goncharov.evgeny.obstacle.avoid.consts.PLAYER_BOUNDS_RADIUS
import com.goncharov.evgeny.obstacle.avoid.consts.PLAYER_SIZE
import com.goncharov.evgeny.obstacle.avoid.consts.WORLD_WIDTH

class PlayerActor : ActorBase() {

    init {
        setCollisionRadius(PLAYER_BOUNDS_RADIUS)
        setSize(PLAYER_SIZE, PLAYER_SIZE)
    }

    override fun act(delta: Float) {
        super.act(delta)
        update()
    }

    private fun update() {
        var xSpeed = 0f
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            xSpeed = MAX_PLAYER_X_SPEED
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            xSpeed = -MAX_PLAYER_X_SPEED
        }
        x += xSpeed
        blockPlayerFromLeavingTheWorld()
    }

    private fun blockPlayerFromLeavingTheWorld() {
        val playerX = MathUtils.clamp(x, 0f, WORLD_WIDTH - width)
        setPosition(playerX, y)
    }
}