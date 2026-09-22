package com.mmfsin.noexcusescompose.presentation.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mmfsin.noexcusescompose.R
import com.mmfsin.noexcusescompose.presentation.core.theme.Black
import com.mmfsin.noexcusescompose.presentation.core.theme.GrayMedium
import com.mmfsin.noexcusescompose.presentation.core.theme.White

@Preview()
@Composable
fun MyOutlinedTextFieldPV() {
    Column() {
        CustomTextField("Texto de prueba", {}, R.string.app_name, lengthVisibility = true)
        SpacerMedium()
        CustomOutlinedTextField("Texto de prueba", {}, R.string.app_name, lengthVisibility = true)
    }
}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: Int? = null,
    singleLine: Boolean = true,
    minLines: Int = 1,
    maxLines: Int = 1,
    maxLength: Int = 50,
    lengthVisibility: Boolean = false,
    imeAction: ImeAction = ImeAction.Next,
    hint: String = "",
    containerColor: Color = White,
    textColor: Color = Black
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        label?.let {
            SmallText(label, color = textColor)
            SpacerMini()
        }
        BasicTextField(
            modifier = Modifier.fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(containerColor)
                .padding(horizontal = 8.dp, vertical = 12.dp),
            value = value, onValueChange = { onValueChange(it.take(maxLength)) },
            singleLine = singleLine,
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = textColor),
            minLines = minLines,
            maxLines = maxLines,
            keyboardOptions = KeyboardOptions(
                imeAction = imeAction,
                capitalization = KeyboardCapitalization.Sentences
            ),
            decorationBox = { innerTextField ->
                Box {
                    if (value.isEmpty()) {
                        MediumText(text = hint, color = textColor)
                    }
                    innerTextField()
                }
            }
        )
        if (lengthVisibility) {
            SpacerMini()
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "${value.length}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    "/",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    "$maxLength",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: Int? = null,
    singleLine: Boolean = true,
    minLines: Int = 1,
    maxLines: Int = 1,
    maxLength: Int = 50,
    lengthVisibility: Boolean = false,
    imeAction: ImeAction = ImeAction.Next,
    hint: String = "",
    borderColor: Color = GrayMedium,
    textColor: Color = Black
) {
    Box(
        modifier = Modifier
            .border(1.dp, borderColor, RoundedCornerShape(8.dp))
            .padding(12.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            label?.let {
                SmallText(label, color = textColor)
                SpacerMini()
            }
            BasicTextField(
                modifier = Modifier.fillMaxWidth(),
                value = value, onValueChange = { onValueChange(it.take(maxLength)) },
                singleLine = singleLine,
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = textColor),
                minLines = minLines,
                maxLines = maxLines,
                keyboardOptions = KeyboardOptions(
                    imeAction = imeAction,
                    capitalization = KeyboardCapitalization.Sentences
                ),
                decorationBox = { innerTextField ->
                    Box {
                        if (value.isEmpty()) {
                            MediumText(text = hint, color = textColor)
                        }
                        innerTextField()
                    }
                }
            )
            if (lengthVisibility) {
                SpacerMini()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "${value.length}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        "/",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        "$maxLength",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}