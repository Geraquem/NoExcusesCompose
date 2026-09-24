package com.mmfsin.noexcusescompose.presentation.core.navigation

import androidx.activity.compose.LocalActivity
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mmfsin.noexcusescompose.presentation.stretching.StretchScreen

@Composable
fun NavigationStretch() {

    val activity = LocalActivity.current
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Stretch,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable<Stretch> {
            StretchScreen(
                goBack = { activity?.finish() },
            )
        }
    }
}