package com.mmfsin.noexcusescompose.presentation.core.navigation

import androidx.activity.compose.LocalActivity
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mmfsin.noexcusescompose.presentation.exercises.detail.ExerciseDetailScreen
import com.mmfsin.noexcusescompose.presentation.rms.detail.RmDetailScreen

@Composable
fun NavigationRmDetail(exerciseId: String) {

    val activity = LocalActivity.current
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = RmDetail(exerciseId),
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable<RmDetail> {
            RmDetailScreen(
                goBack = { activity?.finish() },
                goToExerciseDetail = { exerciseId ->
                    navController.navigate(ExerciseDetail(exerciseId))
                }
            )
        }
        composable<ExerciseDetail> {
            ExerciseDetailScreen(
                goBack = { navController.popBackStack() }
            )
        }
    }
}