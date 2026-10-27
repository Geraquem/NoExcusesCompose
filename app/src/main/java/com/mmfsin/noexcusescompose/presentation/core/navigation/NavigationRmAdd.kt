package com.mmfsin.noexcusescompose.presentation.core.navigation

import androidx.activity.compose.LocalActivity
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mmfsin.noexcusescompose.presentation.exercises.create.CreateExerciseScreen
import com.mmfsin.noexcusescompose.presentation.exercises.create.edit.EditExerciseScreen
import com.mmfsin.noexcusescompose.presentation.exercises.detail.ExerciseDetailScreen
import com.mmfsin.noexcusescompose.presentation.exercises.mgroups.MGroupsScreen
import com.mmfsin.noexcusescompose.presentation.rms.exercises.RmsExercisesScreen

@Composable
fun NavigationRmAdd() {

    val activity = LocalActivity.current
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = MuscularGroups,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable<MuscularGroups> {
            MGroupsScreen(
                goBack = { activity?.finish() },
                goToExercises = { mGroupId ->
                    navController.navigate(RmsExercises(mGroupId))
                }
            )
        }
        composable<RmsExercises> {
            RmsExercisesScreen(
                goBack = {
                    if (navController.previousBackStackEntry == null) activity?.finish()
                    else navController.popBackStack()
                },
                goToExerciseDetail = { exerciseId ->
                    navController.navigate(ExerciseDetail(exerciseId))
                },
                goToCreateExercise = { mgroupId ->
                    navController.navigate(CreateExercise(mgroupId))
                }
            )
        }

        composable<ExerciseDetail> {
            ExerciseDetailScreen(
                goBack = { navController.popBackStack() },
                goToEditExercise = { exerciseId ->
                    navController.navigate(EditExercise(exerciseId))
                }
            )
        }

        composable<CreateExercise> {
            CreateExerciseScreen(
                goBack = { navController.popBackStack() },
            )
        }
        composable<EditExercise> {
            EditExerciseScreen(
                goBack = { navController.popBackStack() },
            )
        }
    }
}