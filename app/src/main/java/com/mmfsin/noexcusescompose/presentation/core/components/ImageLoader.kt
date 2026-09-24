package com.mmfsin.noexcusescompose.presentation.core.components

import android.os.Build.VERSION.SDK_INT
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.gif.AnimatedImageDecoder
import coil3.gif.GifDecoder

@Composable
fun ImageGif(
    url: String?,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
//    val imageLoader = ImageLoader.Builder(context).components {
//        add(GifDecoder.Factory())
//    }.build()

    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(AnimatedImageDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    AsyncImage(
        model = url,
        contentDescription = null,
        modifier = modifier,
        imageLoader = imageLoader
    )
}
