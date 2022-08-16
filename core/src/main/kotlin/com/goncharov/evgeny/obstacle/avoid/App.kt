package com.goncharov.evgeny.obstacle.avoid

import com.badlogic.gdx.Application
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.goncharov.evgeny.obstacle.avoid.consts.AssetDescriptors
import com.goncharov.evgeny.obstacle.avoid.navigation.KeyNavigation
import com.goncharov.evgeny.obstacle.avoid.navigation.Navigation
import com.goncharov.evgeny.obstacle.avoid.screens.HighScoreScreen
import com.goncharov.evgeny.obstacle.avoid.screens.LoadingScreen
import com.goncharov.evgeny.obstacle.avoid.screens.MenuScreen
import com.goncharov.evgeny.obstacle.avoid.screens.OptionsScreen
import com.goncharov.evgeny.obstacle.avoid.screens.game.GameScreen
import com.goncharov.evgeny.obstacle.avoid.utils.FormatUtils
import com.goncharov.evgeny.obstacle.avoid.utils.FpsMonitorManager
import com.goncharov.evgeny.obstacle.avoid.utils.debug.DebugDrawingFps

class App : Game(), Navigation, FpsMonitorManager {

    private val batch by lazy {
        SpriteBatch()
    }
    private val assetManager by lazy {
        AssetManager()
    }
    private val debugRender by lazy {
        ShapeRenderer()
    }
    private var drawingFps = false

    override fun create() {
        assetManager.load(AssetDescriptors.FPS_FONT_DESCRIPTOR)
        assetManager.finishLoading()
        Gdx.app.logLevel = Application.LOG_DEBUG
        Gdx.app.debug(
            "App",
            "${FormatUtils.dateFormat.format(FormatUtils.calendar.time)} start application"
        )
        navigate(KeyNavigation.LoadingKey)
    }

    override fun render() {
        super.render()
        if (drawingFps) {
            DebugDrawingFps.drawFpsMonitor(
                batch,
                assetManager[AssetDescriptors.FPS_FONT_DESCRIPTOR]
            )
        }
    }

    override fun resize(width: Int, height: Int) {
        DebugDrawingFps.resize(width, height)
        super.resize(width, height)
    }

    override fun dispose() {
        batch.dispose()
        assetManager.dispose()
        Gdx.app.debug(
            "App",
            "${FormatUtils.dateFormat.format(FormatUtils.calendar.time)} dispose"
        )
        debugRender.dispose()
    }

    override fun navigate(key: KeyNavigation) {
        when (key) {
            KeyNavigation.LoadingKey -> setScreen(
                LoadingScreen(assetManager, debugRender, this)
            )
            KeyNavigation.MenuKey -> setScreen(
                MenuScreen(this, assetManager, batch, this)
            )
            KeyNavigation.GameKey -> setScreen(
                GameScreen(this, assetManager, batch)
            )
            KeyNavigation.HighScoreKey -> setScreen(
                HighScoreScreen(this, assetManager, batch, this)
            )
            KeyNavigation.OptionsKey -> setScreen(
                OptionsScreen(this, assetManager, batch, this)
            )
        }
    }

    override fun updateFpsMonitor() {
        drawingFps = !drawingFps
    }
}