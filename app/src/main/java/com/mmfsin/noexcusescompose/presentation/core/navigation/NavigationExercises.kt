package com.mmfsin.noexcusescompose.presentation.core.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mmfsin.noexcusescompose.presentation.exercises.detail.ExerciseDetailScreen
import com.mmfsin.noexcusescompose.presentation.exercises.exercises.ExercisesScreen
import com.mmfsin.noexcusescompose.presentation.exercises.mgroups.MGroupsScreen
import kotlinx.serialization.Serializable

@Composable
fun NavigationExercises(mgroupId: String?) {
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
                goToExercises = { mGroupId ->
                    navController.navigate(Exercises(mGroupId))
                }
            )
        }
        composable<Exercises> {
            ExercisesScreen(
                goBack = { navController.popBackStack() },
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

/** SCREENS */
@Serializable
object MuscularGroups

@Serializable
data class Exercises(val mGroupId: String)

@Serializable
data class ExerciseDetail(val exerciseId: String)