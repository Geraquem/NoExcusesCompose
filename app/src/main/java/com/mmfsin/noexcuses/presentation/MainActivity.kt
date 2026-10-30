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

//        lifecycleScope.launch(Dispatchers.IO) {
//            try {
//                val result = RealmMigrationManager.inspectBackup(applicationContext)
//
//                Log.d("REALM_MIGRATION", result.toString())
//
//            } catch (e: Exception) {
//                Log.e("REALM_MIGRATION", "Error abriendo Realm", e)
//            }
//        }

        setContent { NoExcusesTheme { NavigationMain() } }
    }
}