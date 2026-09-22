package com.mmfsin.noexcusescompose.presentation.core.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mmfsin.noexcusescompose.R
import com.mmfsin.noexcusescompose.presentation.core.theme.Black
import com.mmfsin.noexcusescompose.presentation.core.theme.BlueMedium
import com.mmfsin.noexcusescompose.presentation.core.theme.GrayMedium
import com.mmfsin.noexcusescompose.presentation.core.theme.OrangeMedium
import com.mmfsin.noexcusescompose.presentation.core.theme.White
import com.mmfsin.noexcusescompose.presentation.core.theme.montserrat_regular

val alphazetFont = FontFamily(
    Font(R.font.alphazet, weight = FontWeight.Normal),
)

@Preview
@Composable
fun ButtonCustomPV() {
    Column() {
        ButtonCustom(
            onClick = {},
            text = R.string.app_name
        )
        SpacerSmall()
        ButtonCustomIcon(
            onClick = {},
            text = R.string.app_name,
            icon = R.drawable.ic_error,
        )
        SpacerSmall()
        OutlinedButtonCustom(
            onClick = {},
            text = R.string.app_name
        )
    }
}

@Composable
fun ButtonCustom(
    onClick: () -> Unit,
    text: Int,
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    enabled: Boolean = true,
    color: Color = White,
    textColor: Color = Black
) {
    Button(
        onClick = { onClick() },
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = color
        ),
        shape = RoundedCornerShape(25),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 2.dp,
            disabledElevation = 0.dp
        )
    ) {
        MediumText(
            text = text,
            color = textColor,
            modifier = textModifier.padding(vertical = 4.dp),
            fontFamily = montserrat_regular,
            allCaps = true,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun ButtonCustomIcon(
    onClick: () -> Unit,
    text: Int,
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    icon: Int,
    enabled: Boolean = true,
    color: Color = BlueMedium,
    textColor: Color = White
) {
    Row(
        modifier = modifier
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(25)
            )
            .clip(RoundedCornerShape(25))
            .background(if (enabled) color else GrayMedium)
            .clickable(onClick = { if (enabled) onClick() })
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(icon), null,
            tint = White
        )
        SpacerSmall(horizontal = true)
        MediumText(
            text = text,
            color = textColor,
            modifier = textModifier.padding(vertical = 4.dp),
            fontFamily = montserrat_regular,
            allCaps = true
        )
    }
}

@Composable
fun OutlinedButtonCustom(
    onClick: () -> Unit,
    text: Int,
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    enabled: Boolean = true,
    textColor: Color = BlueMedium
) {
    OutlinedButton(
        onClick = { onClick() },
        modifier = modifier,
        enabled = enabled,
        border = BorderStroke(1.dp, textColor),
        shape = RoundedCornerShape(25)
    ) {
        MediumText(
            text = text,
            color = textColor,
            modifier = textModifier.padding(vertical = 4.dp),
            fontFamily = montserrat_regular,
            allCaps = true
        )
    }
}