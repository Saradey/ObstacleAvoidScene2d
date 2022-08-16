package com.goncharov.evgeny.obstacle.avoid.actor

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.scenes.scene2d.Actor

abstract class ActorBase : Actor() {

    private val circleCollision = Circle()
    private var textureRegion: TextureRegion? = TextureRegion()

    fun setCollisionRadius(radius: Float) {
        circleCollision.setRadius(radius)
    }

    fun setRegion(textureRegion: TextureRegion?) {
        this.textureRegion = textureRegion
    }

    override fun draw(batch: Batch?, parentAlpha: Float) {
        if (textureRegion == null) return
        batch?.draw(
            textureRegion,
            x, y,
            originX, originY,
            width, height,
            scaleX, scaleY,
            rotation
        )
    }

    override fun drawDebugBounds(shapes: ShapeRenderer) {
        if (shapes.color != Color.RED) shapes.color = Color.RED
        shapes.x(circleCollision.x, circleCollision.y, 0.1f)
        shapes.circle(circleCollision.x, circleCollision.y, circleCollision.radius, 30)
    }

    override fun positionChanged() {
        updateCollisionShape()
    }

    override fun sizeChanged() {
        updateCollisionShape()
    }

    fun getCollisionShape(): Circle {
        return circleCollision
    }

    private fun updateCollisionShape() {
        val halfWidth = width / 2f
        val halfHeight = height / 2f
        circleCollision.setPosition(x + halfWidth, y + halfHeight)
    }
}