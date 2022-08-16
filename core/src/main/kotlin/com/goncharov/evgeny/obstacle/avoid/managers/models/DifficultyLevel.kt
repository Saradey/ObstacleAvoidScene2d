package com.goncharov.evgeny.obstacle.avoid.managers.models

import com.goncharov.evgeny.obstacle.avoid.consts.EASY_OBSTACLE_SPEED
import com.goncharov.evgeny.obstacle.avoid.consts.HARD_OBSTACLE_SPEED
import com.goncharov.evgeny.obstacle.avoid.consts.MEDIUM_OBSTACLE_SPEED

enum class DifficultyLevel(val obstacleSpeed: Float) {
    EASY(EASY_OBSTACLE_SPEED),
    MEDIUM(MEDIUM_OBSTACLE_SPEED),
    HARD(HARD_OBSTACLE_SPEED)
}