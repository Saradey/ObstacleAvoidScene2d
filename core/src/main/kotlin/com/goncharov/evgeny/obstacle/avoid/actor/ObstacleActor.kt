package com.goncharov.evgeny.obstacle.avoid.actor

import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.utils.Pool
import com.goncharov.evgeny.obstacle.avoid.consts.MEDIUM_OBSTACLE_SPEED
import com.goncharov.evgeny.obstacle.avoid.consts.OBSTACLE_BOUNDS_RADIUS
import com.goncharov.evgeny.obstacle.avoid.consts.OBSTACLE_SIZE

class ObstacleActor : ActorBase(), Pool.Poolable {

    private var ySpeed = MEDIUM_OBSTACLE_SPEED
    private var hit = false

    init {
        setCollisionRadius(OBSTACLE_BOUNDS_RADIUS)
        setSize(OBSTACLE_SIZE, OBSTACLE_SIZE)
    }

    override fun act(delta: Float) {
        super.act(delta)
        update()
    }

    private fun update() {
        y -= y - ySpeed
    }

    fun setYSpeed(ySpeed: Float) {
        this.ySpeed = ySpeed
    }

    fun isPlayerColliding(player: PlayerActor): Boolean {
        val playerBounds = player.getCollisionShape()
        val overlaps = Intersector.overlaps(playerBounds, getCollisionShape())
        hit = overlaps
        return overlaps
    }

    fun isNotHit(): Boolean {
        return !hit
    }

    override fun reset() {
        hit = false
    }
}