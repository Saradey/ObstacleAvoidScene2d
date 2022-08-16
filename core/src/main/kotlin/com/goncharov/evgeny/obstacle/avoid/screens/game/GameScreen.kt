package com.goncharov.evgeny.obstacle.avoid.screens.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.GlyphLayout
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Stage
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
import com.goncharov.evgeny.obstacle.avoid.navigation.Navigation

class GameScreen(
    private val navigator: Navigation,
    assetManager: AssetManager,
    batch: SpriteBatch
) : BaseScreen() {

    private val layout = GlyphLayout()
    private val camera = OrthographicCamera()
    private val viewport = FitViewport(WORLD_WIDTH, WORLD_HEIGHT)
    private val stage = Stage(viewport, batch)
    private val renderer = ShapeRenderer()
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

    }

    override fun render(delta: Float) {

    }

    override fun resize(width: Int, height: Int) {

    }

    override fun hide() {
        dispose()
    }

    override fun dispose() {

    }

    companion object {
        private const val PADDING = 20f
    }
}