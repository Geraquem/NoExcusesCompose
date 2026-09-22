package com.mmfsin.noexcusescompose.util

import android.content.Context
import android.content.Intent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.mmfsin.noexcusescompose.presentation.core.bedrock.BedRockActivity

fun Context.openBedRockActivity(navGraph: String, strArgs: String? = null, boolArgs: Boolean? = null) {
    val intent = Intent(this, BedRockActivity::class.java)
    intent.putExtra(BEDROCK_NAV_GRAPH, navGraph)
    strArgs?.let { intent.putExtra(BEDROCK_STR_ARGS, strArgs) }
    boolArgs?.let { intent.putExtra(BEDROCK_BOOL_ARGS, boolArgs) }
    startActivity(intent)
}

@Composable
fun AnimateX(
    targetX: Float,
    duration: Int = 1000,
    onEnd: () -> Unit = {},
    content: @Composable () -> Unit
) {
    val animatedX by animateDpAsState(
        targetValue = targetX.dp,
        animationSpec = tween(duration),
        finishedListener = { onEnd() }
    )

    Box(modifier = Modifier.offset(x = animatedX)) { content() }
}

@Composable
fun AnimateY(
    targetY: Float,
    duration: Int = 1000,
    onEnd: () -> Unit = {},
    content: @Composable () -> Unit
) {
    val animatedY by animateDpAsState(
        targetValue = targetY.dp,
        animationSpec = tween(duration),
        finishedListener = { onEnd() }
    )

    Box(modifier = Modifier.offset(y = animatedY)) { content() }
}

@Composable
fun ShowAlpha(
    visibleTrigger: Boolean,
    duration: Int = 1000,
    onEnd: () -> Unit = {},
    content: @Composable () -> Unit
) {
    val alpha by animateFloatAsState(
        targetValue = if (visibleTrigger) 1f else 0f,
        animationSpec = tween(duration),
        finishedListener = { if (visibleTrigger) onEnd() }
    )

    Box(modifier = Modifier.alpha(alpha)) { content() }
}