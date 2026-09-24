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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mmfsin.noexcusescompose.R
import com.mmfsin.noexcusescompose.domain.models.Exercise
import com.mmfsin.noexcusescompose.domain.models.MuscularGroupType.Companion.getMuscularGroupName
import com.mmfsin.noexcusescompose.domain.models.getExercisesExamples
import com.mmfsin.noexcusescompose.presentation.core.components.CustomToolbar
import com.mmfsin.noexcusescompose.presentation.core.components.ImageGif
import com.mmfsin.noexcusescompose.presentation.core.components.LoadingLottie
import com.mmfsin.noexcusescompose.presentation.core.components.MediumText
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerMedium
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerMini
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerSmall
import com.mmfsin.noexcusescompose.presentation.core.theme.Black
import com.mmfsin.noexcusescompose.presentation.core.theme.GrayMedium
import com.mmfsin.noexcusescompose.presentation.core.theme.White
import com.mmfsin.noexcusescompose.presentation.core.theme.YellowHard

@Preview
@Composable
fun ExercisesScreenPV() {
    ExercisesContent(
        uiStates = ExercisesStates(
            isLoading = true,
            exercises = getExercisesExamples()
        ),
        {}, {}
    )
}

@Composable
fun ExercisesScreen(
    viewModel: ExercisesViewModel = hiltViewModel(),
    goBack: () -> Unit,
    goToExerciseDetail: (String) -> Unit
) {
    val uiStates by viewModel.uiState.collectAsStateWithLifecycle()

    ExercisesContent(
        uiStates = uiStates,
        goBack = { goBack() },
        goToExerciseDetail = { goToExerciseDetail(it) },
    )
}

@Composable
fun ExercisesContent(
    uiStates: ExercisesStates,
    goBack: () -> Unit,
    goToExerciseDetail: (String) -> Unit,
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
            else {
                ExercisesList(
                    exercises = uiStates.exercises,
                    onExerciseClick = { id -> goToExerciseDetail(id) }
                )
            }
        }
    }
}

@Composable
fun ExercisesList(
    exercises: List<Exercise>,
    onExerciseClick: (String) -> Unit
) {
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
            ) { exercise -> ExerciseBox(exercise, onClick = { onExerciseClick(exercise.id) }) }
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
        colors = CardDefaults.cardColors(
            containerColor = White
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Box(
            Modifier.clickable(onClick = { onClick(exercise.id) }),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .padding(start = 8.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                ImageGif(
                    url = exercise.gifURL,
                    modifier = Modifier.size(75.dp)
                )
                SpacerSmall(horizontal = true)

                if (exercise.isFav) {
                    Icon(
                        painterResource(R.drawable.ic_fav_on), null,
                        tint = YellowHard
                    )
                    SpacerMini(horizontal = true)
                }

                MediumText(
                    text = exercise.name,
                    color = Black,
                    modifier = Modifier.weight(1f)
                )
                SpacerSmall(horizontal = true)
                Icon(painterResource(R.drawable.ic_arrow_right), null)
            }
        }
    }
}
