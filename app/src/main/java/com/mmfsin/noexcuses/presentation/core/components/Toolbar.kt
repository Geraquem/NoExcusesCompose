@file:OptIn(ExperimentalMaterial3Api::class)

package com.mmfsin.noexcuses.presentation.core.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.presentation.core.theme.Black
import com.mmfsin.noexcuses.presentation.core.theme.White
import com.mmfsin.noexcuses.presentation.core.theme.montserrat_bold

@Preview
@Composable
fun CustomToolbarPV() {
    Column {
        CustomMainToolbar({})
        SpacerSmall()
        CustomToolbar(
            true, {}, R.string.app_name, "",
            true, R.drawable.ic_fav_on, {},
        )
    }
}

@Composable
fun CustomMainToolbar(onRightIconClick: () -> Unit) {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                SpacerSmall(horizontal = true)

                //                Icon(
                //                    painterResource(R.drawable.ic_logo),
                //                    null,
                //                    modifier = Modifier.size(30.dp)
                //                )

                //                SpacerSmall(horizontal = true)

                MediumText(
                    text = R.string.app_name,
                    allCaps = true,
                    fontSize = 22.sp,
                    fontFamily = montserrat_bold,
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = White),
    )
}

@Composable
fun CustomToolbar(
    showGoBack: Boolean = true,
    goBack: () -> Unit,
    title: Int = R.string.empty,
    titleString: String? = null,
    showIconRight: Boolean = false,
    iconRight: Int = R.drawable.ic_save,
    iconRightClick: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (showGoBack) {
                    IconButton(onClick = { goBack() }) {
                        Icon(
                            painterResource(R.drawable.ic_arrow_back), null,
                            tint = Black
                        )
                    }
                    SpacerSmall(horizontal = true)
                }
                if (titleString != null) {
                    MediumText(
                        text = titleString,
                        color = Black,
                        fontWeight = FontWeight.SemiBold
                    )
                } else {
                    MediumText(
                        text = title,
                        color = Black,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                if (showIconRight) {
                    Spacer(Modifier.weight(1f))
                    IconButton(onClick = { iconRightClick() }) {
                        Icon(
                            painterResource(iconRight), null,
                            tint = Black
                        )
                    }
                    SpacerSmall(horizontal = true)
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = White),
    )
}