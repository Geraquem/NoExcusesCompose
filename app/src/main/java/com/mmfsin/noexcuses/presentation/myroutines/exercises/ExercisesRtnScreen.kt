@file:OptIn(ExperimentalMaterial3Api::class)

package com.mmfsin.noexcuses.presentation.myroutines.exercises

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mmfsin.noexcuses.domain.models.MuscularGroupType.Companion.getMuscularGroupName
import com.mmfsin.noexcuses.domain.models.getExercisesExamples
import com.mmfsin.noexcuses.presentation.core.components.CustomToolbar
import com.mmfsin.noexcuses.presentation.core.components.ErrorDialog
import com.mmfsin.noexcuses.presentation.core.components.LoadingLottie
import com.mmfsin.noexcuses.presentation.core.theme.GrayMedium
import com.mmfsin.noexcuses.presentation.exercises.exercises.ExercisesList
import com.mmfsin.noexcuses.presentation.myroutines.exercises.add.AddExerciseRtnDialog
import com.mmfsin.noexcuses.util.CREATE_EXERCISE

@Preview
@Composable
fun ExercisesRtnScreenPV() {
    ExercisesRtnContent(
        uiStates = ExercisesRtnStates(
            isLoading = false,
            exercises = getExercisesExamples()
        ),
        {}, {}, {}, {},
        {}
    )
}

@Composable
fun ExercisesRtnScreen(
    viewModel: ExercisesRtnViewModel = hiltViewModel(),
    goBack: () -> Unit,
    goToExerciseDetail: (String) -> Unit,
    goToCreateExercise: (String) -> Unit,
    goToEditExercise: (String) -> Unit,
) {
    val uiStates by viewModel.uiState.collectAsStateWithLifecycle()

    ExercisesRtnContent(
        uiStates = uiStates,
        goBack = { goBack() },
        onExerciseClick = { viewModel.onExerciseClick(it) },
        goToExerciseDetail = { goToExerciseDetail(it) },
        goToCreateExercise = { goToCreateExercise(uiStates.mGroupId) },
        goToEditExercise = { goToEditExercise(it) },
    )
}

@Composable
fun ExercisesRtnContent(
    uiStates: ExercisesRtnStates,
    goBack: () -> Unit,
    onExerciseClick: (String?) -> Unit,
    goToExerciseDetail: (String) -> Unit,
    goToCreateExercise: () -> Unit,
    goToEditExercise: (String) -> Unit,
) {
    Scaffold(
        topBar = {
            CustomToolbar(
                goBack = { goBack() },
                title = getMuscularGroupName(uiStates.mGroupId)
            )
        }
    ) { innerPadding ->
        Box(
            Modifier.fillMaxSize()
                .background(GrayMedium)
                .padding(innerPadding)
        ) {
            if (uiStates.exercises.isEmpty()) LoadingLottie()
            else {
                ExercisesList(
                    exercises = uiStates.exercises,
                    onExerciseClick = { id ->
                        if (id == CREATE_EXERCISE) goToCreateExercise()
                        else onExerciseClick(id)
                    },
                    onExerciseLongClick = { goToEditExercise(it) }
                )
            }
        }
    }

    if (uiStates.exerciseIdClick != null) {
        uiStates.exerciseClicked?.let { exercise ->
            AddExerciseRtnDialog(
                exercise = exercise,
                dayId = uiStates.dayId,
                dayName = uiStates.dayName,
                onDismiss = { onExerciseClick(null) },
                seeExercise = { goToExerciseDetail(uiStates.exerciseIdClick) },
            )
        }
    }

    if (uiStates.sww) ErrorDialog { }
}