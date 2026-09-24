package com.mmfsin.noexcusescompose.presentation.core.navigation

import androidx.activity.compose.LocalActivity
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mmfsin.noexcusescompose.presentation.exercises.detail.ExerciseDetailScreen
import com.mmfsin.noexcusescompose.presentation.exercises.exercises.ExercisesScreen
import com.mmfsin.noexcusescompose.presentation.exercises.mgroups.MGroupsScreen

@Composable
fun NavigationExercises(mgroupId: String?) {

    val activity = LocalActivity.current
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = if (mgroupId == null) MuscularGroups else Exercises(mgroupId),
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable<MuscularGroups> {
            MGroupsScreen(
                goBack = { activity?.finish() },
                goToExercises = { mGroupId ->
                    navController.navigate(Exercises(mGroupId))
                }
            )
        }
        composable<Exercises> {
            ExercisesScreen(
                goBack = {
                    if (navController.previousBackStackEntry == null) activity?.finish()
                    else navController.popBackStack()
                },
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