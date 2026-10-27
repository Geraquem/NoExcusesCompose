package com.mmfsin.noexcuses.presentation.core.navigation

import androidx.activity.compose.LocalActivity
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mmfsin.noexcuses.presentation.exercises.create.CreateExerciseScreen
import com.mmfsin.noexcuses.presentation.exercises.create.edit.EditExerciseScreen
import com.mmfsin.noexcuses.presentation.exercises.detail.ExerciseDetailScreen
import com.mmfsin.noexcuses.presentation.myroutines.days.DayScreen
import com.mmfsin.noexcuses.presentation.myroutines.exercises.ExercisesRtnScreen
import com.mmfsin.noexcuses.presentation.myroutines.mgroups.MGroupsRtnScreen

@Composable
fun NavigationDayDetail(routineId: String, dayId: String?) {
    val navController = rememberNavController()
    val activity = LocalActivity.current

    NavHost(
        navController = navController,
        startDestination = DayDetail(routineId, dayId),
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable<DayDetail> {
            DayScreen(
                goBack = { activity?.finish() },
                goToMuscularGroups = { id, name ->
                    navController.navigate(
                        MuscularGroupsRtn(
                            dayId = id,
                            dayName = name
                        )
                    )
                },
                goToExerciseDetail = { exerciseId ->
                    navController.navigate(ExerciseDetail(exerciseId))
                }
            )
        }

        composable<MuscularGroupsRtn> {
            MGroupsRtnScreen(
                goBack = { navController.popBackStack() },
                goToExercises = { dayId, dayName, mGroupId ->
                    navController.navigate(ExercisesRtn(dayId, dayName, mGroupId))
                }
            )
        }

        composable<ExercisesRtn> {
            ExercisesRtnScreen(
                goBack = { navController.popBackStack() },
                goToExerciseDetail = { exerciseId ->
                    navController.navigate(ExerciseDetail(exerciseId))
                },
                goToCreateExercise = { mgroupId ->
                    navController.navigate(CreateExercise(mgroupId))
                },
                goToEditExercise = { exerciseId ->
                    navController.navigate(EditExercise(exerciseId))
                }
            )
        }

        composable<ExerciseDetail> {
            ExerciseDetailScreen(
                goBack = { navController.popBackStack() },
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