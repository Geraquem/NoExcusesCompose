package com.mmfsin.noexcusescompose.presentation.core.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import com.mmfsin.noexcusescompose.R
import com.mmfsin.noexcusescompose.presentation.core.theme.Black
import com.mmfsin.noexcusescompose.presentation.core.theme.montserrat_regular

@Preview(showBackground = true)
@Composable
fun TextsPV() {
    Column {
        SmallText(text = R.string.app_name)
        MediumText(text = R.string.app_name)
        BigText(text = R.string.app_name)
    }
}

@Composable
fun SmallText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Black,
    fontWeight: FontWeight = FontWeight.Normal,
    gravity: TextAlign = TextAlign.Start,
    fontSize: TextUnit = MaterialTheme.typography.bodySmall.fontSize,
    fontFamily: FontFamily = montserrat_regular,
    textDecoration: TextDecoration = TextDecoration.None,
    allCaps: Boolean = false
) {
    Text(
        modifier = modifier,
        text = if (allCaps) text.uppercase() else text,
        style = MaterialTheme.typography.bodySmall,
        color = color,
        fontWeight = fontWeight,
        textDecoration = textDecoration,
        textAlign = gravity,
        fontSize = fontSize,
        fontFamily = fontFamily
    )
}

@Composable
fun SmallText(
    text: Int,
    modifier: Modifier = Modifier,
    color: Color = Black,
    fontWeight: FontWeight = FontWeight.Normal,
    gravity: TextAlign = TextAlign.Start,
    fontSize: TextUnit = MaterialTheme.typography.bodySmall.fontSize,
    fontFamily: FontFamily = montserrat_regular,
    textDecoration: TextDecoration = TextDecoration.None,
    allCaps: Boolean = false
) {
    val text = stringResource(text)
    Text(
        modifier = modifier,
        text = if (allCaps) text.uppercase() else text,
        style = MaterialTheme.typography.bodySmall,
        color = color,
        fontWeight = fontWeight,
        textDecoration = textDecoration,
        textAlign = gravity,
        fontSize = fontSize,
        fontFamily = fontFamily
    )
}

@Composable
fun MediumText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Black,
    fontWeight: FontWeight = FontWeight.Normal,
    gravity: TextAlign = TextAlign.Start,
    fontSize: TextUnit = MaterialTheme.typography.bodyLarge.fontSize,
    fontFamily: FontFamily = montserrat_regular,
    textDecoration: TextDecoration = TextDecoration.None,
    allCaps: Boolean = false
) {
    Text(
        modifier = modifier,
        text = if (allCaps) text.uppercase() else text,
        style = MaterialTheme.typography.bodyLarge,
        color = color,
        fontWeight = fontWeight,
        textDecoration = textDecoration,
        textAlign = gravity,
        fontSize = fontSize,
        fontFamily = fontFamily
    )
}

@Composable
fun MediumText(
    text: Int,
    modifier: Modifier = Modifier,
    color: Color = Black,
    fontWeight: FontWeight = FontWeight.Normal,
    gravity: TextAlign = TextAlign.Start,
    fontSize: TextUnit = MaterialTheme.typography.bodyLarge.fontSize,
    fontFamily: FontFamily = montserrat_regular,
    textDecoration: TextDecoration = TextDecoration.None,
    allCaps: Boolean = false
) {
    val text = stringResource(text)
    Text(
        modifier = modifier,
        text = if (allCaps) text.uppercase() else text,
        style = MaterialTheme.typography.bodyLarge,
        color = color,
        fontWeight = fontWeight,
        textDecoration = textDecoration,
        textAlign = gravity,
        fontSize = fontSize,
        fontFamily = fontFamily
    )
}

@Composable
fun BigText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Black,
    fontWeight: FontWeight = FontWeight.Normal,
    gravity: TextAlign = TextAlign.Start,
    fontSize: TextUnit = MaterialTheme.typography.titleLarge.fontSize,
    fontFamily: FontFamily = montserrat_regular,
    textDecoration: TextDecoration = TextDecoration.None,
    allCaps: Boolean = false
) {
    Text(
        modifier = modifier,
        text = if (allCaps) text.uppercase() else text,
        style = MaterialTheme.typography.titleLarge,
        color = color,
        fontWeight = fontWeight,
        textDecoration = textDecoration,
        textAlign = gravity,
        fontSize = fontSize,
        fontFamily = fontFamily
    )
}

@Composable
fun BigText(
    text: Int,
    modifier: Modifier = Modifier,
    color: Color = Black,
    fontWeight: FontWeight = FontWeight.Normal,
    gravity: TextAlign = TextAlign.Start,
    fontSize: TextUnit = MaterialTheme.typography.titleLarge.fontSize,
    fontFamily: FontFamily = montserrat_regular,
    textDecoration: TextDecoration = TextDecoration.None,
    allCaps: Boolean = false
) {
    val text = stringResource(text)
    Text(
        modifier = modifier,
        text = if (allCaps) text.uppercase() else text,
        style = MaterialTheme.typography.titleLarge,
        color = color,
        fontWeight = fontWeight,
        textDecoration = textDecoration,
        textAlign = gravity,
        fontSize = fontSize,
        fontFamily = fontFamily
    )
}