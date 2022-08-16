package com.goncharov.evgeny.obstacle.avoid.screens.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.Pools
import com.badlogic.gdx.utils.viewport.FitViewport
import com.goncharov.evgeny.obstacle.avoid.actor.ObstacleActor
import com.goncharov.evgeny.obstacle.avoid.actor.PlayerActor
import com.goncharov.evgeny.obstacle.avoid.common.BaseScreen
import com.goncharov.evgeny.obstacle.avoid.consts.*
import com.goncharov.evgeny.obstacle.avoid.consts.AssetDescriptors.FONT_DESCRIPTOR
import com.goncharov.evgeny.obstacle.avoid.consts.AssetDescriptors.GAME_PLAY_DESCRIPTOR
import com.goncharov.evgeny.obstacle.avoid.consts.AssetDescriptors.HIT_SOUND_DESCRIPTOR
import com.goncharov.evgeny.obstacle.avoid.managers.GameManager
import com.goncharov.evgeny.obstacle.avoid.navigation.KeyNavigation
import com.goncharov.evgeny.obstacle.avoid.navigation.Navigation
import com.goncharov.evgeny.obstacle.avoid.utils.GdxUtils
import kotlin.math.min

class GameScreen(
    private val navigator: Navigation,
    assetManager: AssetManager,
    private val batch: SpriteBatch,
    private val gameManager: GameManager
) : BaseScreen() {

    private val layout = GlyphLayout()
    private val camera = OrthographicCamera()
    private val viewport = FitViewport(WORLD_WIDTH, WORLD_HEIGHT)
    private val stage = Stage(viewport, batch)
    private val uiCamera = OrthographicCamera()
    private val uiViewport = FitViewport(UI_WIDTH, UI_HEIGHT)
    private val font = assetManager[FONT_DESCRIPTOR]
    private var obstacleTimer = 0f
    private var scoreTimer = 0f
    private var lives = LIVES_START
    private var score = 0
    private var displayScore = 0
    private val hitSound = assetManager[HIT_SOUND_DESCRIPTOR]
    private val startPlayerX = (WORLD_WIDTH - PLAYER_SIZE) / 2f
    private val startPlayerY = PLAYER_SIZE / 2f
    private val gameAtlas = assetManager[GAME_PLAY_DESCRIPTOR]
    private val obstacleRegion = gameAtlas.findRegion(OBSTACLE)
    private val backgroundRegion = gameAtlas.findRegion(BACKGROUND)
    private val playerActor = PlayerActor()
    private val obstacles = Array<ObstacleActor>()
    private val obstaclePool = Pools.get(ObstacleActor::class.java)

    override fun show() {
        stage.isDebugAll = true
        val background = Image(backgroundRegion)
        background.setSize(WORLD_WIDTH, WORLD_HEIGHT)
        stage.addActor(background)
        playerActor.setPosition(startPlayerX, startPlayerY)
        playerActor.setRegion(gameAtlas.findRegion(PLAYER))
        stage.addActor(playerActor)
    }

    override fun render(delta: Float) {
        update(delta)
        GdxUtils.clearScreen()
        viewport.apply()
        renderGamePlay()
        uiViewport.apply()
        renderUi()
        viewport.apply()
        if (isGameOver()) {
            navigator.navigate(KeyNavigation.MenuKey)
        }
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
        uiViewport.update(width, height, true)
    }

    override fun hide() {
        dispose()
    }

    override fun dispose() {
        stage.dispose()
    }

    private fun update(delta: Float) {
        if (isGameOver()) return
        createNewObstacle(delta)
        removePassedObstacles()
        updateScore(delta)
        updateDisplayScore(delta)
        checkedCollision()
    }

    private fun renderGamePlay() {
        batch.projectionMatrix = camera.combined
        stage.act()
        stage.draw()
    }

    private fun renderUi() {
        uiViewport.apply()
        batch.projectionMatrix = uiCamera.combined
        batch.begin()
        val liveText = LIVE_TEXT.format(lives)
        layout.setText(font, liveText)
        font.color = Color.WHITE
        font.draw(batch, liveText, 20f, UI_HEIGHT - layout.height)
        val scoreText = SCORE_TEXT.format(displayScore)
        layout.setText(font, scoreText)
        font.draw(batch, scoreText, UI_WIDTH - layout.width - 20f, UI_HEIGHT - layout.height)
        if (isGameOver()) {
            layout.setText(font, OVER_GAME_TEXT)
            font.color = Color.RED
            font.draw(
                batch,
                OVER_GAME_TEXT,
                (UI_WIDTH - layout.width) / 2f,
                (UI_HEIGHT - layout.height) / 2f
            )
        }
        batch.end()
    }

    private fun isGameOver(): Boolean {
        return lives <= 0
    }

    private fun createNewObstacle(delta: Float) {
        obstacleTimer += delta
        if (obstacleTimer >= OBSTACLE_SPAWN_TIME) {
//            val min = 0f
//            val max = WORLD_WIDTH - OBSTACLE_SIZE
//            val obstacleX = MathUtils.random(min, max)
//            val obstacleY = WORLD_HEIGHT
//            val obstacle = obstaclePool.obtain()
//            obstacle.setYSpeed(gameManager.getDifficultyLevel().obstacleSpeed)
//            obstacle.setPosition(obstacleX, obstacleY)
//            obstacle.setRegion(obstacleRegion)
//            obstacles.add(obstacle)
//            stage.addActor(obstacle)
//            obstacleTimer = 0f
        }
    }

    private fun removePassedObstacles() {
        if (obstacles.size > 0) {
            val first = obstacles.first()
            val minObstacleY = -OBSTACLE_SIZE
            if (first.y < minObstacleY) {
                obstacles.removeValue(first, true)
                // removes actor from parent / stage
                first.remove()
                // returning to pool
                obstaclePool.free(first)
            }
        }
    }

    private fun updateScore(delta: Float) {
        scoreTimer += delta
        if (scoreTimer >= SCORE_MAX_TIME) {
            score += MathUtils.random(0, 5)
            scoreTimer = 0f
        }
    }

    private fun updateDisplayScore(delta: Float) {
        if (displayScore < score) {
            displayScore = min(score, displayScore + (60 * delta).toInt())
        }
    }

    private fun checkedCollision() {
        if (!isGameOver() && isPlayerCollidingWithObstacle()) {
            lives--
            if (isGameOver()) {
                gameManager.updateHighScore(score)
            } else {
                restart()
            }
        }
    }

    private fun isPlayerCollidingWithObstacle(): Boolean {
        obstacles.forEach { obstacle ->
            if (obstacle.isNotHit() && obstacle.isPlayerColliding(playerActor)) {
                hitSound.play()
                return true
            }
        }
        return false
    }

    private fun restart() {
        obstacles.forEach { obstacle ->
            // remove obstacle from stage (parent)
            obstacle.remove()
            obstaclePool.free(obstacle)
        }
        obstacles.clear()
        playerActor.setPosition(startPlayerX, startPlayerY)
    }

    companion object {
        private const val PADDING = 20f
        private const val LIVE_TEXT = "LIVES: %d"
        private const val SCORE_TEXT = "SCORE: %d"
        private const val OVER_GAME_TEXT = "GAME OVER"
    }
}