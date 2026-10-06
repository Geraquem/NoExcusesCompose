package com.mmfsin.noexcusescompose.presentation.myroutines.routines

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import com.mmfsin.noexcusescompose.domain.models.Day
import com.mmfsin.noexcusescompose.domain.models.Routine
import com.mmfsin.noexcusescompose.domain.models.getExampleRoutines
import com.mmfsin.noexcusescompose.presentation.core.components.CustomToolbar
import com.mmfsin.noexcusescompose.presentation.core.components.ErrorDialog
import com.mmfsin.noexcusescompose.presentation.core.components.LoadingLottie
import com.mmfsin.noexcusescompose.presentation.core.components.MediumText
import com.mmfsin.noexcusescompose.presentation.core.components.OutlinedButtonCustom
import com.mmfsin.noexcusescompose.presentation.core.components.OutlinedButtonCustomIcon
import com.mmfsin.noexcusescompose.presentation.core.components.SmallText
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerLarge
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerMini
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerSmall
import com.mmfsin.noexcusescompose.presentation.core.theme.Black
import com.mmfsin.noexcusescompose.presentation.core.theme.BlueLight
import com.mmfsin.noexcusescompose.presentation.core.theme.GrayMedium
import com.mmfsin.noexcusescompose.presentation.core.theme.OrangeHard
import com.mmfsin.noexcusescompose.presentation.core.theme.White
import com.mmfsin.noexcusescompose.presentation.core.theme.montserrat_bold
import com.mmfsin.noexcusescompose.presentation.myroutines.routines.components.CreateRoutineDialog
import com.mmfsin.noexcusescompose.presentation.myroutines.routines.components.DeleteRoutineDialog
import com.mmfsin.noexcusescompose.presentation.myroutines.routines.components.EditRoutineDialog

@Preview
@Composable
fun MyRoutinesPV() {
    MyRoutinesContent(
        uiStates = MyRoutinesStates(
            isLoading = false,
            myRoutines = getExampleRoutines(),
        ),
        {}, {}, { _, _ -> }, {},
        { _, _ -> }, {}, {}, { _, _ -> },
        {}, {},
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
        createRoutine = { name, description -> viewModel.createRoutine(name, description) },
        routineToEdit = { viewModel.routineToEdit(it) },
        editRoutine = { name, description -> viewModel.editRoutine(name, description) },
        showDeleteRoutineDialog = { viewModel.showDeleteRoutineDialog(it) },
        deleteRoutine = { viewModel.deleteRoutine() },

        goToDayDetail = { routineId, dayId -> goToDayDetail(routineId, dayId) },

        updatePinnedRoutine = { viewModel.updatePushpin(it) },
        sww = { viewModel.sww(it) },
    )
}

@Composable
fun MyRoutinesContent(
    uiStates: MyRoutinesStates,
    goBack: () -> Unit,

    showCreateRoutineDialog: (Boolean) -> Unit,
    createRoutine: (String, String?) -> Unit,
    routineToEdit: (Routine?) -> Unit,
    editRoutine: (String, String?) -> Unit,
    showDeleteRoutineDialog: (Boolean) -> Unit,
    deleteRoutine: () -> Unit,

    goToDayDetail: (String, String?) -> Unit,

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
                                editRoutine = { routineToEdit(routine) },
                                goToDayDetail = { dayId -> goToDayDetail(routine.id, dayId) },
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
                createRoutine = { name, description -> createRoutine(name, description) }
            )
        }

        if (uiStates.routineToEdit != null) {
            EditRoutineDialog(
                routine = uiStates.routineToEdit,
                onDismiss = { routineToEdit(null) },
                deleteRoutine = { showDeleteRoutineDialog(true) },
                editRoutine = { name, description -> editRoutine(name, description) }
            )
        }

        if (uiStates.showDeleteRoutineDialog) {
            DeleteRoutineDialog(
                routineName = uiStates.routineToEdit?.name ?: "",
                cancel = { showDeleteRoutineDialog(false) },
                delete = { deleteRoutine() }
            )
        }

        if (uiStates.sww) ErrorDialog { sww(false) }
        if (uiStates.isLoading) LoadingLottie()
    }
}

@Composable
fun RoutineBox(
    routine: Routine,
    editRoutine: () -> Unit,
    goToDayDetail: (String?) -> Unit,
    updatePushpin: () -> Unit
) {

    var expanded by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth()
            .combinedClickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = { expanded = !expanded },
                onLongClick = { editRoutine() }
            ),
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
                    Row(
                        modifier = Modifier
                            .width(100.dp)
                            .background(BlueLight, RoundedCornerShape(8.dp))
                            .padding(vertical = 2.dp, horizontal = 12.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        MediumText(
                            text = "${routine.days.size}",
                            fontFamily = montserrat_bold
                        )
                        SpacerMini(horizontal = true)
                        MediumText(
                            text = if (routine.days.size == 1) R.string.my_routines_day else R.string.my_routines_days,
                        )
                    }

                    SpacerSmall()

                    MediumText(
                        text = routine.name,
                        fontFamily = montserrat_bold
                    )

                    routine.description?.let { d ->
                        SpacerMini()
                        MediumText(text = d)
                    }
                }

                val pushpin = if (routine.pinned) R.drawable.ic_pushpin else R.drawable.ic_pushpin_off
                Image(
                    painterResource(pushpin), null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable(onClick = { updatePushpin() })
                )
            }

            AnimatedVisibility(expanded) {
                Column(Modifier.fillMaxWidth()) {
                    SpacerSmall()

                    routine.days.sortedBy { it.order }.forEach { day ->
                        DayBox(
                            day = day,
                            onDayClick = { goToDayDetail(day.id) })
                    }

                    TextButton(
                        onClick = { goToDayDetail(null) },
                        modifier = Modifier.align(Alignment.End)
                            .padding(end = 6.dp)
                    ) {
                        SmallText(
                            text = R.string.my_routines_create_day,
                            allCaps = true,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DayBox(
    day: Day,
    onDayClick: () -> Unit
) {
    Row(
        Modifier.fillMaxWidth()
            .clickable(onClick = { onDayClick() })
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .border(1.dp, color = OrangeHard, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            SmallText(
                text = "Día ${day.order + 1}",
                fontFamily = montserrat_bold,
            )
            MediumText(text = day.name)
        }
    }
}

@Composable
fun EmptyRoutines(createRoutine: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 42.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SpacerLarge()
        SmallText(
            text = R.string.my_routines_empty,
            gravity = TextAlign.Center,
        )
        SpacerSmall()
        OutlinedButtonCustom(
            onClick = { createRoutine() },
            text = R.string.my_routines_create,
            color = Black
        )
        Spacer(Modifier.weight(1f))
    }
}