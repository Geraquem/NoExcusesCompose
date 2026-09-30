package com.mmfsin.noexcusescompose.presentation.myroutines.routines

import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mmfsin.noexcusescompose.R
import com.mmfsin.noexcusescompose.domain.models.Routine
import com.mmfsin.noexcusescompose.domain.models.getExampleRoutines
import com.mmfsin.noexcusescompose.presentation.core.components.BigText
import com.mmfsin.noexcusescompose.presentation.core.components.CustomToolbar
import com.mmfsin.noexcusescompose.presentation.core.components.ErrorDialog
import com.mmfsin.noexcusescompose.presentation.core.components.LoadingLottie
import com.mmfsin.noexcusescompose.presentation.core.components.MediumText
import com.mmfsin.noexcusescompose.presentation.core.components.OutlinedButtonCustom
import com.mmfsin.noexcusescompose.presentation.core.components.OutlinedButtonCustomIcon
import com.mmfsin.noexcusescompose.presentation.core.components.SmallText
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerMedium
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerSmall
import com.mmfsin.noexcusescompose.presentation.core.theme.Black
import com.mmfsin.noexcusescompose.presentation.core.theme.BlueLight
import com.mmfsin.noexcusescompose.presentation.core.theme.GrayMedium
import com.mmfsin.noexcusescompose.presentation.core.theme.White
import com.mmfsin.noexcusescompose.presentation.core.theme.montserrat_bold
import com.mmfsin.noexcusescompose.presentation.myroutines.days.sheet.DaysSheet
import com.mmfsin.noexcusescompose.presentation.myroutines.routines.components.CreateRoutineDialog

@Preview
@Composable
fun MyRoutinesPV() {
    MyRoutinesContent(
        uiStates = MyRoutinesStates(
            isLoading = false,
            myRoutines = getExampleRoutines()
        ),
        {}, {}, { _, _ -> }, { _, _ -> },
        {}, {}, {},
    )
}

@Composable
fun MyRoutinesScreen(
    viewModel: MyRoutinesViewModel = hiltViewModel(),
    goBack: () -> Unit,
    goToDayDetail: (String, String?) -> Unit,
) {
    val uiStates by viewModel.uiState.collectAsStateWithLifecycle()

    MyRoutinesContent(
        uiStates = uiStates,
        goBack = { goBack() },
        showCreateRoutineDialog = { viewModel.showCreateRoutineDialog(it) },
        createRoutine = { n, d -> viewModel.createRoutine(n, d) },
        routineClicked = { id, name -> viewModel.routineClicked(id, name) },
        goToDayDetail = { dayId ->
            uiStates.routineIdClicked?.let { routineId -> goToDayDetail(routineId, dayId) }
        },

        updatePinnedRoutine = { },
        sww = { viewModel.sww(it) },
    )
}

@Composable
fun MyRoutinesContent(
    uiStates: MyRoutinesStates,
    goBack: () -> Unit,
    showCreateRoutineDialog: (Boolean) -> Unit,
    createRoutine: (String, String?) -> Unit,
    routineClicked: (String?, String) -> Unit,
    goToDayDetail: (String?) -> Unit,

    updatePinnedRoutine: (String) -> Unit,
    sww: (Boolean) -> Unit,
) {
    Scaffold(
        topBar = {
            CustomToolbar(
                goBack = { goBack() },
                title = R.string.my_routines_toolbar
            )
        }
    ) { innerPadding ->
        Column(
            Modifier.fillMaxSize()
                .background(GrayMedium)
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .padding(bottom = 12.dp)
        ) {
            if (uiStates.myRoutines.isEmpty()) {
                EmptyRoutines(
                    createRoutine = { showCreateRoutineDialog(true) }
                )
            } else {
                CompositionLocalProvider(
                    LocalOverscrollFactory provides null
                ) {
                    LazyColumn(
                        state = rememberLazyListState(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(vertical = 16.dp)
                    ) {
                        items(
                            items = uiStates.myRoutines,
                            key = { routine -> routine.id }
                        ) { routine ->
                            RoutineBox(
                                routine = routine,
                                onClick = { routineClicked(routine.id, routine.name) },
                                updatePushpin = { updatePinnedRoutine(routine.id) },
                            )
                        }

                        item {
                            SpacerSmall()
                            OutlinedButtonCustomIcon(
                                onClick = { showCreateRoutineDialog(true) },
                                text = R.string.my_routines_create,
                                icon = R.drawable.ic_add,
                                modifier = Modifier.fillMaxWidth(),
                                color = Black
                            )
                        }
                    }
                }
            }
        }

        if (uiStates.showCreateRoutineDialog) {
            CreateRoutineDialog(
                onDismiss = { showCreateRoutineDialog(false) },
                create = { n, d -> createRoutine(n, d) }
            )
        }

        if (uiStates.routineIdClicked != null) {
            DaysSheet(
                onDismiss = { routineClicked(null, "") },
                routineId = uiStates.routineIdClicked,
                routineName = uiStates.routineClicked,
                onDayClick = { dayId -> goToDayDetail(dayId) },
            )
        }

        if (uiStates.sww) ErrorDialog { sww(false) }
        if (uiStates.isLoading) LoadingLottie()
    }
}

@Composable
fun RoutineBox(
    routine: Routine,
    onClick: () -> Unit,
    updatePushpin: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = White
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 12.dp),
        ) {
            Column(
                modifier = Modifier
                    .width(54.dp)
                    .background(BlueLight, RoundedCornerShape(8.dp))
                    .padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BigText(
                    text = "${routine.daysCount}",
                    fontFamily = montserrat_bold,
                    color = White
                )
                SmallText(
                    text = if (routine.daysCount == 1) "DÍA" else "DÍAS",
                    fontFamily = montserrat_bold,
                    allCaps = true,
                    color = White
                )
            }

            SpacerSmall(horizontal = true)

            Column(
                Modifier.weight(1f)
                    .background(White, RoundedCornerShape(12.dp))
                    .clip(RoundedCornerShape(12.dp))
                    .align(Alignment.CenterVertically)
            ) {
                Row(verticalAlignment = Alignment.Top) {
                    MediumText(
                        text = routine.name,
                        modifier = Modifier.weight(1f),
                        fontFamily = montserrat_bold
                    )
                }

                routine.description?.let { d ->
                    MediumText(text = d)
                }
            }

            val pushpin = if (routine.doingIt) R.drawable.ic_pushpin else R.drawable.ic_pushpin_off
            Image(
                painterResource(pushpin), null,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable(onClick = { updatePushpin() })
            )
        }
    }
}

@Composable
fun EmptyRoutines(createRoutine: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 42.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(1f))
        MediumText(
            text = R.string.my_routines_empty,
            gravity = TextAlign.Center,
        )
        SpacerMedium()
        OutlinedButtonCustom(
            onClick = { createRoutine() },
            text = R.string.my_routines_create,
            color = Black
        )
        Spacer(Modifier.weight(1f))
    }
}