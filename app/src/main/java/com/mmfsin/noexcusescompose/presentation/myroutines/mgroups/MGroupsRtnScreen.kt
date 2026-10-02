@file:OptIn(ExperimentalMaterial3Api::class)

package com.mmfsin.noexcusescompose.presentation.myroutines.mgroups

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
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mmfsin.noexcusescompose.domain.models.getMuscularGroupsExamples
import com.mmfsin.noexcusescompose.presentation.core.components.CustomToolbar
import com.mmfsin.noexcusescompose.presentation.core.components.ErrorDialog
import com.mmfsin.noexcusescompose.presentation.core.theme.GrayMedium
import com.mmfsin.noexcusescompose.presentation.exercises.mgroups.MuscularGroupList

@Preview
@Composable
fun MGroupsRtnScreenPV() {
    MGroupsRtnContent(
        uiStates = MGroupsRtnStates(
            isLoading = false,
            muscularGroups = getMuscularGroupsExamples()
        ),
        {}, {}
    )
}

@Composable
fun MGroupsRtnScreen(
    viewModel: MGroupsRtnViewModel = hiltViewModel(),
    goBack: () -> Unit,
    goToExercises: (String, String, String) -> Unit
) {
    val uiStates by viewModel.uiState.collectAsStateWithLifecycle()

    MGroupsRtnContent(
        uiStates = uiStates,
        goBack = { goBack() },
        goToExercises = { mGroupId ->
            goToExercises(uiStates.dayId, uiStates.dayName, mGroupId)
        },
    )
}

@Composable
fun MGroupsRtnContent(
    uiStates: MGroupsRtnStates,
    goBack: () -> Unit,
    goToExercises: (String) -> Unit,
) {
    Scaffold(
        topBar = {
            CustomToolbar(
                goBack = { goBack() },
                titleString = uiStates.dayName
            )
        }
    ) { innerPadding ->
        Box(
            Modifier.fillMaxSize()
                .background(GrayMedium)
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            MuscularGroupList(
                muscularGroups = uiStates.muscularGroups,
                goToExercises = { mGroupId -> goToExercises(mGroupId) }
            )
        }
    }

    if (uiStates.sww) ErrorDialog { goBack() }
}