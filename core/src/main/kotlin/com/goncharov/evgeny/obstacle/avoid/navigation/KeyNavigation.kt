package com.goncharov.evgeny.obstacle.avoid.navigation

sealed class KeyNavigation {
    object LoadingKey : KeyNavigation()
    object MenuKey : KeyNavigation()
    object GameKey : KeyNavigation()
    object HighScoreKey : KeyNavigation()
    object OptionsKey : KeyNavigation()
}