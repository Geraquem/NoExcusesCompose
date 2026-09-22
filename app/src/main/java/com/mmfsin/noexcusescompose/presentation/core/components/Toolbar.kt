@file:OptIn(ExperimentalMaterial3Api::class)

package com.mmfsin.noexcusescompose.presentation.core.components

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.tooling.preview.Preview
import com.mmfsin.noexcusescompose.R
import com.mmfsin.noexcusescompose.presentation.core.theme.BackgroundBlack
import com.mmfsin.noexcusescompose.presentation.core.theme.GrayLight
import com.mmfsin.noexcusescompose.presentation.core.theme.White
import com.mmfsin.noexcusescompose.presentation.core.theme.montserrat_bold

@Preview
@Composable
fun CustomToolbarPV() {
    Column {
        CustomMainToolbar({})
        SpacerSmall()
        CustomToolbar({}, true, R.string.app_name)
    }
}

@Composable
fun CustomMainToolbar(onRightIconClick: () -> Unit) {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                MediumText(
                    text = R.string.app_name,
                    allCaps = true,
                    fontFamily = montserrat_bold,
                )

                Spacer(Modifier.weight(1f))

                IconButton(onClick = { onRightIconClick() }) {
                    Icon(painterResource(R.drawable.ic_dots), null)
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = GrayLight),
    )
}

@Composable
fun CustomToolbar(
    goBack: () -> Unit,
    showGoBack: Boolean,
    title: Int = R.string.empty
) {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Row(
                    modifier = Modifier.clickable(onClick = { goBack() }),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (showGoBack) {
                        Icon(
                            painterResource(R.drawable.ic_arrow_back), null,
                            tint = White
                        )
                        SpacerSmall(horizontal = true)
                    }
                    MediumText(
                        text = title
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundBlack),
    )
}