package com.mmfsin.noexcusescompose.presentation.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.mmfsin.noexcusescompose.R
import com.mmfsin.noexcusescompose.presentation.core.theme.RedMedium
import com.mmfsin.noexcusescompose.presentation.core.theme.White
import com.mmfsin.noexcusescompose.presentation.core.theme.montserrat_bold

@Preview
@Composable
fun ErrorDialogPV() {
    ErrorDialog({})
}

@Composable
fun ErrorDialog(accept: () -> Unit) {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(0.9f)
                .clip(RoundedCornerShape(8.dp))
                .background(White)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth().background(RedMedium).padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painterResource(R.drawable.ic_error), null,
                    tint = White
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
            ) {
                MediumText(text = R.string.error_title)

                SpacerSmall()

                MediumText(text = R.string.error_description)

                SpacerMedium()

                TextButton(
                    onClick = { accept() },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    MediumText(
                        text = R.string.error_btn,
                        fontFamily = montserrat_bold,
                        color = RedMedium
                    )
                }
            }
        }
    }
}