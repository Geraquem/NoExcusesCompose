package com.mmfsin.noexcusescompose.presentation.dfroutines.routines

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mmfsin.noexcusescompose.R
import com.mmfsin.noexcusescompose.domain.models.Routine
import com.mmfsin.noexcusescompose.domain.models.getExampleRoutines
import com.mmfsin.noexcusescompose.presentation.core.components.CustomToolbar
import com.mmfsin.noexcusescompose.presentation.core.components.ErrorDialog
import com.mmfsin.noexcusescompose.presentation.core.components.LoadingLottie
import com.mmfsin.noexcusescompose.presentation.core.components.MediumText
import com.mmfsin.noexcusescompose.presentation.core.components.SmallText
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerMedium
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerSmall
import com.mmfsin.noexcusescompose.presentation.core.theme.GrayHard
import com.mmfsin.noexcusescompose.presentation.core.theme.GrayMedium
import com.mmfsin.noexcusescompose.presentation.core.theme.GreenLight
import com.mmfsin.noexcusescompose.presentation.core.theme.White
import com.mmfsin.noexcusescompose.presentation.core.theme.montserrat_bold
import com.mmfsin.noexcusescompose.presentation.dfroutines.routines.component.AddDfRoutineDialog
import com.mmfsin.noexcusescompose.presentation.myroutines.routines.DayBox

@Preview
@Composable
fun DfRoutinesPV() {
    DfRoutinesContent(
        uiStates = DfRoutinesStates(
            isLoading = false,
            routineAdded = true,
            routines = getExampleRoutines()
        ),
        {}, {}, { _, _ -> }, {}, {}
    )
}

@Composable
fun DfRoutinesScreen(
    viewModel: DfRoutinesViewModel = hiltViewModel(),
    goBack: () -> Unit,
    goToDayDetail: (String, String) -> Unit
) {
    val uiStates by viewModel.uiState.collectAsStateWithLifecycle()

    DfRoutinesContent(
        uiStates = uiStates,
        goBack = { goBack() },
        getDays = { viewModel.getDays(it) },
        goToDayDetail = { routineId, dayId -> goToDayDetail(routineId, dayId) },
        updateRoutineToAdd = { viewModel.updateRoutineToAdd(it) },
        addRoutine = { viewModel.addRoutine() },
    )
}

@Composable
fun DfRoutinesContent(
    uiStates: DfRoutinesStates,
    goBack: () -> Unit,
    getDays: (String) -> Unit,
    goToDayDetail: (String, String) -> Unit,
    updateRoutineToAdd: (Routine?) -> Unit,
    addRoutine: () -> Unit,
) {
    Scaffold(
        topBar = {
            CustomToolbar(
                goBack = { goBack() },
                title = R.string.df_routine_top_title
            )
        }
    ) { innerPadding ->
        Box(
            Modifier.fillMaxSize()
                .background(GrayMedium)
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Column(Modifier.fillMaxWidth()) {
                CompositionLocalProvider(
                    LocalOverscrollFactory provides null
                ) {
                    LazyColumn(
                        state = rememberLazyListState(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(vertical = 16.dp)
                    ) {
                        items(
                            items = uiStates.routines,
                            key = { routine -> routine.id }
                        ) { routine ->
                            DfRoutineBox(
                                routine = routine,
                                getDays = { getDays(routine.id) },
                                goToDayDetail = { dayId -> goToDayDetail(routine.id, dayId) },
                                addRoutine = { updateRoutineToAdd(routine) }
                            )
                        }
                    }
                }
            }

            AnimatedVisibility(
                visible = uiStates.routineAdded,
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Column {
                    RoutineAdded()
                    SpacerMedium()
                }
            }
        }

        if (uiStates.routineToAdd != null) {
            AddDfRoutineDialog(
                name = uiStates.routineToAdd.name,
                cancel = { updateRoutineToAdd(null) },
                accept = { addRoutine() }
            )
        }

        if (uiStates.sww) ErrorDialog { goBack() }
        if (uiStates.isLoading) LoadingLottie()
    }
}


@Composable
fun DfRoutineBox(
    routine: Routine,
    getDays: () -> Unit,
    goToDayDetail: (String) -> Unit,
    addRoutine: () -> Unit
) {

    var expanded by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = { expanded = !expanded },
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .padding(top = 12.dp)
            ) {
                Column(
                    Modifier.weight(1f)
                        .background(White, RoundedCornerShape(12.dp))
                        .align(Alignment.CenterVertically)
                ) {
                    Row {
                        Box(
                            modifier = Modifier.background(routine.color, RoundedCornerShape(8.dp)).padding(4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painterResource(R.drawable.ic_dumbbell), null,
                                modifier = Modifier.size(18.dp).rotate(145f)
                            )
                        }

                        SpacerSmall(horizontal = true)

                        Column {
                            MediumText(
                                text = routine.name,
                                fontFamily = montserrat_bold,
                                fontSize = 18.sp
                            )

                            routine.description?.let { d -> SmallText(text = d) }
                        }
                    }
                }

                Icon(
                    painterResource(R.drawable.ic_add_circle), null,
                    tint = GrayHard,
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable(onClick = { addRoutine() })
                )
            }

            AnimatedVisibility(expanded) {
                Column(Modifier.fillMaxWidth()) {
                    SpacerSmall()

                    if (routine.days.isEmpty()) getDays()
                    else {
                        routine.days.sortedBy { it.order }.forEachIndexed { i, day ->
                            DayBox(
                                position = i + 1,
                                day = day,
                                color = routine.color,
                                onDayClick = { goToDayDetail(day.id) },
                                onDayLongClick = { }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RoutineAdded() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = GreenLight),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 18.dp)
        ) {
            MediumText(text = R.string.df_routine_added)
            Spacer(Modifier.weight(1f))
            Icon(painterResource(R.drawable.ic_check), null)
        }
    }
}
