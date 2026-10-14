package com.mmfsin.noexcusescompose.presentation.routines

import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mmfsin.noexcusescompose.R
import com.mmfsin.noexcusescompose.domain.models.getExampleRoutines
import com.mmfsin.noexcusescompose.presentation.core.components.CustomToolbar
import com.mmfsin.noexcusescompose.presentation.core.components.ErrorDialog
import com.mmfsin.noexcusescompose.presentation.core.components.LoadingLottie
import com.mmfsin.noexcusescompose.presentation.core.components.SmallText
import com.mmfsin.noexcusescompose.presentation.core.theme.GrayMedium
import com.mmfsin.noexcusescompose.presentation.myroutines.routines.RoutineBox

@Preview
@Composable
fun DfRoutinesPV() {
    DfRoutinesContent(
        uiStates = DfRoutinesStates(
            routines = getExampleRoutines()
        ),
        {},
    )
}

@Composable
fun DfRoutinesScreen(
    viewModel: DfRoutinesViewModel = hiltViewModel(),
    goBack: () -> Unit,
    goToDayDetail: (String, String) -> Unit
) {
    val uiStates by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    DfRoutinesContent(
        uiStates = uiStates,
        goBack = { goBack() },
    )
}

@Composable
fun DfRoutinesContent(
    uiStates: DfRoutinesStates,
    goBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            CustomToolbar(
                goBack = { goBack() },
                title = R.string.my_routines_toolbar
            )
        }
    ) { innerPadding ->
        Column(
            Modifier.fillMaxSize()
                .background(GrayMedium)
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .padding(bottom = 12.dp)
        ) {
            CompositionLocalProvider(
                LocalOverscrollFactory provides null
            ) {
                LazyColumn(
                    state = rememberLazyListState(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    item {
                        SmallText(
                            text = R.string.df_routine_top_title,
                            allCaps = true
                        )
                    }

                    items(
                        items = uiStates.routines,
                        key = { routine -> routine.id }
                    ) { routine ->
                        RoutineBox(
                            routine = routine,
                            editRoutine = { },
                            goToDayDetail = { /*dayId -> goToDayDetail(routine.id, dayId)*/ },
                            onDayLongClick = { },
                            updatePushpin = {/* updatePinnedRoutine(routine.id)*/ },
                        )
                    }
                }
            }
        }

        //        if (uiStates.dayToDelete != null) {
        //            DeleteDayDialog(
        //                dayName = uiStates.dayToDelete.name,
        //                cancel = { updateDayToDelete(null) },
        //                delete = { deleteDay() }
        //            )
        //        }

        if (uiStates.sww) ErrorDialog { goBack() }
        if (uiStates.isLoading) LoadingLottie()
    }
}