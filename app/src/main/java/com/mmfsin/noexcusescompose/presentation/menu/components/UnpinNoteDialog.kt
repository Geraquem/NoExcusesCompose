package com.mmfsin.noexcusescompose.presentation.menu.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.mmfsin.noexcusescompose.R
import com.mmfsin.noexcusescompose.presentation.core.components.MediumText
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerMedium
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerSmall
import com.mmfsin.noexcusescompose.presentation.core.theme.RedMedium
import com.mmfsin.noexcusescompose.presentation.core.theme.White
import com.mmfsin.noexcusescompose.presentation.core.theme.montserrat_bold

@Preview
@Composable
fun UnpinNoteDialogPV() {
    UnpinNoteDialog("Notita 1", {}, {})
}

@Composable
fun UnpinNoteDialog(
    noteTitle: String,
    cancel: () -> Unit,
    accept: () -> Unit,
) {
    Dialog(
        onDismissRequest = { cancel() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(0.9f)
                .background(White, RoundedCornerShape(8.dp)),
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
                    .background(RedMedium, RoundedCornerShape(topEnd = 8.dp, topStart = 8.dp))
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painterResource(R.drawable.ic_pushpin_off), null,
                    tint = White
                )
            }

            SpacerMedium()

            Column(Modifier.fillMaxWidth().padding(horizontal = 12.dp)) {
                MediumText(text = stringResource(R.string.notes_unpin_question, noteTitle))
                SpacerSmall()
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Spacer(Modifier.weight(1f))
                    TextButton(onClick = { cancel() }) {
                        MediumText(R.string.notes_unpin_cancel)
                    }
                    SpacerSmall(horizontal = true)
                    TextButton(onClick = { accept() }) {
                        MediumText(
                            R.string.notes_unpin_accept,
                            fontFamily = montserrat_bold,
                            color = RedMedium
                        )
                    }
                }
            }
        }
    }
}