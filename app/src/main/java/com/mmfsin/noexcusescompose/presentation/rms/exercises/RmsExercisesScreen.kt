@file:OptIn(ExperimentalMaterial3Api::class)

package com.mmfsin.noexcusescompose.presentation.rms.exercises

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
import com.mmfsin.noexcusescompose.domain.models.MuscularGroupType.Companion.getMuscularGroupName
import com.mmfsin.noexcusescompose.domain.models.getExercisesExamples
import com.mmfsin.noexcusescompose.presentation.core.components.CustomToolbar
import com.mmfsin.noexcusescompose.presentation.core.components.ErrorDialog
import com.mmfsin.noexcusescompose.presentation.core.components.LoadingLottie
import com.mmfsin.noexcusescompose.presentation.core.theme.GrayMedium
import com.mmfsin.noexcusescompose.presentation.exercises.exercises.ExercisesList
import com.mmfsin.noexcusescompose.presentation.rms.exercises.components.AddRmDialogDialog

@Preview
@Composable
fun RmsExercisesScreenPV() {
    RmsExercisesContent(
        uiStates = RmsExercisesStates(
            isLoading = false,
            exercises = getExercisesExamples()
        ),
        {}, {}, {}, { _, _, _ -> },
        {},
    )
}

@Composable
fun RmsExercisesScreen(
    viewModel: RmsExercisesViewModel = hiltViewModel(),
    goBack: () -> Unit,
    goToExerciseDetail: (String) -> Unit,
    goToCreateExercise: (String) -> Unit,
) {
    val uiStates by viewModel.uiState.collectAsStateWithLifecycle()

    RmsExercisesContent(
        uiStates = uiStates,
        goBack = { goBack() },
        onExerciseClick = { viewModel.onExerciseClick(it) },
        goToExerciseDetail = { goToExerciseDetail(it) },
        addRm = { exerciseId, date, weight -> viewModel.addRm(exerciseId, date, weight) },
        goToCreateExercise = {},
    )
}

@Composable
fun RmsExercisesContent(
    uiStates: RmsExercisesStates,
    goBack: () -> Unit,
    onExerciseClick: (String?) -> Unit,
    goToExerciseDetail: (String) -> Unit,
    addRm: (String, Long, Double) -> Unit,
    goToCreateExercise: () -> Unit,
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
                    onExerciseClick = { id -> onExerciseClick(id) },
                )
            }
        }
    }

    if (uiStates.exerciseIdClick != null) {
        uiStates.exerciseClicked?.let { exercise ->
            AddRmDialogDialog(
                onDismiss = { onExerciseClick(null) },
                exercise = exercise,
                seeExercise = { goToExerciseDetail(exercise.id) },
                addRm = { date, kgs -> addRm(exercise.id, date, kgs) }
            )
        }
    }

    if (uiStates.sww) ErrorDialog { }
}