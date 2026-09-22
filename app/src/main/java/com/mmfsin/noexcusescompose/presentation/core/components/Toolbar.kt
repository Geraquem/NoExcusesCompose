@file:OptIn(ExperimentalMaterial3Api::class)

package com.mmfsin.noexcusescompose.presentation.core.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.mmfsin.noexcusescompose.R
import com.mmfsin.noexcusescompose.presentation.core.theme.BackgroundBlack
import com.mmfsin.noexcusescompose.presentation.core.theme.White
import com.mmfsin.noexcusescompose.presentation.core.theme.kineks

@Preview
@Composable
fun CustomToolbarPV() {
    CustomToolbar({}, true, R.string.app_name)
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