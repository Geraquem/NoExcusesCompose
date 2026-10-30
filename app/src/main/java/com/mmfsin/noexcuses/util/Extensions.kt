package com.mmfsin.noexcuses.util

import android.content.Context
import android.content.Intent
import android.net.Uri
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
import com.mmfsin.noexcuses.presentation.core.bedrock.BedRockActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.UUID

fun <T1 : Any, T2 : Any, R : Any> checkNotNulls(p1: T1?, p2: T2?, block: (T1, T2) -> R): R? {
    return if (p1 != null && p2 != null) block(p1, p2) else null
}

fun Context.openBedRockActivity(
    navGraph: String,
    strArgs: String? = null,
    strArgs2: String? = null,
    boolArgs: Boolean? = null
) {
    val intent = Intent(this, BedRockActivity::class.java)
    intent.putExtra(BEDROCK_NAV_GRAPH, navGraph)
    strArgs?.let { intent.putExtra(BEDROCK_STR_ARGS, strArgs) }
    strArgs2?.let { intent.putExtra(BEDROCK_STR_ARGS_2, strArgs2) }
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


fun Double?.formatTime(): String? {
    return this?.let { d ->
        String.format("%.2f", d).replace(",", ":").replace(":00", "")
    } ?: run { null }
}

fun Double?.deletePointZero(): String? {
    val formatted = this.toString()
    return this?.let {
        if (formatted.endsWith(".0")) {
            formatted.replace(".0", "")
        } else formatted
    } ?: run { null }
}

fun formatDate(date: Long?): String? {
    if (date == null) return null
    return Instant.ofEpochMilli(date)
        .atZone(ZoneId.systemDefault())
        .format(
            DateTimeFormatter.ofPattern(
                "dd MMM yyyy",
                Locale.forLanguageTag("es-ES")
            )
        )
        .replaceFirstChar { it.uppercase(Locale.forLanguageTag("es-ES")) }
}

suspend fun copyImageToInternalStorage(
    context: Context,
    uri: Uri
): String = withContext(Dispatchers.IO) {

    val imagesDir = File(context.filesDir, "exercise_images")
    if (!imagesDir.exists()) { imagesDir.mkdirs() }

    val extension = when (context.contentResolver.getType(uri)) {
        "image/png" -> ".png"
        "image/webp" -> ".webp"
        "image/gif" -> ".gif"
        else -> ".jpg"
    }

    val destination = File(
        imagesDir,
        "${UUID.randomUUID()}$extension"
    )

    context.contentResolver.openInputStream(uri).use { input ->
        requireNotNull(input) { "No se ha podido abrir la imagen" }
        destination.outputStream().use { output -> input.copyTo(output) }
    }

    destination.absolutePath
}