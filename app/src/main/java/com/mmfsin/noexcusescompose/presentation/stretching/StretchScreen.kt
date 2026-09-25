package com.mmfsin.noexcusescompose.presentation.stretching

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mmfsin.noexcusescompose.R
import com.mmfsin.noexcusescompose.domain.models.MuscularGroupType.Companion.getMuscularGroupColor
import com.mmfsin.noexcusescompose.domain.models.Stretch
import com.mmfsin.noexcusescompose.domain.models.Stretching
import com.mmfsin.noexcusescompose.domain.models.getExampleStretchData
import com.mmfsin.noexcusescompose.presentation.core.components.CustomToolbar
import com.mmfsin.noexcusescompose.presentation.core.components.ImageGif
import com.mmfsin.noexcusescompose.presentation.core.components.LoadingLottie
import com.mmfsin.noexcusescompose.presentation.core.components.MediumText
import com.mmfsin.noexcusescompose.presentation.core.components.SmallText
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerLarge
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerMini
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerSmall
import com.mmfsin.noexcusescompose.presentation.core.theme.GrayMedium
import com.mmfsin.noexcusescompose.presentation.core.theme.White
import com.mmfsin.noexcusescompose.presentation.core.theme.montserrat_bold

@Preview
@Composable
fun StretchScreenPV() {
    StretchContent(
        uiStates = StretchStates(
            isLoading = false,
            stretchingExercises = getExampleStretchData()
        ),
        {},
    )
}

@Composable
fun StretchScreen(
    viewModel: StretchViewModel = hiltViewModel(),
    goBack: () -> Unit
) {
    val uiStates by viewModel.uiState.collectAsStateWithLifecycle()

    StretchContent(
        uiStates = uiStates,
        goBack = { goBack() },
    )
}

@Composable
fun StretchContent(
    uiStates: StretchStates,
    goBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            CustomToolbar(
                goBack = { goBack() },
                title = R.string.stretching_title
            )
        }
    ) { innerPadding ->
        Box(
            Modifier.fillMaxSize()
                .background(GrayMedium)
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
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
                        items = uiStates.stretchingExercises,
                        key = { e -> e.mGroup }
                    ) { exercise -> StretchBox(exercise) }
                }
            }
        }

        if (uiStates.isLoading) LoadingLottie()
    }
}

@Composable
fun StretchBox(stretch: Stretch) {

    var expanded by rememberSaveable { mutableStateOf(false) }

    Column(
        Modifier.fillMaxWidth()
            .background(White, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = { expanded = !expanded })
            .padding(horizontal = 12.dp, vertical = 18.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                Modifier.size(14.dp).background(
                    getMuscularGroupColor(stretch.mGroup),
                    shape = CircleShape
                )
            )
            SpacerSmall(horizontal = true)
            MediumText(
                text = stretch.mGroup.replaceFirstChar { it.uppercase() },
                fontFamily = montserrat_bold
            )
            Spacer(Modifier.weight(1f))
            Icon(
                painterResource(R.drawable.ic_arrow_down), null,
                modifier = Modifier.graphicsLayer { scaleY = if (expanded) 1f else -1f })
        }

        AnimatedVisibility(expanded) {
            Column {
                SpacerLarge()
                stretch.stretching.forEachIndexed { i, stretching ->
                    StretchExercise(i + 1, stretching)
                }
            }
        }
    }
}

@Composable
fun StretchExercise(index: Int, stretching: Stretching) {
    Column(Modifier.fillMaxWidth()) {
        Row {
            SmallText(text = "$index.", fontFamily = montserrat_bold)
            SpacerMini(horizontal = true)
            SmallText(text = stretching.description)
        }
        SpacerMini()
        ImageGif(
            url = stretching.imageURL,
            modifier = Modifier.size(200.dp).align(Alignment.CenterHorizontally)
        )
    }
}
