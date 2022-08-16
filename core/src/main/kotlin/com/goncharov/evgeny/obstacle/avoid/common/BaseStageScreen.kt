package com.goncharov.evgeny.obstacle.avoid.common

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.utils.viewport.FitViewport
import com.goncharov.evgeny.obstacle.avoid.consts.AssetDescriptors
import com.goncharov.evgeny.obstacle.avoid.consts.UI_HEIGHT
import com.goncharov.evgeny.obstacle.avoid.consts.UI_WIDTH
import com.goncharov.evgeny.obstacle.avoid.navigation.Navigation
import com.goncharov.evgeny.obstacle.avoid.utils.FpsMonitorManager
import com.goncharov.evgeny.obstacle.avoid.utils.GdxUtils
import com.goncharov.evgeny.obstacle.avoid.utils.debug.DebugCameraController
import com.goncharov.evgeny.obstacle.avoid.utils.debug.DebugUiInputController

abstract class BaseStageScreen(
    protected val navigation: Navigation,
    protected val assetManager: AssetManager,
    private val batch: SpriteBatch,
    fpsMonitorManager: FpsMonitorManager
) : BaseScreen() {

    private val camera = OrthographicCamera()
    private val viewport = FitViewport(UI_WIDTH, UI_HEIGHT, camera)
    private val stage = Stage(viewport, batch)
    protected val uiSkin: Skin = assetManager[AssetDescriptors.UI_SKIN_DESCRIPTOR]
    private val debugController = DebugUiInputController(stage, fpsMonitorManager)

    override fun show() {
        debug("show")
        val inputMultiplexer = InputMultiplexer()
        inputMultiplexer.addProcessor(stage)
        inputMultiplexer.addProcessor(debugController)
        Gdx.input.inputProcessor = inputMultiplexer
        stage.addActor(initUi())
    }

    override fun resize(width: Int, height: Int) {
        debug("resize")
        viewport.update(width, height, true)
    }

    override fun render(delta: Float) {
        DebugCameraController.updateCameraUi(camera)
        GdxUtils.clearScreen()
        stage.act()
        stage.draw()
    }

    override fun hide() {
        debug("hide")
        dispose()
    }

    override fun dispose() {
        debug("dispose")
        stage.dispose()
        Gdx.input.inputProcessor = null
    }

    protected abstract fun initUi(): Actor
}