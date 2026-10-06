@file:OptIn(ExperimentalMaterial3Api::class)

package com.mmfsin.noexcusescompose.presentation.myroutines.days

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mmfsin.noexcusescompose.R
import com.mmfsin.noexcusescompose.domain.models.ExerciseRtn
import com.mmfsin.noexcusescompose.domain.models.MuscularGroupType.Companion.getMuscularGroupColor
import com.mmfsin.noexcusescompose.domain.models.Serie
import com.mmfsin.noexcusescompose.domain.models.getExerciseRtnExamples
import com.mmfsin.noexcusescompose.presentation.core.components.CustomTextField
import com.mmfsin.noexcusescompose.presentation.core.components.CustomToolbar
import com.mmfsin.noexcusescompose.presentation.core.components.ErrorDialog
import com.mmfsin.noexcusescompose.presentation.core.components.ImageGif
import com.mmfsin.noexcusescompose.presentation.core.components.MediumText
import com.mmfsin.noexcusescompose.presentation.core.components.OutlinedButtonCustomIcon
import com.mmfsin.noexcusescompose.presentation.core.components.SmallText
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerCustom
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerMedium
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerMini
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerSmall
import com.mmfsin.noexcusescompose.presentation.core.theme.Black
import com.mmfsin.noexcusescompose.presentation.core.theme.GrayLight
import com.mmfsin.noexcusescompose.presentation.core.theme.GrayMedium
import com.mmfsin.noexcusescompose.presentation.core.theme.RedHard
import com.mmfsin.noexcusescompose.presentation.core.theme.White
import com.mmfsin.noexcusescompose.presentation.core.theme.alphazet
import com.mmfsin.noexcusescompose.presentation.core.theme.montserrat_bold

@Preview
@Composable
fun DayScreenPV() {
    DayContent(
        uiStates = DayStates(
            isLoading = false,
            dayName = "Pecho y tríceps",
            exercises = getExerciseRtnExamples()
        ),
        {}, {}, {}, {},
        {}, {}, {}
    )
}

@Composable
fun DayScreen(
    viewModel: DayViewModel = hiltViewModel(),
    goBack: () -> Unit,
    goToMuscularGroups: (String, String) -> Unit
) {
    val uiStates by viewModel.uiState.collectAsStateWithLifecycle()

    DayContent(
        uiStates = uiStates,
        goBack = { goBack() },
        updateDayName = { viewModel.updateDayName(it) },
        createDay = { viewModel.createOrEditDay() },
        handleBack = { viewModel.handleBack() },
        goToMuscularGroups = { goToMuscularGroups(uiStates.dayId, uiStates.dayName) },
        showEditExerciseRtn = { viewModel.showEditExerciseRtn(it) },
        sww = { viewModel.sww(it) },
    )
}

@Composable
fun DayContent(
    uiStates: DayStates,
    goBack: () -> Unit,
    updateDayName: (String) -> Unit,
    createDay: () -> Unit,
    handleBack: () -> Unit,
    goToMuscularGroups: () -> Unit,
    showEditExerciseRtn: (String) -> Unit,
    sww: (Boolean) -> Unit
) {

    val listState = rememberLazyListState()

    Scaffold(
        topBar = {
            CustomToolbar(
                goBack = { handleBack() },
                titleString = uiStates.routineName,
                iconRight = R.drawable.ic_add_circle,
                showIconRight = true,
                iconRightClick = { createDay() }
            )
        }
    ) { innerPadding ->
        Box(
            Modifier.fillMaxSize()
                .background(GrayMedium)
                .padding(innerPadding)
                .padding(horizontal = 12.dp)
                .padding(bottom = 12.dp)
        ) {

            CompositionLocalProvider(
                LocalOverscrollFactory provides null
            ) {
                LazyColumn(
                    state = listState,
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    item {
                        if (uiStates.emptyNameError) {
                            SmallText(
                                text = R.string.my_routines_day_error_empty_name,
                                fontFamily = montserrat_bold,
                                color = RedHard,
                            )
                            SpacerSmall()
                        }

                        Row {
                            MediumText(
                                text = R.string.my_routines_day,
                                fontFamily = montserrat_bold,
                                modifier = Modifier.padding(start = 6.dp)
                            )

                            SpacerMini(horizontal = true)

                            if (uiStates.dayOrder != -1) {
                                MediumText(
                                    text = "${uiStates.dayOrder}",
                                    fontFamily = montserrat_bold,
                                )
                            }
                        }

                        SpacerMini()
                        CustomTextField(
                            value = uiStates.dayName,
                            onValueChange = { updateDayName(it) },
                            lengthVisibility = true,
                        )

                        SpacerSmall()

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            MediumText(
                                text = R.string.my_routines_day_exercises,
                                fontFamily = montserrat_bold,
                                modifier = Modifier.padding(start = 6.dp)
                            )

                            if (uiStates.isLoading) {
                                SpacerSmall(horizontal = true)
                                CircularProgressIndicator(
                                    Modifier.size(20.dp),
                                    strokeWidth = 3.dp,
                                    color = Black,
                                    strokeCap = StrokeCap.Round
                                )
                            }
                        }
                        SpacerSmall()
                    }

                    itemsIndexed(
                        items = uiStates.exercises,
                        key = { _, exercise -> exercise.id }
                    ) { i, exerciseRtn ->
                        ExerciseRtnBox(
                            position = i,
                            exerciseRtn = exerciseRtn,
                            onEditClick = { showEditExerciseRtn(exerciseRtn.id) }
                        )

                        if (!exerciseRtn.superSerie) SpacerCustom(12.dp)
                        else SuperSerie()
                    }

                    item {
                        SpacerMedium()
                        OutlinedButtonCustomIcon(
                            onClick = { goToMuscularGroups() },
                            text = R.string.my_routines_day_add_exercises,
                            icon = R.drawable.ic_add,
                            modifier = Modifier.fillMaxWidth(),
                            color = Black
                        )
                        SpacerMedium()
                    }
                }
            }
        }

        if (uiStates.exerciseRtnIdToEdit != null) {

        }

        if (uiStates.shouldGoBack) goBack()
        if (uiStates.sww) ErrorDialog { sww(false) }
    }
    BackHandler {
        handleBack()
    }
}

