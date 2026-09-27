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
import com.mmfsin.noexcusescompose.presentation.notes.create.NoteDetailScreen

@Composable
fun NavigationNoteDetail(noteId: String?) {

    val activity = LocalActivity.current
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NoteDetail(noteId),
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable<NoteDetail> {
            NoteDetailScreen(
                goBack = {
                    if (navController.previousBackStackEntry == null) activity?.finish()
                    else navController.popBackStack()
                },
            )
        }
    }
}