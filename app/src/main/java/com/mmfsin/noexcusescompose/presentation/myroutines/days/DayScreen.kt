@file:OptIn(ExperimentalMaterial3Api::class)

package com.mmfsin.noexcusescompose.presentation.myroutines.days

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mmfsin.noexcusescompose.R
import com.mmfsin.noexcusescompose.presentation.core.components.CustomTextField
import com.mmfsin.noexcusescompose.presentation.core.components.CustomToolbar
import com.mmfsin.noexcusescompose.presentation.core.components.ErrorDialog
import com.mmfsin.noexcusescompose.presentation.core.components.LoadingLottie
import com.mmfsin.noexcusescompose.presentation.core.components.MediumText
import com.mmfsin.noexcusescompose.presentation.core.components.OutlinedButtonCustomIcon
import com.mmfsin.noexcusescompose.presentation.core.components.SmallText
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerMedium
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerMini
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerSmall
import com.mmfsin.noexcusescompose.presentation.core.theme.Black
import com.mmfsin.noexcusescompose.presentation.core.theme.GrayMedium
import com.mmfsin.noexcusescompose.presentation.core.theme.RedHard
import com.mmfsin.noexcusescompose.presentation.core.theme.montserrat_bold

@Preview
@Composable
fun DayScreenPV() {
    DayContent(
        uiStates = DayStates(
            isLoading = false,
            dayName = "Pecho y tríceps"
        ),
        {}, {}, {}, {},
        {}, {}
    )
}

@Composable
fun DayScreen(
    viewModel: DayViewModel = hiltViewModel(),
    goBack: () -> Unit,
    goToMuscularGroups: (String, String) -> Unit
) {
    val uiStates by viewModel.uiState.collectAsStateWithLifecycle()

    DayContent(
        uiStates = uiStates,
        goBack = { goBack() },
        updateDayName = { viewModel.updateDayName(it) },
        createDay = { viewModel.createOrEditDay() },
        handleBack = { viewModel.handleBack() },
        goToMuscularGroups = { goToMuscularGroups(uiStates.dayId, uiStates.dayName) },
        sww = { viewModel.sww(it) },
    )
}

@Composable
fun DayContent(
    uiStates: DayStates,
    goBack: () -> Unit,
    updateDayName: (String) -> Unit,
    createDay: () -> Unit,
    handleBack: () -> Unit,
    goToMuscularGroups: () -> Unit,
    sww: (Boolean) -> Unit
) {

    val listState = rememberLazyListState()

    Scaffold(
        topBar = {
            CustomToolbar(
                goBack = { handleBack() },
                titleString = uiStates.routineName,
                iconRight = R.drawable.ic_add_circle,
                showIconRight = true,
                iconRightClick = { createDay() }
            )
        }
    ) { innerPadding ->
        Box(
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
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    item {
                        if (uiStates.emptyNameError) {
                            SmallText(
                                text = R.string.my_routines_day_error_empty_name,
                                fontFamily = montserrat_bold,
                                color = RedHard,
                            )
                            SpacerSmall()
                        }

                        Row {
                            MediumText(
                                text = R.string.my_routines_day,
                                fontFamily = montserrat_bold,
                                modifier = Modifier.padding(start = 6.dp)
                            )

                            SpacerMini(horizontal = true)

                            if (uiStates.dayOrder != -1) {
                                MediumText(
                                    text = "${uiStates.dayOrder}",
                                    fontFamily = montserrat_bold,
                                )
                            }
                        }

                        SpacerMini()
                        CustomTextField(
                            value = uiStates.dayName,
                            onValueChange = { updateDayName(it) },
                            lengthVisibility = true,
                        )

                        SpacerMedium()

                        MediumText(
                            text = R.string.my_routines_day_exercises,
                            fontFamily = montserrat_bold,
                            modifier = Modifier.padding(start = 6.dp)
                        )
                    }

                    itemsIndexed(
                        items = uiStates.exercises,
                        key = { _, exercise -> exercise.id }
                    ) { i, day ->
                        Column() {
                            Box(Modifier.fillMaxWidth().height(20.dp).background(RedHard))
                            SpacerMini()
                        }
                    }

                    item {
                        OutlinedButtonCustomIcon(
                            onClick = { goToMuscularGroups() },
                            text = R.string.my_routines_day_add_exercises,
                            icon = R.drawable.ic_add,
                            modifier = Modifier.fillMaxWidth(),
                            color = Black
                        )
                        SpacerMedium()
                    }
                }
            }
        }

        if (uiStates.shouldGoBack) goBack()
        if (uiStates.sww) ErrorDialog { sww(false) }
        if (uiStates.isLoading) LoadingLottie()
    }
    BackHandler {
        handleBack()
    }
}