package com.mmfsin.noexcusescompose.presentation.myroutines.exercises.components

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
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
import com.mmfsin.noexcusescompose.presentation.core.components.ImageGif
import com.mmfsin.noexcusescompose.presentation.core.components.MediumText
import com.mmfsin.noexcusescompose.presentation.core.components.OutlinedButtonCustom
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerMedium
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerMini
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerSmall
import com.mmfsin.noexcusescompose.presentation.core.theme.Black
import com.mmfsin.noexcusescompose.presentation.core.theme.BlueLight
import com.mmfsin.noexcusescompose.presentation.core.theme.GrayMedium
import com.mmfsin.noexcusescompose.presentation.core.theme.White
import com.mmfsin.noexcusescompose.presentation.core.theme.montserrat_bold

@Preview
@Composable
fun AddExerciseDialogPV() {
    AddExerciseContent(
        uiStates = AddExerciseStates(
            series = listOf("1", "2")
        ),
        exercise = getExercisesExamples().first(),
        {}, {}, {}, {},
    )
}

@Composable
fun AddExerciseDialog(
    viewModel: AddExerciseViewModel = hiltViewModel(),
    exercise: Exercise,
    onDismiss: () -> Unit,
    seeExercise: () -> Unit,
    addExercise: () -> Unit,
) {
    val uiStates by viewModel.uiState.collectAsStateWithLifecycle()

    AddExerciseContent(
        uiStates = uiStates,
        exercise = exercise,
        onDismiss = { onDismiss() },
        seeExercise = { seeExercise() },
        addSerie = { viewModel.addSerie() },
        addExercise = { addExercise() },
    )
}

@Composable
fun AddExerciseContent(
    uiStates: AddExerciseStates,
    exercise: Exercise,
    onDismiss: () -> Unit,
    seeExercise: () -> Unit,
    addSerie: () -> Unit,
    addExercise: () -> Unit,
) {

    val series = remember { mutableStateListOf<Serie>() }

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
            Column(
                modifier = Modifier.fillMaxWidth().padding(12.dp)
            ) {
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

                CompositionLocalProvider(
                    LocalOverscrollFactory provides null
                ) {
                    LazyColumn(
                        state = rememberLazyListState(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(vertical = 16.dp)
                    ) {
                        item { SeriesHeader() }
                        itemsIndexed(
                            items = series,
                            key = { i, _ -> i }
                        ) { i, serie ->
                            SeriesItem(i + 1, serie)
                        }
                        item {
                            SpacerMedium()
                            Row(
                                modifier = Modifier.fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable(onClick = { series.add(createSerie()) }),
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
            text = "Series",
            fontFamily = montserrat_bold,
            modifier = Modifier.width(64.dp)
        )
        Row(modifier = Modifier.weight(1f)) {
            MediumText(
                text = "Reps",
                fontFamily = montserrat_bold,
                gravity = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
            MediumText(
                text = "Kg",
                fontFamily = montserrat_bold,
                gravity = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
        }
        Icon(painterResource(R.drawable.ic_cross), null)
    }
}

@Composable
fun SeriesItem(i: Int, serie: Serie) {
    var reps by remember { mutableStateOf("") }
    var kgs by remember { mutableStateOf("") }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        MediumText(
            text = "Serie $i",
            fontFamily = montserrat_bold,
            modifier = Modifier.width(64.dp)
        )
        SpacerSmall(horizontal = true)
        ItemTextField(
            value = reps,
            onValueChange = { reps = it },
            hint = serie.reps.toString(),
            modifier = Modifier.weight(1f)
        )
        SpacerSmall(horizontal = true)
        ItemTextField(
            value = kgs,
            onValueChange = { kgs = it },
            hint = serie.kgs.toString(),
            modifier = Modifier.weight(1f)
        )
        SpacerSmall(horizontal = true)
        Icon(painterResource(R.drawable.ic_cross), null)
    }
}

@Composable
fun ItemTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    modifier: Modifier
) {
    BasicTextField(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(GrayMedium)
            .padding(12.dp),
        value = value, onValueChange = { onValueChange(it.take(5)) },
        singleLine = true,
        textStyle = MaterialTheme.typography.bodyLarge.copy(color = Black),
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next,
            capitalization = KeyboardCapitalization.Sentences
        ),
        decorationBox = { innerTextField ->
            Box {
                if (value.isEmpty()) {
                    MediumText(
                        text = hint,
                        color = Black,
                        modifier = Modifier.alpha(0.3f)
                    )
                }
                innerTextField()
            }
        }
    )
}