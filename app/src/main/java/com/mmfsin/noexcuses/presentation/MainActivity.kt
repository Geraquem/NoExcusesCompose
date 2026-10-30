package com.mmfsin.noexcuses.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.mmfsin.noexcuses.data.migration.RealmMigrator
import com.mmfsin.noexcuses.presentation.core.navigation.NavigationMain
import com.mmfsin.noexcuses.presentation.core.theme.NoExcusesTheme
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var realmMigrator: RealmMigrator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        lifecycleScope.launch {
            realmMigrator.migrateIfNeeded()
            setContent { NoExcusesTheme { NavigationMain() } }
        }
    }
}