package com.mmfsin.noexcuses.presentation.exercises.mgroups

import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.mmfsin.noexcuses.domain.models.MuscularGroup
import com.mmfsin.noexcuses.domain.models.getMuscularGroupsExamples
import com.mmfsin.noexcuses.presentation.core.components.CustomToolbar
import com.mmfsin.noexcuses.presentation.core.components.ErrorDialog
import com.mmfsin.noexcuses.presentation.core.components.MediumText
import com.mmfsin.noexcuses.presentation.core.theme.GrayMedium
import com.mmfsin.noexcuses.presentation.core.theme.White
import com.mmfsin.noexcuses.presentation.core.theme.montserrat_bold

@Preview
@Composable
fun MGroupsScreenPV() {
    MGroupsContent(
        uiStates = MGroupsStates(
            isLoading = true,
            muscularGroups = getMuscularGroupsExamples()
        ),
        {}, {},
    )
}

@Composable
fun MGroupsScreen(
    viewModel: MGroupsViewModel = hiltViewModel(),
    goBack: () -> Unit,
    goToExercises: (String) -> Unit,
) {
    val uiStates by viewModel.uiState.collectAsStateWithLifecycle()

    MGroupsContent(
        uiStates = uiStates,
        goBack = { goBack() },
        goToExercises = { goToExercises(it) },
    )
}

@Composable
fun MGroupsContent(
    uiStates: MGroupsStates,
    goBack: () -> Unit,
    goToExercises: (String) -> Unit,
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
                .padding(horizontal = 12.dp)
        ) {
            MuscularGroupList(
                muscularGroups = uiStates.muscularGroups,
                goToExercises = { mGroupId -> goToExercises(mGroupId) }
            )
        }
    }

    if (uiStates.sww) ErrorDialog { goBack() }
}

@Composable
fun MuscularGroupList(
    muscularGroups: List<MuscularGroup>,
    goToExercises: (String) -> Unit
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
                items = muscularGroups,
                key = { mg -> mg.id }
            ) { muscularGroup ->
                MuscularGroupBox(
                    muscularGroup = muscularGroup,
                    onClick = { mGroupId -> goToExercises(mGroupId) }
                )
            }
        }
    }
}

@Composable
fun MuscularGroupBox(
    muscularGroup: MuscularGroup,
    onClick: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Box(
            Modifier.height(100.dp).clickable(onClick = { onClick(muscularGroup.id) }),
        ) {
            AsyncImage(
                model = muscularGroup.longImageURL,
                contentDescription = muscularGroup.name,
                modifier = Modifier.fillMaxSize().alpha(0.8f),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF474747).copy(alpha = 0.90f),
                                Color(0xFF525252).copy(alpha = 0.85f),
                                Color(0xFFACACAC).copy(alpha = 0.20f),
                                Color.Transparent
                            ),
                            startX = 0f,
                            endX = 850f
                        )
                    )
            )

            MediumText(
                text = muscularGroup.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                color = White,
                allCaps = true,
                fontSize = 26.sp,
                fontFamily = montserrat_bold
            )
        }
    }
}
