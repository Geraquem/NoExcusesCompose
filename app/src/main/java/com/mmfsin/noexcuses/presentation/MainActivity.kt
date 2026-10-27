package com.mmfsin.noexcuses.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mmfsin.noexcuses.presentation.core.navigation.NavigationMain
import com.mmfsin.noexcuses.presentation.core.theme.NoExcusesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { NoExcusesTheme { NavigationMain() } }
    }
}