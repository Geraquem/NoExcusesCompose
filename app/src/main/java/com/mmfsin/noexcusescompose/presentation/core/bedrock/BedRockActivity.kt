package com.mmfsin.noexcusescompose.presentation.core.bedrock

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mmfsin.noexcusescompose.presentation.core.navigation.NavigationExercises
import com.mmfsin.noexcusescompose.util.BEDROCK_BOOL_ARGS
import com.mmfsin.noexcusescompose.util.BEDROCK_NAV_GRAPH
import com.mmfsin.noexcusescompose.util.BEDROCK_STR_ARGS
import com.mmfsin.noexcusescompose.util.NAV_EXERCISES
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BedRockActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val navGraph = intent?.getStringExtra(BEDROCK_NAV_GRAPH)
        val strArgs = intent?.getStringExtra(BEDROCK_STR_ARGS)
        val boolArgs = intent?.getBooleanExtra(BEDROCK_BOOL_ARGS, false)

        setContent {
            when (navGraph) {
                NAV_EXERCISES -> NavigationExercises(mgroup = strArgs)
                else -> finish()
            }
        }
    }
}