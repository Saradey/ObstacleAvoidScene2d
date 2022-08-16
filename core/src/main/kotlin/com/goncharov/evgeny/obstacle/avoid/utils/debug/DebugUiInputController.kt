package com.goncharov.evgeny.obstacle.avoid.utils.debug

import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.Stage
import com.goncharov.evgeny.obstacle.avoid.common.BaseInputProcessor
import com.goncharov.evgeny.obstacle.avoid.utils.FpsMonitorManager

class DebugUiInputController(
    private val stage: Stage,
    private val fpsMonitorManager: FpsMonitorManager
) : BaseInputProcessor() {

    override fun keyDown(keycode: Int): Boolean {
        when (keycode) {
            Input.Keys.C -> stage.isDebugAll = !stage.isDebugAll
            Input.Keys.B -> fpsMonitorManager.updateFpsMonitor()
        }
        return true
    }
}