@Composable
fun ExerciseRtnBox(
    position: Int,
    exerciseRtn: ExerciseRtn,
    onEditClick: () -> Unit
) {

    var expanded by rememberSaveable { mutableStateOf(false) }

    exerciseRtn.exercise?.let { e ->
        Column(
            modifier = Modifier.fillMaxWidth()
                .background(color = White, shape = RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .clickable(onClick = { if (exerciseRtn.series.isNotEmpty()) expanded = !expanded })
                .padding(bottom = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 8.dp, end = 4.dp).padding(top = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ImageGif(
                    url = e.gifURL,
                    modifier = Modifier.size(70.dp).padding(top = 8.dp)
                )

                SpacerSmall(horizontal = true)

                Column(
                    modifier = Modifier.weight(1f).padding(top = 8.dp)
                ) {
                    SmallText(
                        text = e.category.replaceFirstChar { it.uppercase() },
                        modifier = Modifier.width(150.dp).background(
                            color = getMuscularGroupColor(e.category),
                            shape = RoundedCornerShape(8.dp)
                        ),
                        gravity = TextAlign.Center
                    )

                    SpacerMini()

                    Row() {
                        MediumText(
                            text = "${position + 1}.",
                            fontFamily = montserrat_bold
                        )
                        SpacerMini(horizontal = true)
                        MediumText(
                            text = e.name
                        )
                    }
                    SpacerMini()

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (exerciseRtn.series.isNotEmpty()) {
                            val series = when (exerciseRtn.series.size) {
                                1 -> stringResource(R.string.my_routines_day_exercises_serie, exerciseRtn.series.size.toString())
                                else -> stringResource(R.string.my_routines_day_exercises_series, exerciseRtn.series.size.toString())
                            }
                            SmallText(text = series, fontFamily = alphazet)
                        }
                        SpacerSmall(horizontal = true)

                        exerciseRtn.rest?.let { rest ->
                            val rest = stringResource(R.string.my_routines_day_exercises_rest, rest)
                            SmallText(text = rest, fontFamily = alphazet)
                        }
                    }
                }

                IconButton(
                    onClick = { onEditClick() },
                    modifier = Modifier.align(Alignment.Top)
                ) {
                    Icon(painterResource(R.drawable.ic_edit), null)
                }
            }

            AnimatedVisibility(expanded) {
                Column(Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
                    ShowSerieHeader()
                    exerciseRtn.series.forEachIndexed { i, serie ->
                        SpacerMini()
                        ShowSerie(i + 1, serie)
                    }
                }
            }

            exerciseRtn.notes?.let { notes ->
                SpacerSmall()
                MediumText(
                    text = notes,
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .background(GrayLight, RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 6.dp)
                )
            }
        }
    }
}

@Composable
fun SuperSerie() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Spacer(Modifier.weight(1f))

        Icon(
            painterResource(R.drawable.ic_arrow_up), null,
            modifier = Modifier.size(18.dp).graphicsLayer { scaleY = -1f })

        SpacerSmall(horizontal = true)

        SmallText(
            text = R.string.my_routines_day_exercises_super_serie,
            allCaps = true,
            fontFamily = montserrat_bold
        )

        SpacerSmall(horizontal = true)

        Icon(
            painterResource(R.drawable.ic_arrow_up), null,
            modifier = Modifier.size(18.dp)
        )

        Spacer(Modifier.weight(1f))
    }
}


@Composable
fun ShowSerieHeader() {
    Row(modifier = Modifier.fillMaxWidth()) {
        MediumText(
            text = R.string.empty,
            fontFamily = montserrat_bold,
            modifier = Modifier.width(64.dp)
        )
        Row(modifier = Modifier.weight(1f)) {
            MediumText(
                text = R.string.my_routines_exercises_reps,
                fontFamily = montserrat_bold,
                gravity = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
            MediumText(
                text = R.string.my_routines_exercises_kgs,
                fontFamily = montserrat_bold,
                gravity = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun ShowSerie(i: Int, serie: Serie) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        MediumText(
            text = stringResource(R.string.my_routines_exercises_serie, "$i"),
            modifier = Modifier.width(64.dp)
        )

        SpacerSmall(horizontal = true)

        MediumText(
            text = serie.reps?.toString() ?: "0",
            gravity = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )

        SpacerSmall(horizontal = true)

        MediumText(
            text = serie.kgs ?: "0",
            gravity = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )
    }
}