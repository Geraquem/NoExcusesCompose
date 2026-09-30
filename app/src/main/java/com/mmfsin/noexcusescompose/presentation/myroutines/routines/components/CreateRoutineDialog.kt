package com.mmfsin.noexcusescompose.presentation.myroutines.routines.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.mmfsin.noexcusescompose.R
import com.mmfsin.noexcusescompose.presentation.core.components.CustomTextField
import com.mmfsin.noexcusescompose.presentation.core.components.MediumText
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerLarge
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerSmall
import com.mmfsin.noexcusescompose.presentation.core.theme.BlueLight
import com.mmfsin.noexcusescompose.presentation.core.theme.BlueMedium
import com.mmfsin.noexcusescompose.presentation.core.theme.GrayLight
import com.mmfsin.noexcusescompose.presentation.core.theme.GrayMedium
import com.mmfsin.noexcusescompose.presentation.core.theme.White
import com.mmfsin.noexcusescompose.presentation.core.theme.montserrat_bold

@Preview
@Composable
fun CreateRoutineDialogPV() {
    CreateRoutineDialog(
        {},
        "",
        {},
        "Descriciónnnn",
        {},
        {},
    )
}

@Composable
fun CreateRoutineDialog(
    onDismiss: () -> Unit,
    name: String,
    updateName: (String) -> Unit,
    description: String,
    updateDescription: (String) -> Unit,
    createRoutine: () -> Unit,
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
                    text = R.string.my_routines_routine_weekly,
                    allCaps = true,
                    color = White,
                    fontFamily = montserrat_bold
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth().padding(12.dp)
            ) {
                CustomTextField(
                    value = name,
                    onValueChange = { updateName(it) },
                    label = R.string.my_routines_create_name,
                    modifier = Modifier.fillMaxWidth(),
                    maxLength = 50,
                    hint = R.string.my_routines_create_name_hint,
                    lengthVisibility = true,
                    containerColor = GrayLight
                )

                SpacerSmall()

                CustomTextField(
                    value = description,
                    onValueChange = { updateDescription(it) },
                    label = R.string.my_routines_create_description,
                    modifier = Modifier.fillMaxWidth().height(100.dp),
                    maxLength = 450,
                    maxLines = 10,
                    hint = R.string.my_routines_create_description_hint,
                    lengthVisibility = true,
                    containerColor = GrayLight
                )

                SpacerLarge()

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Spacer(Modifier.weight(1f))

                    TextButton(onClick = { onDismiss() }) {
                        MediumText(R.string.my_routines_create_cancel)
                    }

                    SpacerSmall(horizontal = true)

                    TextButton(
                        onClick = { createRoutine() },
                        enabled = name.isNotBlank(),
                    ) {
                        MediumText(
                            text = R.string.my_routines_create_save,
                            fontFamily = montserrat_bold,
                            color = if (name.isNotBlank()) BlueMedium else GrayMedium
                        )
                    }
                }
            }
        }
    }
}
