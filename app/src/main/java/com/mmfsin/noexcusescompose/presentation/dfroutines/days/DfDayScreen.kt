package com.mmfsin.noexcusescompose.presentation.dfroutines.days

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.StrokeCap
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
import com.mmfsin.noexcusescompose.domain.models.getExerciseRtnExamples
import com.mmfsin.noexcusescompose.presentation.core.components.CustomToolbar
import com.mmfsin.noexcusescompose.presentation.core.components.ErrorDialog
import com.mmfsin.noexcusescompose.presentation.core.components.ImageGif
import com.mmfsin.noexcusescompose.presentation.core.components.MediumText
import com.mmfsin.noexcusescompose.presentation.core.components.SmallText
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerCustom
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerLarge
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerMini
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerSmall
import com.mmfsin.noexcusescompose.presentation.core.theme.Black
import com.mmfsin.noexcusescompose.presentation.core.theme.GrayHard
import com.mmfsin.noexcusescompose.presentation.core.theme.GrayLight
import com.mmfsin.noexcusescompose.presentation.core.theme.GrayMedium
import com.mmfsin.noexcusescompose.presentation.core.theme.White
import com.mmfsin.noexcusescompose.presentation.core.theme.montserrat_bold
import com.mmfsin.noexcusescompose.presentation.myroutines.days.SuperSerie

@Preview
@Composable
fun DfDayPV() {
    DfDayContent(
        uiStates = DfDayStates(
            isLoading = false,
            dayName = "Pecho, hombro y espalda",
            exercises = getExerciseRtnExamples()
        ),
        {}, {}
    )
}

@Composable
fun DfDayScreen(
    viewModel: DfDayViewModel = hiltViewModel(),
    goBack: () -> Unit,
    goToExerciseDetail: (String) -> Unit,
) {
    val uiStates by viewModel.uiState.collectAsStateWithLifecycle()

    DfDayContent(
        uiStates = uiStates,
        goBack = { goBack() },
        goToExerciseDetail = { goToExerciseDetail(it) },
    )
}

@Composable
fun DfDayContent(
    uiStates: DfDayStates,
    goBack: () -> Unit,
    goToExerciseDetail: (String) -> Unit,
) {
    val lazyListState = rememberLazyListState()

    Scaffold(
        topBar = {
            CustomToolbar(
                goBack = { goBack() },
                titleString = uiStates.routineName,
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
                    state = lazyListState,
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    item {
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

                        Box(
                            Modifier.fillMaxWidth()
                                .background(White, RoundedCornerShape(8.dp))
                                .padding(12.dp)
                        ) {
                            MediumText(
                                text = uiStates.dayName
                            )
                        }

                        SpacerLarge()

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

                            Spacer(Modifier.weight(1f))

                            val total = uiStates.exercises.size
                            if (total != 0) {
                                Row(
                                    modifier = Modifier.padding(start = 6.dp)
                                        .background(GrayLight, RoundedCornerShape(8.dp))
                                        .padding(horizontal = 16.dp, vertical = 2.dp)
                                ) {
                                    SmallText(text = "$total")
                                    SpacerMini(horizontal = true)
                                    SmallText(
                                        text = if (total == 1) R.string.my_routines_day_exercise
                                        else R.string.my_routines_day_exercises
                                    )
                                }
                            }
                        }
                        SpacerSmall()
                    }

                    itemsIndexed(
                        items = uiStates.exercises,
                        key = { _, exercise -> exercise.id }
                    ) { i, exerciseRtn ->
                        Column {
                            DfExerciseRtnBox(
                                position = i,
                                exerciseRtn = exerciseRtn,
                                toExerciseDetail = { goToExerciseDetail(exerciseRtn.exerciseId) }
                            )

                            if (!exerciseRtn.superSerie) SpacerCustom(12.dp)
                            else SuperSerie()
                        }
                    }
                }
            }
        }

        if (uiStates.sww) ErrorDialog { goBack() }
    }
}


@Composable
fun DfExerciseRtnBox(
    position: Int,
    exerciseRtn: ExerciseRtn,
    toExerciseDetail: () -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    exerciseRtn.exercise?.let { e ->

        val reps = exerciseRtn.notes?.split(",")

        Column(
            modifier = Modifier.fillMaxWidth()
                .background(color = White, shape = RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .clickable(onClick = { expanded = !expanded })
                .padding(bottom = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 8.dp, end = 4.dp).padding(top = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ImageGif(
                    url = e.gifURL,
                    modifier = Modifier.size(70.dp).padding(top = 8.dp).align(Alignment.Top)
                )

                SpacerSmall(horizontal = true)

                Column(
                    modifier = Modifier.weight(1f).padding(top = 8.dp)
                ) {
                    SmallText(
                        text = e.category.replaceFirstChar { it.uppercase() },
                        modifier = Modifier.fillMaxWidth().background(
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

                    SpacerSmall()

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painterResource(R.drawable.ic_series), null,
                            tint = GrayHard,
                            modifier = Modifier.size(16.dp)
                        )

                        SpacerMini(horizontal = true)

                        val series = when (reps?.size) {
                            1 -> stringResource(R.string.my_routines_day_exercises_serie, reps.size.toString())
                            else -> stringResource(R.string.my_routines_day_exercises_series, reps?.size.toString())
                        }
                        SmallText(text = series)
                        SpacerSmall(horizontal = true)

                        exerciseRtn.rest?.let { rest ->
                            Icon(
                                painterResource(R.drawable.ic_rest), null,
                                tint = GrayHard,
                                modifier = Modifier.size(16.dp)
                            )

                            SpacerMini(horizontal = true)

                            val rest = stringResource(R.string.my_routines_day_exercises_rest, rest)
                            SmallText(text = rest)
                        }
                    }

                    AnimatedVisibility(expanded) {
                        Column(Modifier.padding(top = 8.dp)) {
                            reps?.forEachIndexed { i, reps ->
                                SpacerMini()
                                DfSerie(i + 1, reps)
                            }
                        }
                    }
                }

                SpacerMini(horizontal = true)

                Icon(
                    painterResource(R.drawable.ic_eye), null,
                    tint = GrayHard,
                    modifier = Modifier.size(28.dp)
                        .align(Alignment.Top)
                        .clip(CircleShape)
                        .clickable(onClick = { toExerciseDetail() })
                        .padding(top = 4.dp)
                        .padding(end = 4.dp)
                        .background(GrayMedium, CircleShape)
                        .padding(4.dp)
                )
            }
        }
    }
}

@Composable
fun DfSerie(i: Int, reps: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SmallText(
            text = stringResource(R.string.my_routines_exercises_serie, "$i"),
            modifier = Modifier.width(64.dp)
        )
        SmallText(
            text = stringResource(R.string.df_routine_exercises_reps, reps),
            modifier = Modifier.width(80.dp).align(Alignment.CenterVertically)
        )
    }
}