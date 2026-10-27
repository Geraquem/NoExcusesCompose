package com.mmfsin.noexcuses.presentation.core.bedrock

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.mmfsin.noexcuses.presentation.core.navigation.NavigationDayDetail
import com.mmfsin.noexcuses.presentation.core.navigation.NavigationDfRoutines
import com.mmfsin.noexcuses.presentation.core.navigation.NavigationExercises
import com.mmfsin.noexcuses.presentation.core.navigation.NavigationFavorites
import com.mmfsin.noexcuses.presentation.core.navigation.NavigationMyRoutines
import com.mmfsin.noexcuses.presentation.core.navigation.NavigationNoteDetail
import com.mmfsin.noexcuses.presentation.core.navigation.NavigationRmAdd
import com.mmfsin.noexcuses.presentation.core.navigation.NavigationRmDetail
import com.mmfsin.noexcuses.util.BEDROCK_BOOL_ARGS
import com.mmfsin.noexcuses.util.BEDROCK_NAV_GRAPH
import com.mmfsin.noexcuses.util.BEDROCK_STR_ARGS
import com.mmfsin.noexcuses.util.BEDROCK_STR_ARGS_2
import com.mmfsin.noexcuses.util.NAV_DAY_DETAIL
import com.mmfsin.noexcuses.util.NAV_DF_ROUTINES
import com.mmfsin.noexcuses.util.NAV_EXERCISES
import com.mmfsin.noexcuses.util.NAV_FAVORITES
import com.mmfsin.noexcuses.util.NAV_MY_ROUTINES
import com.mmfsin.noexcuses.util.NAV_NOTE_DETAIL
import com.mmfsin.noexcuses.util.NAV_RM_ADD
import com.mmfsin.noexcuses.util.NAV_RM_DETAIL
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BedRockActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val navGraph = intent?.getStringExtra(BEDROCK_NAV_GRAPH)
        val strArgs = intent?.getStringExtra(BEDROCK_STR_ARGS)
        val strArgs2 = intent?.getStringExtra(BEDROCK_STR_ARGS_2)
        val boolArgs = intent?.getBooleanExtra(BEDROCK_BOOL_ARGS, false)

        setContent {
            when (navGraph) {
                NAV_EXERCISES -> NavigationExercises(mgroupId = strArgs)
                NAV_DF_ROUTINES -> NavigationDfRoutines()
                NAV_MY_ROUTINES -> NavigationMyRoutines()

                NAV_DAY_DETAIL -> {
                    if (strArgs != null) NavigationDayDetail(routineId = strArgs, dayId = strArgs2)
                    else finish()
                }

                NAV_FAVORITES -> NavigationFavorites()
                NAV_RM_ADD -> NavigationRmAdd()
                NAV_RM_DETAIL -> strArgs?.let { NavigationRmDetail(exerciseId = strArgs) } ?: run { finish() }
                NAV_NOTE_DETAIL -> NavigationNoteDetail(noteId = strArgs)
                else -> finish()
            }
        }
    }
}