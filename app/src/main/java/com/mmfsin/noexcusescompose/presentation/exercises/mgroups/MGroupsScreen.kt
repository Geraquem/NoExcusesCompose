package com.mmfsin.noexcusescompose.presentation.exercises.mgroups

import androidx.activity.compose.LocalActivity
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.mmfsin.noexcusescompose.domain.models.MuscularGroup
import com.mmfsin.noexcusescompose.domain.models.MuscularGroupType.Companion.getMuscularGroupColor
import com.mmfsin.noexcusescompose.domain.models.getMuscularGroupsExamples
import com.mmfsin.noexcusescompose.presentation.core.components.CustomToolbar
import com.mmfsin.noexcusescompose.presentation.core.components.MediumText
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerSmall
import com.mmfsin.noexcusescompose.presentation.core.theme.Black
import com.mmfsin.noexcusescompose.presentation.core.theme.GrayMedium
import com.mmfsin.noexcusescompose.presentation.core.theme.montserrat_bold

@Preview
@Composable
fun MGroupsScreenPV() {
    MGroupsContent(
        uiStates = MGroupsStates(
            isLoading = true,
            muscularGroups = getMuscularGroupsExamples()
        ),
        {}, {}
    )
}

@Composable
fun MGroupsScreen(
    viewModel: MGroupsViewModel = hiltViewModel(),
    goToExercises: (String) -> Unit
) {
    val uiStates by viewModel.uiState.collectAsStateWithLifecycle()
    val activity = LocalActivity.current

    MGroupsContent(
        uiStates = uiStates,
        goBack = { activity?.finish() },
        goToExercises = { goToExercises(it) }
    )
}

@Composable
fun MGroupsContent(
    uiStates: MGroupsStates,
    goBack: () -> Unit,
    goToExercises: (String) -> Unit
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
            MuscularGroupList(
                muscularGroups = uiStates.muscularGroups,
                goToExercises = { mGroupId -> goToExercises(mGroupId) }
            )
        }
    }
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
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = muscularGroup.manImageURL,
                contentDescription = muscularGroup.name,
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp, horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    Modifier.size(14.dp).background(
                        getMuscularGroupColor(muscularGroup.id),
                        shape = CircleShape
                    )
                )

                SpacerSmall(horizontal = true)

                MediumText(
                    text = muscularGroup.name,
                    color = Black,
                    allCaps = true,
                    fontFamily = montserrat_bold
                )
            }
        }
    }
}
