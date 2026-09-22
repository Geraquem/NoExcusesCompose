package com.mmfsin.noexcusescompose.presentation.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mmfsin.noexcusescompose.presentation.core.theme.GrayLight

@Preview
@Composable
fun MenuScreenPV() {
    MenuContent(
        uiStates = MenuStates()
    )
}

@Composable
fun MenuScreen(viewModel: MenuViewModel = hiltViewModel()) {
    val uiStates by viewModel.uiState.collectAsStateWithLifecycle()
    MenuContent(
        uiStates = uiStates
    )
}

@Composable
fun MenuContent(
    uiStates: MenuStates,

    ) {
    Scaffold{ innerPadding ->
        Column(
            modifier = Modifier
                .background(GrayLight)
                .padding(innerPadding)
        ) {

        }
    }
}