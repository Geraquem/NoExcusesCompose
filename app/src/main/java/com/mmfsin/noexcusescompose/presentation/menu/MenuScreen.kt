package com.mmfsin.noexcusescompose.presentation.menu

import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mmfsin.noexcusescompose.R
import com.mmfsin.noexcusescompose.domain.models.Note
import com.mmfsin.noexcusescompose.domain.models.getExampleRoutines
import com.mmfsin.noexcusescompose.presentation.core.components.MediumText
import com.mmfsin.noexcusescompose.presentation.core.components.SmallText
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerCustom
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerMedium
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerMini
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerSmall
import com.mmfsin.noexcusescompose.presentation.core.theme.Black
import com.mmfsin.noexcusescompose.presentation.core.theme.BlueMedium
import com.mmfsin.noexcusescompose.presentation.core.theme.GrayMedium
import com.mmfsin.noexcusescompose.presentation.core.theme.GreenHard
import com.mmfsin.noexcusescompose.presentation.core.theme.OrangeHard
import com.mmfsin.noexcusescompose.presentation.core.theme.White
import com.mmfsin.noexcusescompose.presentation.core.theme.montserrat_bold
import com.mmfsin.noexcusescompose.presentation.menu.components.UnpinDialog
import com.mmfsin.noexcusescompose.presentation.myroutines.routines.RoutineBox
import com.mmfsin.noexcusescompose.presentation.notes.NoteBox
import com.mmfsin.noexcusescompose.util.NAV_DAY_DETAIL
import com.mmfsin.noexcusescompose.util.NAV_DF_ROUTINES
import com.mmfsin.noexcusescompose.util.NAV_EXERCISES
import com.mmfsin.noexcusescompose.util.NAV_FAVORITES
import com.mmfsin.noexcusescompose.util.NAV_MY_ROUTINES
import com.mmfsin.noexcusescompose.util.NAV_NOTE_DETAIL
import com.mmfsin.noexcusescompose.util.openBedRockActivity

@Preview
@Composable
fun MenuScreenPV() {
    MenuContent(
        uiStates = MenuStates(
            actualRoutine = getExampleRoutines().first(),
            actualNote = Note(
                id = "",
                title = "Notita 1",
                description = "Description notita",
                date = "12 de agosto de 1245",
                pinned = true
            ),
        ),
        {}, {}, {}, {},
        { _, _ -> }, {}, {}, {},
        {}, {}
    )
}

@Composable
fun MenuScreen(viewModel: MenuViewModel = hiltViewModel()) {
    val uiStates by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    MenuContent(
        uiStates = uiStates,
        goToExercises = { context.openBedRockActivity(NAV_EXERCISES, it) },
        goToDfRoutines = { context.openBedRockActivity(NAV_DF_ROUTINES) },
        goToMyRoutines = { context.openBedRockActivity(NAV_MY_ROUTINES) },
        goToFavorites = { context.openBedRockActivity(NAV_FAVORITES) },

        openDayDetail = { rId, dId -> context.openBedRockActivity(NAV_DAY_DETAIL, rId, dId) },
        showUnpinRoutineDialog = { viewModel.showUnpinRoutineDialog(it) },
        unpinRoutine = { viewModel.unpinRoutine(it) },

        openNoteDetail = { context.openBedRockActivity(NAV_NOTE_DETAIL, it) },
        showUnpinNoteDialog = { viewModel.showUnpinNoteDialog(it) },
        unpinNote = { viewModel.unpinNote(it) },
    )
}

@Composable
fun MenuContent(
    uiStates: MenuStates,
    goToExercises: (String?) -> Unit,
    goToDfRoutines: () -> Unit,
    goToMyRoutines: () -> Unit,
    goToFavorites: () -> Unit,

    openDayDetail: (String, String?) -> Unit,
    showUnpinRoutineDialog: (Boolean) -> Unit,
    unpinRoutine: (String) -> Unit,

    openNoteDetail: (String) -> Unit,
    showUnpinNoteDialog: (Boolean) -> Unit,
    unpinNote: (String) -> Unit,
) {
    CompositionLocalProvider(
        LocalOverscrollFactory provides null
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .background(GrayMedium)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
        ) {

            SpacerSmall()

            uiStates.actualRoutine?.let { routine ->
                SmallText(
                    text = R.string.menu_my_actual_routine,
                    modifier = Modifier.padding(start = 6.dp)
                )

                SpacerMini()

                RoutineBox(
                    routine = routine,
                    editRoutine = {},
                    goToDayDetail = { dayId -> openDayDetail(routine.id, dayId) },
                    onDayLongClick = {},
                    updatePushpin = { showUnpinRoutineDialog(true) },
                    showPinnedDate = true
                )
            }

            uiStates.actualNote?.let { note ->
                SpacerMedium()
                NoteBox(
                    note = note,
                    onClick = { openNoteDetail(note.id) },
                    updatePushpin = { showUnpinNoteDialog(true) }
                )
            }

            SpacerMedium()

            MenuBox(
                icon = R.drawable.ic_df_routines,
                iconColor = GreenHard,
                title = R.string.menu_default_routines_title,
                titleColor = GreenHard,
                description = R.string.menu_default_routines_description,
                onClick = { goToDfRoutines() }
            )

            Column {
                MenuBox(
                    icon = R.drawable.ic_my_routines,
                    iconColor = BlueMedium,
                    title = R.string.menu_my_routines_title,
                    titleColor = BlueMedium,
                    description = R.string.menu_my_routines_description,
                    onClick = { goToMyRoutines() }
                )
            }

            Column {
                MenuBox(
                    icon = R.drawable.ic_dumbbell,
                    iconColor = Black,
                    title = R.string.menu_exercises_title,
                    titleColor = Black,
                    description = R.string.menu_exercises_description,
                    onClick = { goToExercises(null) }
                )
                if (uiStates.muscularGroups.isNotEmpty()) {
                    SpacerSmall()
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        uiStates.muscularGroups.forEach { mGroup ->
                            item {
                                Box(Modifier.size(120.dp).background(GreenHard).clickable(onClick = {
                                    goToExercises(mGroup.id)
                                }))
                            }
                        }
                    }
                }
            }

            MenuBox(
                icon = R.drawable.ic_fav_on,
                iconColor = OrangeHard,
                title = R.string.menu_favs_title,
                titleColor = OrangeHard,
                description = R.string.menu_favs_description,
                onClick = { goToFavorites() }
            )

            SpacerCustom(64.dp)
        }
    }

    if (uiStates.showUnpinRoutineDialog) {
        uiStates.actualRoutine?.let { routine ->
            UnpinDialog(
                name = routine.name,
                cancel = { showUnpinRoutineDialog(false) },
                accept = { unpinRoutine(routine.id) }
            )
        }
    }

    if (uiStates.showUnpinNoteDialog) {
        uiStates.actualNote?.let { note ->
            UnpinDialog(
                name = note.title,
                cancel = { showUnpinNoteDialog(false) },
                accept = { unpinNote(note.id) }
            )
        }
    }
}

@Composable
fun MenuBox(
    icon: Int,
    iconColor: Color,
    title: Int,
    titleColor: Color,
    description: Int,
    onClick: () -> Unit
) {
    Column(
        Modifier.fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(White)
            .clickable(onClick = { onClick() })
            .padding(12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painterResource(icon), null,
                tint = iconColor
            )
            SpacerSmall(horizontal = true)
            MediumText(
                text = title,
                color = titleColor,
                fontFamily = montserrat_bold,
                fontSize = 18.sp
            )
        }

        SpacerSmall()

        MediumText(
            text = description
        )
    }
}