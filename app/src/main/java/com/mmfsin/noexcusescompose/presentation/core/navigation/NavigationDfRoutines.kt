package com.mmfsin.noexcusescompose.presentation.core.navigation

import androidx.activity.compose.LocalActivity
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mmfsin.noexcusescompose.presentation.dfroutines.days.DfDayScreen
import com.mmfsin.noexcusescompose.presentation.exercises.detail.ExerciseDetailScreen
import com.mmfsin.noexcusescompose.presentation.myroutines.days.DayScreen
import com.mmfsin.noexcusescompose.presentation.dfroutines.routines.DfRoutinesScreen
import com.mmfsin.noexcusescompose.presentation.exercises.create.edit.EditExerciseScreen

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