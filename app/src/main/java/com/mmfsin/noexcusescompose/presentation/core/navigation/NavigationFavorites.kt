package com.mmfsin.noexcusescompose.presentation.core.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mmfsin.noexcusescompose.presentation.exercises.detail.ExerciseDetailScreen
import com.mmfsin.noexcusescompose.presentation.exercises.exercises.ExercisesScreen
import com.mmfsin.noexcusescompose.presentation.exercises.favorites.FavoritesScreen
import com.mmfsin.noexcusescompose.presentation.exercises.mgroups.MGroupsScreen

@Composable
fun NavigationFavorites() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Favorites,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable<Favorites> {
            FavoritesScreen(
                goToMuscularGroups = { navController.navigate(MuscularGroups) },
                goToDetail = { exerciseId ->
                    navController.navigate(ExerciseDetail(exerciseId))
                }
            )
        }

        composable<MuscularGroups> {
            MGroupsScreen(
                goBack = { navController.popBackStack() },
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