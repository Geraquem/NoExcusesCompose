package com.mmfsin.noexcusescompose.presentation.exercises.favorites

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.mmfsin.noexcusescompose.R
import com.mmfsin.noexcusescompose.domain.models.Exercise
import com.mmfsin.noexcusescompose.domain.models.MuscularGroupType.Companion.getMuscularGroupColor
import com.mmfsin.noexcusescompose.presentation.core.components.CustomToolbar
import com.mmfsin.noexcusescompose.presentation.core.components.LoadingLottie
import com.mmfsin.noexcusescompose.presentation.core.components.MediumText
import com.mmfsin.noexcusescompose.presentation.core.components.OutlinedButtonCustom
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerMedium
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerSmall
import com.mmfsin.noexcusescompose.presentation.core.theme.Black
import com.mmfsin.noexcusescompose.presentation.core.theme.GrayMedium
import com.mmfsin.noexcusescompose.presentation.core.theme.montserrat_bold

@Preview
@Composable
fun FavoritesScreenPV() {
    FavoritesContent(
        uiStates = FavoritesStates(
            isLoading = false,
            favorites = emptyList()
            //            favorites = getExercisesExamples()
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

            CompositionLocalProvider(
                LocalOverscrollFactory provides null
            ) {
                LazyColumn(
                    state = rememberLazyListState(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    items(
                        items = uiStates.favorites,
                        key = { mg -> mg.id }
                    ) { exercise ->
                        FavoriteBox(
                            exercise = exercise,
                            onClick = { exerciseId -> goToDetail(exerciseId) }
                        )
                    }
                }
            }

            if (uiStates.isLoading) LoadingLottie()
        }
    }
}

@Composable
fun FavoriteBox(
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
            AsyncImage(
                model = exercise.gifURL,
                contentDescription = exercise.name,
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp, horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    Modifier.size(14.dp).background(
                        getMuscularGroupColor(exercise.id),
                        shape = CircleShape
                    )
                )

                SpacerSmall(horizontal = true)

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