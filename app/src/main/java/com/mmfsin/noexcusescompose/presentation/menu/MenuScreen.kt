package com.mmfsin.noexcusescompose.presentation.menu

import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.mmfsin.noexcusescompose.R
import com.mmfsin.noexcusescompose.domain.models.MuscularGroup
import com.mmfsin.noexcusescompose.domain.models.Note
import com.mmfsin.noexcusescompose.domain.models.getExampleRoutines
import com.mmfsin.noexcusescompose.domain.models.getMuscularGroupsExamples
import com.mmfsin.noexcusescompose.presentation.core.components.MediumText
import com.mmfsin.noexcusescompose.presentation.core.components.SmallText
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerMedium
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerMini
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerSmall
import com.mmfsin.noexcusescompose.presentation.core.theme.BlueLight
import com.mmfsin.noexcusescompose.presentation.core.theme.GrayLight
import com.mmfsin.noexcusescompose.presentation.core.theme.GrayMedium
import com.mmfsin.noexcusescompose.presentation.core.theme.GreenLight
import com.mmfsin.noexcusescompose.presentation.core.theme.OrangeLight
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
            muscularGroups = getMuscularGroupsExamples(),
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
        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .background(GrayMedium),
            state = rememberLazyListState(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {

            item {
                Column(Modifier.padding(horizontal = 16.dp)) {
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
                Row() {
                    Card(
                        modifier = Modifier.weight(1f).height(200.dp).padding(start = 16.dp),
                        colors = CardDefaults.cardColors(containerColor = White),
                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
                        onClick = { goToDfRoutines() }
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize().padding(12.dp),
                        ) {
                            MediumText(
                                text = R.string.menu_default_routines_title,
                                modifier = Modifier.background(GreenLight, RoundedCornerShape(12.dp))
                                    .padding(horizontal = 12.dp, vertical = 2.dp)
                            )
                            SpacerSmall()
                            SmallText(text = R.string.menu_default_routines_description)

                            Spacer(Modifier.weight(1f))

                            Box(
                                Modifier.size(28.dp)
                                    .background(
                                        color = GrayLight,
                                        shape = CircleShape
                                    ).padding(4.dp)
                                    .align(Alignment.End),
                            ) {
                                Icon(
                                    painterResource(R.drawable.ic_arrow_right), null,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }

                    SpacerSmall(horizontal = true)

                    Card(
                        modifier = Modifier.weight(1.25f).height(200.dp).padding(end = 16.dp),
                        colors = CardDefaults.cardColors(containerColor = White),
                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
                        onClick = { goToMyRoutines() }
                    ) {
                        Column(modifier = Modifier.fillMaxSize().padding(12.dp)) {
                            MediumText(
                                text = R.string.menu_my_routines_title,
                                modifier = Modifier.background(BlueLight, RoundedCornerShape(12.dp))
                                    .padding(horizontal = 12.dp, vertical = 2.dp)
                            )
                            SpacerSmall()
                            SmallText(text = R.string.menu_my_routines_description)

                            Spacer(Modifier.weight(1f))

                            Box(
                                Modifier.size(28.dp)
                                    .background(
                                        color = GrayLight,
                                        shape = CircleShape
                                    ).padding(4.dp)
                                    .align(Alignment.End),
                            ) {
                                Icon(
                                    painterResource(R.drawable.ic_arrow_right), null,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }

                }
            }

            item {
                Column {
                    Card(
                        modifier = Modifier.fillMaxWidth().height(100.dp).padding(horizontal = 16.dp),
                        colors = CardDefaults.cardColors(containerColor = White),
                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
                        onClick = { goToExercises(null) }
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize()
                                .clip(RoundedCornerShape(12.dp))
                        ) {
                            //                            AsyncImage(
                            //                                model = "https://i.pinimg.com/1200x/81/19/d3/8119d30283db4d8e9b74327ea200fd75.jpg",
                            //                                contentDescription = null,
                            //                                modifier = Modifier.fillMaxSize().alpha(0.8f),
                            //                                contentScale = ContentScale.Crop
                            //                            )

                            Row(Modifier.padding(12.dp)) {
                                Column(Modifier.weight(1f)) {
                                    MediumText(
                                        text = R.string.menu_exercises_title,
                                        modifier = Modifier
                                            .background(GrayMedium, RoundedCornerShape(12.dp))
                                            .padding(horizontal = 12.dp, vertical = 2.dp)
                                    )
                                    SpacerMini()
                                    SmallText(
                                        text = R.string.menu_exercises_description,
                                        color = White
                                    )
                                }

                                SpacerSmall(horizontal = true)

                                Box(
                                    Modifier.size(28.dp)
                                        .background(
                                            color = GrayLight,
                                            shape = CircleShape
                                        ).padding(4.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painterResource(R.drawable.ic_arrow_right), null,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                    }

                    SpacerSmall()

                    if (uiStates.muscularGroups.isNotEmpty()) {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            uiStates.muscularGroups.forEachIndexed { i, mGroup ->
                                item {
                                    MuscularGroupBox(
                                        position = i,
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
                Card(
                    modifier = Modifier.fillMaxWidth().height(100.dp).padding(horizontal = 16.dp),
                    colors = CardDefaults.cardColors(containerColor = White),
                    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
                    onClick = { goToFavorites() }
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                            .clip(RoundedCornerShape(12.dp))
                    ) {
                        //                        AsyncImage(
                        //                            model = "https://i.pinimg.com/736x/91/f2/83/91f2839b0077b983a95e3963da5ef607.jpg",
                        //                            contentDescription = null,
                        //                            modifier = Modifier.fillMaxSize(),
                        //                            contentScale = ContentScale.Crop
                        //                        )

                        Row(Modifier.padding(12.dp)) {
                            MediumText(
                                text = R.string.menu_favs_title,
                                modifier = Modifier
                                    .background(OrangeLight, RoundedCornerShape(12.dp))
                                    .padding(horizontal = 12.dp, vertical = 2.dp)
                            )

                            Spacer(Modifier.weight(1f))

                            Box(
                                Modifier.size(28.dp)
                                    .background(
                                        color = GrayLight,
                                        shape = CircleShape
                                    ).padding(4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    painterResource(R.drawable.ic_arrow_right), null,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
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
    position: Int,
    mGroup: MuscularGroup,
    onClick: () -> Unit
) {
    Row {
        if (position == 0) SpacerMedium(horizontal = true)
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
    }
}