package com.mmfsin.noexcusescompose.presentation.myroutines.days.edit

import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mmfsin.noexcusescompose.R
import com.mmfsin.noexcusescompose.domain.models.Exercise
import com.mmfsin.noexcusescompose.domain.models.Serie
import com.mmfsin.noexcusescompose.domain.models.createSerie
import com.mmfsin.noexcusescompose.domain.models.getExercisesExamples
import com.mmfsin.noexcusescompose.presentation.core.components.ButtonCustom
import com.mmfsin.noexcusescompose.presentation.core.components.CustomTextField
import com.mmfsin.noexcusescompose.presentation.core.components.ImageGif
import com.mmfsin.noexcusescompose.presentation.core.components.MediumText
import com.mmfsin.noexcusescompose.presentation.core.components.OutlinedButtonCustom
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerMedium
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerMini
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerSmall
import com.mmfsin.noexcusescompose.presentation.core.theme.Black
import com.mmfsin.noexcusescompose.presentation.core.theme.BlueLight
import com.mmfsin.noexcusescompose.presentation.core.theme.BlueMedium
import com.mmfsin.noexcusescompose.presentation.core.theme.GrayHard
import com.mmfsin.noexcusescompose.presentation.core.theme.GrayLight
import com.mmfsin.noexcusescompose.presentation.core.theme.GrayMedium
import com.mmfsin.noexcusescompose.presentation.core.theme.RedHard
import com.mmfsin.noexcusescompose.presentation.core.theme.White
import com.mmfsin.noexcusescompose.presentation.core.theme.montserrat_bold
import com.mmfsin.noexcusescompose.presentation.myroutines.exercises.add.AddExerciseRtnStates
import com.mmfsin.noexcusescompose.presentation.myroutines.exercises.add.AddExerciseRtnViewModel

@Preview
@Composable
fun EditExerciseRtnDialogPV() {
    EditExerciseRtnContent(
        uiStates = EditExerciseRtnStates(
            series = listOf(createSerie(0), createSerie(1)),
            sww = false
        ),
        exercise = getExercisesExamples().first(),
        dayName = "Día 1",
        {}, {}, {}, {},
        { _, _ -> }, { _, _ -> }, {},
        {}, {}, {},
    )
}

@Composable
fun EditExerciseRtnDialog(
    viewModel: EditExerciseRtnViewModel = hiltViewModel(),
    exercise: Exercise,
    dayId: String,
    dayName: String,
    onDismiss: () -> Unit,
    seeExercise: () -> Unit,
) {

    fun close() {
        viewModel.resetData()
        onDismiss()
    }

    val uiStates by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(dayId) { viewModel.updateDayId(dayId, exercise.id) }

    EditExerciseRtnContent(
        uiStates = uiStates,
        exercise = exercise,
        dayName = dayName,
        onDismiss = { close() },
        seeExercise = { seeExercise() },
        addSerie = { viewModel.addSerie() },
        deleteSerie = { viewModel.deleteSerie(it) },
        updateSerieReps = { id, reps -> viewModel.updateSerieReps(id, reps) },
        updateSerieKgs = { id, kgs -> viewModel.updateSerieKgs(id, kgs) },
        updateRest = { viewModel.updateRest(it) },
        updateNotes = { viewModel.updateNotes(it) },
        updateSuperSerie = { viewModel.updateSuperSerie(it) },
        addExercise = { viewModel.addExerciseToDay() },
    )

    if (uiStates.goBack) close()
}

