package com.mmfsin.noexcusescompose.presentation.exercises.exercises

import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mmfsin.noexcusescompose.domain.models.Exercise
import com.mmfsin.noexcusescompose.domain.models.MuscularGroupType.Companion.getMuscularGroupName
import com.mmfsin.noexcusescompose.presentation.core.components.CustomToolbar
import com.mmfsin.noexcusescompose.presentation.core.components.LoadingLottie
import com.mmfsin.noexcusescompose.presentation.core.components.MediumText
import com.mmfsin.noexcusescompose.presentation.core.theme.Black
import com.mmfsin.noexcusescompose.presentation.core.theme.GrayMedium
import com.mmfsin.noexcusescompose.presentation.core.theme.montserrat_bold

@Preview
@Composable
fun ExercisesScreenPV() {
    ExercisesContent(
        uiStates = ExercisesStates(
            isLoading = true,
        ),
        {}
    )
}

@Composable
fun ExercisesScreen(
    viewModel: ExercisesViewModel = hiltViewModel(),
    goBack: () -> Unit
) {
    val uiStates by viewModel.uiState.collectAsStateWithLifecycle()

    ExercisesContent(
        uiStates = uiStates,
        goBack = { goBack() },
    )
}

@Composable
fun ExercisesContent(
    uiStates: ExercisesStates,
    goBack: () -> Unit,
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
                .padding(horizontal = 16.dp)
        ) {
            if (uiStates.exercises.isEmpty()) LoadingLottie()
            ExercisesList(uiStates.exercises)
        }
    }
}

@Composable
fun ExercisesList(exercises: List<Exercise>) {
    CompositionLocalProvider(
        LocalOverscrollFactory provides null
    ) {
        LazyColumn(
            state = rememberLazyListState(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(
                items = exercises,
                key = { e -> e.id }
            ) { exercise -> ExerciseBox(exercise, onClick = {}) }
        }
    }
}

@Composable
fun ExerciseBox(
    exercise: Exercise,
    onClick: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Box(
            Modifier.height(100.dp).clickable(onClick = { onClick(exercise.id) }),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp, horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                MediumText(
                    text = exercise.name,
                    color = Black,
                    allCaps = true,
                    fontFamily = montserrat_bold
                )
            }
        }
    }
}
