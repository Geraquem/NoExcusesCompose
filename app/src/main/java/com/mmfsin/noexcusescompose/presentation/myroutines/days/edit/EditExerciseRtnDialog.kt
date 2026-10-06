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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mmfsin.noexcusescompose.R
import com.mmfsin.noexcusescompose.domain.models.ExerciseRtn
import com.mmfsin.noexcusescompose.domain.models.createSerie
import com.mmfsin.noexcusescompose.domain.models.getExerciseRtnExamples
import com.mmfsin.noexcusescompose.presentation.core.components.ButtonCustom
import com.mmfsin.noexcusescompose.presentation.core.components.CustomTextField
import com.mmfsin.noexcusescompose.presentation.core.components.ImageGif
import com.mmfsin.noexcusescompose.presentation.core.components.MediumText
import com.mmfsin.noexcusescompose.presentation.core.components.OutlinedButtonCustom
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerMedium
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerMini
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerSmall
import com.mmfsin.noexcusescompose.presentation.core.theme.BlueLight
import com.mmfsin.noexcusescompose.presentation.core.theme.BlueMedium
import com.mmfsin.noexcusescompose.presentation.core.theme.GrayHard
import com.mmfsin.noexcusescompose.presentation.core.theme.GrayLight
import com.mmfsin.noexcusescompose.presentation.core.theme.GrayMedium
import com.mmfsin.noexcusescompose.presentation.core.theme.RedHard
import com.mmfsin.noexcusescompose.presentation.core.theme.White
import com.mmfsin.noexcusescompose.presentation.core.theme.YellowHard
import com.mmfsin.noexcusescompose.presentation.core.theme.montserrat_bold
import com.mmfsin.noexcusescompose.presentation.myroutines.exercises.add.ItemTextField
import com.mmfsin.noexcusescompose.presentation.myroutines.exercises.add.SeriesHeader
import com.mmfsin.noexcusescompose.presentation.myroutines.exercises.add.SeriesItem

@Preview
@Composable
fun EditExerciseRtnDialogPV() {
    EditExerciseRtnContent(
        uiStates = EditExerciseRtnStates(
            series = listOf(createSerie(0), createSerie(1)),
            sww = false
        ),
        exerciseRtn = getExerciseRtnExamples().first(),
        {}, {}, {}, {},
        { _, _ -> }, { _, _ -> }, {},
        {}, {}, {}, {},
    )
}

@Composable
fun EditExerciseRtnDialog(
    viewModel: EditExerciseRtnViewModel = hiltViewModel(),
    exerciseRtn: ExerciseRtn,
    onDismiss: () -> Unit,
    seeExerciseDetail: () -> Unit,
    deleteExercise: () -> Unit,
) {

    fun close() {
        viewModel.resetData()
        onDismiss()
    }

    val uiStates by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(exerciseRtn.id) { viewModel.updateExerciseRtn(exerciseRtn) }

    EditExerciseRtnContent(
        uiStates = uiStates,
        exerciseRtn = exerciseRtn,
        onDismiss = { close() },
        seeExercise = { seeExerciseDetail() },
        addSerie = { viewModel.addSerie() },
        deleteSerie = { viewModel.deleteSerie(it) },
        updateSerieReps = { id, reps -> viewModel.updateSerieReps(id, reps) },
        updateSerieKgs = { id, kgs -> viewModel.updateSerieKgs(id, kgs) },
        updateRest = { viewModel.updateRest(it) },
        updateNotes = { viewModel.updateNotes(it) },
        updateSuperSerie = { viewModel.updateSuperSerie(it) },
        deleteExercise = { deleteExercise() },
        editExercise = { viewModel.editExercise() },
    )

    if (uiStates.goBack) close()
}

@Composable
fun EditExerciseRtnContent(
    uiStates: EditExerciseRtnStates,
    exerciseRtn: ExerciseRtn,
    onDismiss: () -> Unit,
    seeExercise: () -> Unit,
    addSerie: () -> Unit,
    deleteSerie: (String) -> Unit,
    updateSerieReps: (String, Int?) -> Unit,
    updateSerieKgs: (String, String?) -> Unit,
    updateRest: (String?) -> Unit,
    updateNotes: (String?) -> Unit,
    updateSuperSerie: (Boolean) -> Unit,
    deleteExercise: () -> Unit,
    editExercise: () -> Unit,
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
                    text = R.string.my_routines_exercises_edit_exercise,
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
                                url = exerciseRtn.exercise?.gifURL,
                                modifier = Modifier.size(72.dp)
                            )
                            SpacerSmall(horizontal = true)
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                MediumText(
                                    text = exerciseRtn.exercise?.name ?: "",
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
                                thumbContent = {
                                    val icon = if (uiStates.superSerie) R.drawable.ic_check else R.drawable.ic_add
                                    Icon(
                                        painterResource(icon), null,
                                        tint = if (uiStates.superSerie) YellowHard else GrayHard
                                    )
                                },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = White,
                                    checkedTrackColor = BlueMedium,
                                    uncheckedThumbColor = GrayHard,
                                    uncheckedTrackColor = GrayMedium,
                                    uncheckedBorderColor = GrayHard
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

                        Row(Modifier.fillMaxWidth()) {
                            IconButton(onClick = { deleteExercise() }) {
                                Icon(
                                    painterResource(R.drawable.ic_trash), null,
                                    tint = RedHard
                                )
                            }

                            SpacerMedium(horizontal = true)

                            ButtonCustom(
                                onClick = { editExercise() },
                                modifier = Modifier.weight(1f),
                                text = R.string.my_routines_exercises_edit,
                                color = BlueLight,
                                textColor = White
                            )
                        }
                    }
                }
            }
        }
    }
}