@Composable
fun EditExerciseRtnContent(
    uiStates: EditExerciseRtnStates,
    exercise: Exercise,
    dayName: String,
    onDismiss: () -> Unit,
    seeExercise: () -> Unit,
    addSerie: () -> Unit,
    deleteSerie: (String) -> Unit,
    updateSerieReps: (String, Int?) -> Unit,
    updateSerieKgs: (String, String?) -> Unit,
    updateRest: (String?) -> Unit,
    updateNotes: (String?) -> Unit,
    updateSuperSerie: (Boolean) -> Unit,
    addExercise: () -> Unit,
) {
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(0.9f)
                .background(White, RoundedCornerShape(8.dp)),
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
                    .background(BlueLight, RoundedCornerShape(topEnd = 8.dp, topStart = 8.dp))
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                MediumText(
                    text = R.string.my_routines_exercises_add,
                    allCaps = true,
                    color = White,
                    fontFamily = montserrat_bold
                )
            }

            CompositionLocalProvider(
                LocalOverscrollFactory provides null
            ) {
                LazyColumn(
                    state = rememberLazyListState(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            ImageGif(
                                url = exercise.gifURL,
                                modifier = Modifier.size(72.dp)
                            )
                            SpacerSmall(horizontal = true)
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                MediumText(
                                    text = exercise.name,
                                    fontFamily = montserrat_bold
                                )
                                SpacerMini()
                                OutlinedButtonCustom(
                                    onClick = { seeExercise() },
                                    text = R.string.my_routines_exercises_see_exercise,
                                    textModifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                        SpacerSmall()
                    }

                    item { SeriesHeader() }

                    itemsIndexed(
                        items = uiStates.series,
                        key = { i, _ -> i }
                    ) { i, serie ->
                        SeriesItem(
                            i = i + 1,
                            serie = serie,
                            updateReps = { reps -> updateSerieReps(serie.id, reps) },
                            updateKgs = { kgs -> updateSerieKgs(serie.id, kgs) },
                            deleteSerie = { deleteSerie(serie.id) },
                        )
                    }

                    item {
                        SpacerMedium()
                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .clickable(onClick = { addSerie() }),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(painterResource(R.drawable.ic_add), null)
                            SpacerMini(horizontal = true)
                            MediumText(
                                text = R.string.my_routines_exercises_add_serie_button
                            )
                        }
                    }

                    item {
                        SpacerMedium()
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            MediumText(text = R.string.my_routines_exercises_rest)

                            SpacerSmall(horizontal = true)

                            ItemTextField(
                                value = uiStates.rest?.replace(".", ":"),
                                onValueChange = { updateRest(it) },
                                keyboardType = KeyboardType.Decimal,
                                length = 5,
                                modifier = Modifier.width(100.dp)
                            )

                            SpacerMini(horizontal = true)

                            MediumText(text = R.string.my_routines_exercises_rest_min_serie)
                        }
                    }

                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            MediumText(
                                text = R.string.my_routines_exercises_super_serie,
                                modifier = Modifier.weight(1f)
                            )
                            SpacerSmall(horizontal = true)
                            Switch(
                                uiStates.superSerie, { updateSuperSerie(it) },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = BlueMedium,
                                    uncheckedThumbColor = GrayHard,
                                    checkedTrackColor = GrayMedium,
                                    uncheckedTrackColor = GrayMedium,
                                    checkedBorderColor = GrayMedium,
                                    uncheckedBorderColor = GrayMedium
                                ),
                            )
                        }
                    }

                    item {
                        SpacerSmall()
                        MediumText(text = R.string.my_routines_exercises_add_notes)
                        CustomTextField(
                            value = uiStates.notes ?: "",
                            onValueChange = { updateNotes(it) },
                            containerColor = GrayLight,
                            singleLine = false,
                            maxLines = 6,
                            imeAction = ImeAction.None
                        )
                    }

                    item {
                        SpacerMedium()

                        if (uiStates.sww) {
                            MediumText(
                                text = R.string.my_routines_exercises_add_error,
                                color = RedHard
                            )
                            SpacerSmall()
                        }

                        val text = stringResource(R.string.my_routines_exercises_add_button, dayName)
                        ButtonCustom(
                            onClick = { addExercise() },
                            text = text,
                            color = BlueLight,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SeriesHeader() {
    Row(modifier = Modifier.fillMaxWidth()) {
        MediumText(
            text = R.string.my_routines_exercises_series,
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
        Icon(
            painterResource(R.drawable.ic_cross), null,
            modifier = Modifier.alpha(0f)
        )
    }
}

@Composable
fun SeriesItem(
    i: Int,
    serie: Serie,
    updateReps: (Int?) -> Unit,
    updateKgs: (String?) -> Unit,
    deleteSerie: () -> Unit,
) {
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

        ItemTextField(
            value = serie.reps?.toString(),
            onValueChange = { updateReps(it.toIntOrNull()) },
            keyboardType = KeyboardType.Number,
            length = 4,
            modifier = Modifier.weight(1f)
        )

        SpacerSmall(horizontal = true)

        ItemTextField(
            value = serie.kgs,
            onValueChange = { updateKgs(it) },
            keyboardType = KeyboardType.Decimal,
            length = 6,
            modifier = Modifier.weight(1f)
        )

        SpacerSmall(horizontal = true)

        Icon(
            painterResource(R.drawable.ic_cross), null,
            modifier = Modifier.clip(RoundedCornerShape(8.dp))
                .clickable(onClick = { deleteSerie() })
        )
    }
}

@Composable
fun ItemTextField(
    value: String?,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType,
    length: Int,
    modifier: Modifier = Modifier
) {
    BasicTextField(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(GrayLight)
            .padding(8.dp),
        value = value ?: "",
        onValueChange = { onValueChange(it.take(length)) },
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            color = Black,
            textAlign = TextAlign.Center
        ),
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.Next,
        ),
        decorationBox = { innerTextField ->
            Box {
                if (value == null) {
                    MediumText(
                        text = "0",
                        color = Black,
                        modifier = Modifier.fillMaxWidth().alpha(0.3f),
                        gravity = TextAlign.Center
                    )
                }
                innerTextField()
            }
        }
    )
}