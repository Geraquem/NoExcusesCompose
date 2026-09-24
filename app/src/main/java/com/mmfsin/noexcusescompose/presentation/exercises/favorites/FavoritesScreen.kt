package com.mmfsin.noexcusescompose.presentation.exercises.favorites

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mmfsin.noexcusescompose.R
import com.mmfsin.noexcusescompose.domain.models.getExercisesExamples
import com.mmfsin.noexcusescompose.presentation.core.components.CustomToolbar
import com.mmfsin.noexcusescompose.presentation.core.components.LoadingLottie
import com.mmfsin.noexcusescompose.presentation.core.components.MediumText
import com.mmfsin.noexcusescompose.presentation.core.components.OutlinedButtonCustom
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerMedium
import com.mmfsin.noexcusescompose.presentation.core.theme.Black
import com.mmfsin.noexcusescompose.presentation.core.theme.GrayMedium
import com.mmfsin.noexcusescompose.presentation.exercises.exercises.ExercisesList

@Preview
@Composable
fun FavoritesScreenPV() {
    FavoritesContent(
        uiStates = FavoritesStates(
            isLoading = false,
            //            favorites = emptyList()
            favorites = getExercisesExamples()
        ),
        {}, {}, {}
    )
}

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = hiltViewModel(),
    goToMuscularGroups: () -> Unit,
    goToDetail: (String) -> Unit
) {
    val uiStates by viewModel.uiState.collectAsStateWithLifecycle()
    val activity = LocalActivity.current

    FavoritesContent(
        uiStates = uiStates,
        goBack = { activity?.finish() },
        goToMuscularGroups = { goToMuscularGroups() },
        goToDetail = { goToDetail(it) },
    )
}

@Composable
fun FavoritesContent(
    uiStates: FavoritesStates,
    goBack: () -> Unit,
    goToMuscularGroups: () -> Unit,
    goToDetail: (String) -> Unit,
) {
    Scaffold(
        topBar = {
            CustomToolbar(
                goBack = { goBack() },
                title = R.string.favs_title
            )
        }
    ) { innerPadding ->
        Box(
            Modifier.fillMaxSize()
                .background(GrayMedium)
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {

            if (uiStates.favorites.isEmpty()) {
                FavsEmpty(goToMuscularGroups = { goToMuscularGroups() })
            }

            ExercisesList(
                exercises = uiStates.favorites,
                onExerciseClick = { id -> goToDetail(id) }
            )

            if (uiStates.isLoading) LoadingLottie()
        }
    }
}

@Composable
fun FavsEmpty(goToMuscularGroups: () -> Unit) {
    Column(
        modifier = Modifier.padding(horizontal = 42.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(1f))
        MediumText(
            text = R.string.favs_empty,
            gravity = TextAlign.Center,
        )
        SpacerMedium()
        OutlinedButtonCustom(
            onClick = { goToMuscularGroups() },
            text = R.string.favs_see_exercises,
            color = Black
        )
        Spacer(Modifier.weight(1f))
    }
}