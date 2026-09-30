package com.mmfsin.noexcusescompose.presentation.core.navigation

import androidx.activity.compose.LocalActivity
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mmfsin.noexcusescompose.presentation.exercises.mgroups.MGroupsScreen
import com.mmfsin.noexcusescompose.presentation.myroutines.days.detail.DayDetailScreen
import com.mmfsin.noexcusescompose.presentation.myroutines.routines.MyRoutinesScreen

@Composable
fun NavigationMyRoutines() {
    val navController = rememberNavController()
    val activity = LocalActivity.current

    NavHost(
        navController = navController,
        startDestination = MyRoutines,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable<MyRoutines> {
            MyRoutinesScreen(
                goBack = { activity?.finish() },
                goToDayDetail = { routineId, dayId ->
                    navController.navigate(DayDetail(routineId, dayId))
                }
            )
        }

        composable<DayDetail> {
            DayDetailScreen(
                goBack = { navController.popBackStack() },
                goToMuscularGroups = { navController.navigate(MuscularGroups) }
            )
        }

        composable<MuscularGroups> {
            MGroupsScreen(
                goBack = { navController.popBackStack() },
                goToExercises = {

                }
            )
        }
    }
}