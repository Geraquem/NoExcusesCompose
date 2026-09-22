package com.mmfsin.noexcusescompose.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mmfsin.noexcusescompose.presentation.core.navigation.NavigationMain
import com.mmfsin.noexcusescompose.presentation.core.theme.NoExcusesComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { NoExcusesComposeTheme { NavigationMain() } }
    }
}