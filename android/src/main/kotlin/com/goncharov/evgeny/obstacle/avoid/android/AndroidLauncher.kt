package com.goncharov.evgeny.obstacle.avoid.android

import android.os.Bundle

import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.goncharov.evgeny.obstacle.avoid.App

/** Launches the Android application. */
class AndroidLauncher : AndroidApplication() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize(App(), AndroidApplicationConfiguration().apply {
            // Configure your application here.
        })
    }
}
