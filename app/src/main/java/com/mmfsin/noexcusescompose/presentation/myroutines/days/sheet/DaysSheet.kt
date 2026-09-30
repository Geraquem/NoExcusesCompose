@file:OptIn(ExperimentalMaterial3Api::class)

package com.mmfsin.noexcusescompose.presentation.myroutines.days.sheet

import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mmfsin.noexcusescompose.R
import com.mmfsin.noexcusescompose.domain.models.Day
import com.mmfsin.noexcusescompose.domain.models.getExampleDays
import com.mmfsin.noexcusescompose.presentation.core.components.MediumText
import com.mmfsin.noexcusescompose.presentation.core.components.OutlinedButtonCustomIcon
import com.mmfsin.noexcusescompose.presentation.core.components.SmallText
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerCustom
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerMedium
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerSmall
import com.mmfsin.noexcusescompose.presentation.core.theme.Black
import com.mmfsin.noexcusescompose.presentation.core.theme.GrayMedium
import com.mmfsin.noexcusescompose.presentation.core.theme.OrangeLight
import com.mmfsin.noexcusescompose.presentation.core.theme.White
import com.mmfsin.noexcusescompose.presentation.core.theme.barlow
import com.mmfsin.noexcusescompose.presentation.core.theme.montserrat_bold
import kotlinx.coroutines.launch

@Preview
@Composable
fun DaysSheetPV() {
    DaysContent(
        uiStates = DaysSheetStates(
            isLoading = false,
            days = getExampleDays()
            //            days = emptyList()
        ),
        routineName = "Rutina 1",
        {}, {}
    )
}

@Composable
fun DaysSheet(
    viewModel: DaysSheetViewModel = hiltViewModel(),
    onDismiss: () -> Unit,
    routineId: String,
    routineName: String,
    onDayClick: (String?) -> Unit,
) {

    val uiStates by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(routineId) {
        viewModel.getDays(routineId)
    }

    DaysContent(
        uiStates = uiStates,
        routineName = routineName,
        onDismiss = { onDismiss() },
        onDayClick = { onDayClick(it) },
    )

}

@Composable
fun DaysContent(
    uiStates: DaysSheetStates,
    routineName: String,
    onDismiss: () -> Unit,
    onDayClick: (String?) -> Unit,
) {

    val listState = rememberLazyListState()

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    val scope = rememberCoroutineScope()
    fun closeDialog(action: () -> Unit = {}) {
        scope.launch {
            sheetState.hide()
            onDismiss()
            action()
        }
    }
    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = sheetState,
        dragHandle = { }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .background(White)
                .padding(horizontal = 16.dp),
        ) {
            SpacerMedium()
            Box(
                Modifier.width(64.dp).height(4.dp)
                    .clip(RoundedCornerShape(50))
                    .background(GrayMedium)
                    .align(Alignment.CenterHorizontally)
            )

            SpacerCustom(32.dp)

            Row(verticalAlignment = Alignment.CenterVertically) {
                MediumText(
                    text = routineName,
                    fontFamily = montserrat_bold
                )

                if (!uiStates.isLoading && uiStates.days.isEmpty()) {
                    SpacerSmall(horizontal = true)
                    MediumText("-")
                    SpacerSmall(horizontal = true)
                    MediumText(
                        text = R.string.my_routines_days_empty
                    )
                }
            }

            CompositionLocalProvider(
                LocalOverscrollFactory provides null
            ) {
                LazyColumn(
                    state = listState,
                    contentPadding = PaddingValues(vertical = 12.dp)
                ) {
                    itemsIndexed(
                        items = uiStates.days,
                        key = { _, day -> day.id }
                    ) { i, day ->
                        DayBox(
                            position = i + 1,
                            day = day,
                            onDayClick = { closeDialog { onDayClick(day.id) } }
                        )
                    }

                    item {
                        SpacerSmall()
                        OutlinedButtonCustomIcon(
                            onClick = { closeDialog { onDayClick(null) } },
                            text = R.string.my_routines_create_day,
                            icon = R.drawable.ic_add,
                            modifier = Modifier.fillMaxWidth(),
                            color = Black
                        )
                        SpacerMedium()
                    }
                }
            }
        }
    }
}

@Composable
fun DayBox(
    position: Int,
    day: Day,
    onDayClick: () -> Unit
) {
    Row(
        Modifier.fillMaxWidth()
            .clickable(onClick = { onDayClick() })
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(48.dp).background(OrangeLight, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            MediumText(
                text = "D$position",
                fontFamily = barlow,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = White
            )
        }

        SpacerSmall(horizontal = true)

        Column(verticalArrangement = Arrangement.Center) {
            SmallText(
                text = "Día $position",
                fontFamily = montserrat_bold
            )
            MediumText(
                text = day.title
            )
        }
    }
}