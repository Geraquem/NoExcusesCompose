package com.mmfsin.noexcuses.presentation.menu

import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import coil3.compose.AsyncImage
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.domain.models.MuscularGroup
import com.mmfsin.noexcuses.domain.models.getMuscularGroupsExamples
import com.mmfsin.noexcuses.presentation.core.components.LoadingFullScreen
import com.mmfsin.noexcuses.presentation.core.components.MediumText
import com.mmfsin.noexcuses.presentation.core.components.SmallText
import com.mmfsin.noexcuses.presentation.core.components.SpacerCustom
import com.mmfsin.noexcuses.presentation.core.components.SpacerMini
import com.mmfsin.noexcuses.presentation.core.components.SpacerSmall
import com.mmfsin.noexcuses.presentation.core.theme.GrayMedium
import com.mmfsin.noexcuses.presentation.core.theme.White
import com.mmfsin.noexcuses.presentation.core.theme.montserrat_bold
import com.mmfsin.noexcuses.presentation.menu.components.UnpinDialog
import com.mmfsin.noexcuses.presentation.myroutines.routines.RoutineBox
import com.mmfsin.noexcuses.presentation.menu.items.DfRoutinesItem
import com.mmfsin.noexcuses.presentation.menu.items.ExercisesItem
import com.mmfsin.noexcuses.presentation.menu.items.FavoritesItem
import com.mmfsin.noexcuses.presentation.menu.items.MyRoutinesItem
import com.mmfsin.noexcuses.presentation.notes.NoteBox
import com.mmfsin.noexcuses.util.NAV_DAY_DETAIL
import com.mmfsin.noexcuses.util.NAV_DF_ROUTINES
import com.mmfsin.noexcuses.util.NAV_EXERCISES
import com.mmfsin.noexcuses.util.NAV_FAVORITES
import com.mmfsin.noexcuses.util.NAV_MY_ROUTINES
import com.mmfsin.noexcuses.util.NAV_NOTE_DETAIL
import com.mmfsin.noexcuses.util.openBedRockActivity

@Preview
@Composable
fun MenuScreenPV() {
    MenuContent(
        uiStates = MenuStates(
            isLoading = false,
            muscularGroups = getMuscularGroupsExamples(),
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
        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .background(GrayMedium),
            state = rememberLazyListState(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 12.dp)
        ) {

            item {
                Column(Modifier.padding(horizontal = 12.dp)) {
                    uiStates.actualRoutine?.let { routine ->
                        SpacerSmall()
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
                        SpacerSmall()
                        NoteBox(
                            note = note,
                            onClick = { openNoteDetail(note.id) },
                            updatePushpin = { showUnpinNoteDialog(true) },
                            deleteNote = {}
                        )
                    }
                }
            }
            item {
                MyRoutinesItem(
                    bgImage = uiStates.imageMyRoutines,
                    onClick = { goToMyRoutines() }
                )
            }

            item {
                DfRoutinesItem(
                    bgImage = uiStates.imageDfRoutines,
                    onClick = { goToDfRoutines() }
                )
            }

            item {
                Column {
                    ExercisesItem(
                        bgImage = uiStates.imageExercises,
                        onClick = { goToExercises(null) }
                    )

                    SpacerSmall()

                    if (uiStates.muscularGroups.isNotEmpty()) {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            uiStates.muscularGroups.forEachIndexed { i, mGroup ->
                                item {
                                    MuscularGroupBox(
                                        isFirst = i == 0,
                                        isLast = i == (uiStates.muscularGroups.size - 1),
                                        mGroup = mGroup,
                                        onClick = { goToExercises(mGroup.id) }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            item {
                FavoritesItem(
                    bgImage = uiStates.imageFavorites,
                    onClick = { goToFavorites() }
                )
            }
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

    if (uiStates.isLoading) LoadingFullScreen()
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

@Composable
fun MuscularGroupBox(
    isFirst: Boolean,
    isLast: Boolean,
    mGroup: MuscularGroup,
    onClick: () -> Unit
) {
    Row {
        if (isFirst) SpacerCustom(space = 12.dp, horizontal = true)
        Column(
            modifier = Modifier.background(White, RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
                .clickable(onClick = { onClick() }),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = mGroup.manImageURL,
                contentDescription = mGroup.name,
                modifier = Modifier.size(120.dp)
            )
            SpacerMini()
            MediumText(text = mGroup.name)
            SpacerMini()
        }
        if (isLast) SpacerCustom(space = 12.dp, horizontal = true)
    }
}