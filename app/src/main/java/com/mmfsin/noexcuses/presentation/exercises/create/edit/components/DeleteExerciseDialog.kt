package com.mmfsin.noexcuses.presentation.exercises.create.edit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.presentation.core.components.MediumText
import com.mmfsin.noexcuses.presentation.core.components.SpacerLarge
import com.mmfsin.noexcuses.presentation.core.components.SpacerSmall
import com.mmfsin.noexcuses.presentation.core.theme.GrayHard
import com.mmfsin.noexcuses.presentation.core.theme.RedMedium
import com.mmfsin.noexcuses.presentation.core.theme.White
import com.mmfsin.noexcuses.presentation.core.theme.montserrat_bold

@Preview
@Composable
fun DeleteExerciseDialogPV() {
    DeleteExerciseDialog("Press banca", {}, {})
}

@Composable
fun DeleteExerciseDialog(
    exerciseName: String,
    cancel: () -> Unit,
    delete: () -> Unit
) {
    Dialog(
        onDismissRequest = { cancel() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(0.9f)
                .clip(RoundedCornerShape(8.dp))
                .background(White)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
                    .background(RedMedium, RoundedCornerShape(topEnd = 8.dp, topStart = 8.dp))
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                MediumText(
                    text = R.string.my_routines_delete,
                    allCaps = true,
                    color = White,
                    fontFamily = montserrat_bold
                )
            }
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
                    .padding(top = 16.dp, bottom = 12.dp),
            ) {
                val text = stringResource(R.string.my_routines_delete_text, exerciseName)
                MediumText(text = text)

                SpacerLarge()

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Spacer(Modifier.weight(1f))

                    TextButton(onClick = { cancel() }) {
                        MediumText(
                            R.string.my_routines_create_cancel,
                            color = GrayHard
                        )
                    }

                    SpacerSmall(horizontal = true)

                    TextButton(onClick = { delete() }) {
                        MediumText(
                            text = R.string.my_routines_delete,
                            fontFamily = montserrat_bold,
                            color = RedMedium
                        )
                    }
                }
            }
        }
    }
}