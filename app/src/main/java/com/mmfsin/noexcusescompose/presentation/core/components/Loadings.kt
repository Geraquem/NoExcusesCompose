package com.mmfsin.noexcusescompose.presentation.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.rememberLottieDynamicProperties
import com.airbnb.lottie.compose.rememberLottieDynamicProperty
import com.mmfsin.noexcusescompose.R
import com.mmfsin.noexcusescompose.presentation.core.theme.BackgroundBlack
import com.mmfsin.noexcusescompose.presentation.core.theme.BlueMedium
import com.mmfsin.noexcusescompose.presentation.core.theme.White

@Preview
@Composable
fun LoadingFullScreen() {
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            Modifier.fillMaxSize()
                .alpha(0.75f)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = {}
                )
                .background(BackgroundBlack)
        )
        LoadingLottie()
    }
}

@Preview
@Composable
fun LoadingMini() {
    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            Modifier.size(40.dp),
            strokeWidth = 6.dp,
            color = Color.Blue,
            strokeCap = StrokeCap.Round
        )
    }
}

@Preview
@Composable
fun LoadingDialogPV() {
    LoadingDialog(R.string.app_name)
}

@Composable
fun LoadingDialog(text: Int? = null) {
    Dialog(onDismissRequest = {}) {
        Box(
            modifier = Modifier.size(200.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(White),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator(
                    Modifier.size(64.dp),
                    strokeWidth = 6.dp,
                    color = BlueMedium,
                    strokeCap = StrokeCap.Round
                )

                text?.let {
                    SpacerLarge()
                    SmallText(text = it)
                }
            }
        }
    }
}

@Preview
@Composable
fun LoadingLottie(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val composition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(R.raw.lottie_waiting)
        )

        val progress by animateLottieCompositionAsState(
            composition,
            iterations = LottieConstants.IterateForever
        )

        val dynamicProperties = rememberLottieDynamicProperties(
            rememberLottieDynamicProperty(
                property = LottieProperty.COLOR,
                value = Color.White.toArgb(),
                keyPath = arrayOf("**")
            )
        )

        LottieAnimation(
            composition = composition,
            progress = { progress },
            dynamicProperties = dynamicProperties,
            modifier = Modifier.size(140.dp)
        )
    }
}