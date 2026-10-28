package com.mmfsin.noexcuses.presentation.menu.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.presentation.core.components.MediumText
import com.mmfsin.noexcuses.presentation.core.components.SmallText
import com.mmfsin.noexcuses.presentation.core.components.SpacerMini
import com.mmfsin.noexcuses.presentation.core.components.SpacerSmall
import com.mmfsin.noexcuses.presentation.core.theme.BlueLight
import com.mmfsin.noexcuses.presentation.core.theme.White
import com.mmfsin.noexcuses.presentation.core.theme.montserrat_bold

@Preview
@Composable
fun MyRoutinesItemPV() {
    MyRoutinesItem(
        bgImage = "",
        onClick = {})
}

@Composable
fun MyRoutinesItem(
    bgImage: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().height(180.dp).padding(horizontal = 12.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        onClick = { onClick() }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
                .clip(RoundedCornerShape(12.dp))
        ) {
            AsyncImage(
                model = bgImage,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFFFFFFFF).copy(alpha = 0.80f),
                                Color(0xFFFFFFFF).copy(alpha = 0.60f),
                                Color.Transparent
                            ),
                            startX = 0f,
                            endX = 850f
                        )
                    )
            )

            Row(Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1.5f).padding(12.dp)) {
                    MediumText(
                        text = R.string.menu_my_routines_title,
                        fontFamily = montserrat_bold,
                        fontSize = 28.sp
                    )
                    SpacerMini()
                    SmallText(text = R.string.menu_my_routines_description)
                    Spacer(Modifier.weight(1f))
                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(
                            modifier = Modifier.background(BlueLight, RoundedCornerShape(8.dp))
                                .padding(4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painterResource(R.drawable.ic_dumbbell), null,
                                modifier = Modifier.size(20.dp).rotate(145f)
                            )
                        }
                        SpacerSmall(horizontal = true)
                        SmallText(
                            text = "Personaliza tu forma de entrenar",
                        )
                    }
                }
                Box(Modifier.weight(1f))
            }
        }
    }
}