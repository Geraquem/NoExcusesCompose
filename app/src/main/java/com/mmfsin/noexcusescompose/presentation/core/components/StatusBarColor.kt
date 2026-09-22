package com.mmfsin.noexcusescompose.presentation.core.components

import android.app.Activity
import android.view.Window
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat

@Composable
fun StatusBarColor(color: Color = Color.White, darkIcons: Boolean = true) {
    val view = LocalView.current
    SideEffect {
        val window = (view.context as Activity).window
        WindowCompat.setDecorFitsSystemWindows(window, true)
        window.statusBarColor = color.toArgb()
        WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = darkIcons
    }
}

@Composable
fun StatusBarWhiteIcons() {
    val view = LocalView.current
    SideEffect {
        val window = (view.context as Activity).window
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
    }
}

fun Activity.setStatusBarIconsWhite() {
    WindowCompat.getInsetsController(window, window.decorView)
        .isAppearanceLightStatusBars = false
}