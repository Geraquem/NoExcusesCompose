package com.mmfsin.noexcusescompose.presentation.exercises.favorites

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mmfsin.noexcusescompose.R
import com.mmfsin.noexcusescompose.presentation.core.components.CustomToolbar
import com.mmfsin.noexcusescompose.presentation.core.components.LoadingLottie
import com.mmfsin.noexcusescompose.presentation.core.components.MediumText
import com.mmfsin.noexcusescompose.presentation.core.components.SmallText
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerLarge
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerSmall
import com.mmfsin.noexcusescompose.presentation.core.theme.GrayHard
import com.mmfsin.noexcusescompose.presentation.core.theme.GrayMedium
import com.mmfsin.noexcusescompose.presentation.core.theme.OrangeLight
import com.mmfsin.noexcusescompose.presentation.core.theme.montserrat_bold
import com.mmfsin.noexcusescompose.presentation.exercises.exercises.ExercisesList

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
                title = R.string.favs_title,
                showIconRight = true,
                iconRight = R.drawable.ic_add,
                iconRightClick = { goToMuscularGroups() }
            )
        }
    ) { innerPadding ->
        Box(
            Modifier.fillMaxSize()
                .background(GrayMedium)
                .padding(innerPadding)
        ) {

            if (uiStates.favorites.isEmpty()) FavsEmpty()

            ExercisesList(
                exercises = uiStates.favorites,
                onExerciseClick = { id -> goToDetail(id) },
            )

            if (uiStates.isLoading) LoadingLottie()
        }
    }
}

@Composable
fun FavsEmpty() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 42.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(Modifier.weight(1f))

        Box(
            modifier = Modifier.size(64.dp).background(OrangeLight, RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painterResource(R.drawable.ic_fav_on), null,
                modifier = Modifier.size(24.dp),
                tint = GrayHard
            )
        }

        SpacerLarge()

        MediumText(
            text = R.string.favs_empty_title,
            gravity = TextAlign.Center,
            fontFamily = montserrat_bold
        )
        SpacerSmall()
        SmallText(
            text = R.string.favs_empty_subtitle,
            gravity = TextAlign.Center,
        )

        Spacer(Modifier.weight(1f))
    }
}