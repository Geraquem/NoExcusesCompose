package com.mmfsin.noexcusescompose.presentation.exercises.exercises

import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mmfsin.noexcusescompose.R
import com.mmfsin.noexcusescompose.domain.models.Exercise
import com.mmfsin.noexcusescompose.domain.models.MuscularGroupType.Companion.getMuscularGroupName
import com.mmfsin.noexcusescompose.domain.models.getExercisesExamples
import com.mmfsin.noexcusescompose.presentation.core.components.CustomToolbar
import com.mmfsin.noexcusescompose.presentation.core.components.ErrorDialog
import com.mmfsin.noexcusescompose.presentation.core.components.ImageGif
import com.mmfsin.noexcusescompose.presentation.core.components.LoadingLottie
import com.mmfsin.noexcusescompose.presentation.core.components.MediumText
import com.mmfsin.noexcusescompose.presentation.core.components.SmallText
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerSmall
import com.mmfsin.noexcusescompose.presentation.core.theme.Black
import com.mmfsin.noexcusescompose.presentation.core.theme.GrayLight
import com.mmfsin.noexcusescompose.presentation.core.theme.GrayMedium
import com.mmfsin.noexcusescompose.presentation.core.theme.White
import com.mmfsin.noexcusescompose.presentation.core.theme.YellowHard
import com.mmfsin.noexcusescompose.presentation.core.theme.YellowLight
import com.mmfsin.noexcusescompose.presentation.core.theme.montserrat_regular

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
    if (uiStates.sww) ErrorDialog { goBack() }
}

@Composable
fun ExercisesList(
    exercises: List<Exercise>,
    onExerciseClick: (String) -> Unit
) {
    CompositionLocalProvider(
        LocalOverscrollFactory provides null
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(
                vertical = 8.dp,
                horizontal = 8.dp
            )
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
        onClick = { onClick(exercise.id) },
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = White
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Box {
            Column(
                modifier = Modifier

                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ImageGif(
                    url = exercise.gifURL,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                )

                SpacerSmall()

                Box(
                    modifier = Modifier.fillMaxWidth()
                        .height(80.dp)
                        .background(if(exercise.isFav) YellowLight else GrayLight)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = exercise.name,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        fontFamily = montserrat_regular,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}
