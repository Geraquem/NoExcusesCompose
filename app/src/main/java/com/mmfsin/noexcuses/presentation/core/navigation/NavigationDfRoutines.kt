package com.mmfsin.noexcuses.presentation.core.navigation

import androidx.activity.compose.LocalActivity
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mmfsin.noexcuses.presentation.dfroutines.days.DfDayScreen
import com.mmfsin.noexcuses.presentation.exercises.detail.ExerciseDetailScreen
import com.mmfsin.noexcuses.presentation.dfroutines.routines.DfRoutinesScreen

@Composable
fun NavigationDfRoutines() {
    val navController = rememberNavController()
    val activity = LocalActivity.current

    NavHost(
        navController = navController,
        startDestination = DfRoutines,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable<DfRoutines> {
            DfRoutinesScreen (
                goBack = { activity?.finish() },
                goToDayDetail = { routineId, dayId ->
                    navController.navigate(DfDayDetail(routineId, dayId))
                }
            )
        }

        composable<DfDayDetail> {
            DfDayScreen(
                goBack = { navController.popBackStack() },
                goToExerciseDetail = { exerciseId ->
                    navController.navigate(ExerciseDetail(exerciseId))
                }
            )
        }

        composable<ExerciseDetail> {
            ExerciseDetailScreen(
                goBack = { navController.popBackStack() },
            )
        }
    }
}