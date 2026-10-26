package com.mmfsin.noexcusescompose.presentation.rms.exercises.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.mmfsin.noexcusescompose.R
import com.mmfsin.noexcusescompose.domain.models.Exercise
import com.mmfsin.noexcusescompose.domain.models.getExercisesExamples
import com.mmfsin.noexcusescompose.presentation.core.components.ButtonCustom
import com.mmfsin.noexcusescompose.presentation.core.components.ImageGif
import com.mmfsin.noexcusescompose.presentation.core.components.MediumText
import com.mmfsin.noexcusescompose.presentation.core.components.OutlinedButtonCustom
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerLarge
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerMedium
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerMini
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerSmall
import com.mmfsin.noexcusescompose.presentation.core.theme.BlueLight
import com.mmfsin.noexcusescompose.presentation.core.theme.GrayLight
import com.mmfsin.noexcusescompose.presentation.core.theme.White
import com.mmfsin.noexcusescompose.presentation.core.theme.montserrat_bold
import com.mmfsin.noexcusescompose.presentation.myroutines.exercises.add.ItemTextField
import com.mmfsin.noexcusescompose.util.formatDate
import java.time.LocalDate
import java.time.ZoneId

@Preview
@Composable
fun AddRmDialogPV() {
    AddRmDialogDialog(
        {},
        exercise = getExercisesExamples().first(),
        {}, { _, _ -> }
    )
}

@Composable
fun AddRmDialogDialog(
    onDismiss: () -> Unit,
    exercise: Exercise,
    seeExercise: () -> Unit,
    addRm: (Long, Double) -> Unit,
) {

    val today = remember { LocalDate.now() }

    var date by remember { mutableLongStateOf(System.currentTimeMillis()) }
    var dateText by remember { mutableStateOf(formatDate(date).orEmpty()) }

    var kgsStr by remember { mutableStateOf("") }

    var showPicker by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = today
            .atStartOfDay(ZoneId.of("UTC"))
            .toInstant()
            .toEpochMilli()
    )

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
                    text = R.string.rms_add_title,
                    allCaps = true,
                    color = White,
                    fontFamily = montserrat_bold
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth().padding(12.dp).padding(bottom = 6.dp),
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

                SpacerLarge()

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(painterResource(R.drawable.ic_calendar), null)
                    SpacerSmall(horizontal = true)
                    MediumText(text = R.string.rms_date)
                }

                SpacerSmall()

                Card(
                    onClick = { showPicker = true },
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = GrayLight
                    ),
                    elevation = CardDefaults.elevatedCardElevation(
                        defaultElevation = 4.dp
                    )
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().background(GrayLight, RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        MediumText(
                            text = dateText,
                            modifier = Modifier.weight(1f)
                        )

                        SpacerMini(horizontal = true)

                        Icon(painterResource(R.drawable.ic_arrow_down), null)
                    }
                }

                SpacerMedium()

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(painterResource(R.drawable.ic_dumbbell), null)
                    SpacerSmall(horizontal = true)
                    MediumText(text = R.string.rms_select_weight)
                }

                SpacerMini()

                Row(verticalAlignment = Alignment.CenterVertically) {
                    ItemTextField(
                        value = kgsStr,
                        onValueChange = { kgsStr = it },
                        keyboardType = KeyboardType.Decimal,
                        length = 6,
                        modifier = Modifier.width(100.dp)
                    )

                    SpacerMini(horizontal = true)

                    MediumText(text = R.string.rms_kgs)
                }

                SpacerLarge()

                val kgs = kgsStr.toDoubleOrNull()
                ButtonCustom(
                    onClick = { kgs?.let { addRm(date, it) } },
                    text = R.string.rms_add_title,
                    color = BlueLight,
                    enabled = kgs != null && kgs > 0.0,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

    if (showPicker) {
        DatePickerDialog(
            onDismissRequest = { showPicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            date = millis
                            dateText = formatDate(millis).orEmpty()
                        }
                        showPicker = false
                    }
                ) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showPicker = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                showModeToggle = false
            )
        }
    }
}