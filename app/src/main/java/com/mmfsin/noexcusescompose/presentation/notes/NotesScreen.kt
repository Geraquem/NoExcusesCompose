package com.mmfsin.noexcusescompose.presentation.notes

import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mmfsin.noexcusescompose.R
import com.mmfsin.noexcusescompose.domain.models.Note
import com.mmfsin.noexcusescompose.domain.models.getExampleNotes
import com.mmfsin.noexcusescompose.presentation.core.components.MediumText
import com.mmfsin.noexcusescompose.presentation.core.components.OutlinedButtonCustomIcon
import com.mmfsin.noexcusescompose.presentation.core.components.SmallText
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerMedium
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerSmall
import com.mmfsin.noexcusescompose.presentation.core.theme.Black
import com.mmfsin.noexcusescompose.presentation.core.theme.GrayMedium
import com.mmfsin.noexcusescompose.presentation.core.theme.YellowLight
import com.mmfsin.noexcusescompose.presentation.core.theme.montserrat_bold
import com.mmfsin.noexcusescompose.util.NAV_NOTE_DETAIL
import com.mmfsin.noexcusescompose.util.openBedRockActivity

@Preview
@Composable
fun NotesPV() {
    NotesContent(
        uiStates = NotesStates(
            notes = getExampleNotes()
        ),
        {}, {}, {}
    )
}

@Composable
fun NotesScreen(viewModel: NotesViewModel = hiltViewModel()) {
    val uiStates by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    NotesContent(
        uiStates = uiStates,
        createNote = { context.openBedRockActivity(NAV_NOTE_DETAIL) },
        openNoteDetail = { noteId ->
            context.openBedRockActivity(NAV_NOTE_DETAIL, noteId)
        },
        updatePushpin = { noteId -> viewModel.updatePushpin(noteId) }
    )
}

@Composable
fun NotesContent(
    uiStates: NotesStates,
    createNote: () -> Unit,
    openNoteDetail: (String) -> Unit,
    updatePushpin: (String) -> Unit
) {
    Column(
        Modifier.fillMaxSize()
            .background(GrayMedium)
            .padding(vertical = 12.dp, horizontal = 16.dp)
    ) {
        if (uiStates.notes.isEmpty()) {
            EmptyNotes(
                createNote = { createNote() }
            )
        } else {
            CompositionLocalProvider(
                LocalOverscrollFactory provides null
            ) {
                LazyColumn(
                    state = rememberLazyListState(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    items(
                        items = uiStates.notes,
                        key = { note -> note.id }
                    ) { note ->
                        NoteBox(
                            note,
                            onClick = { openNoteDetail(note.id) },
                            updatePushpin = { updatePushpin(note.id) },
                        )
                    }

                    item {
                        SpacerSmall()
                        OutlinedButtonCustomIcon(
                            onClick = { createNote() },
                            text = R.string.notes_create,
                            icon = R.drawable.ic_add,
                            modifier = Modifier.fillMaxWidth(),
                            color = Black
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NoteBox(
    note: Note,
    onClick: () -> Unit,
    updatePushpin: () -> Unit
) {
    Column(
        Modifier.fillMaxWidth()
            .background(YellowLight, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = { onClick() })
            .padding(12.dp)
    ) {
        Row(verticalAlignment = Alignment.Top) {
            MediumText(
                text = note.title,
                modifier = Modifier.weight(1f),
                fontFamily = montserrat_bold
            )
            Spacer(Modifier.weight(1f))

            val pushpin = if (note.pinned) R.drawable.ic_pushpin else R.drawable.ic_pushpin_off
            Image(
                painterResource(pushpin), null,
                modifier = Modifier.clickable(onClick = { updatePushpin() })
            )
        }
        SpacerSmall()
        MediumText(text = "${note.description.take(150)}...")
        SpacerSmall()
        SmallText(text = note.date)
    }
}

@Composable
fun EmptyNotes(createNote: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 42.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(1f))
        MediumText(
            text = R.string.notes_empty,
            gravity = TextAlign.Center,
        )
        SpacerMedium()
        OutlinedButtonCustomIcon(
            onClick = { createNote() },
            text = R.string.notes_create,
            icon = R.drawable.ic_notes_empty,
            color = Black
        )
        Spacer(Modifier.weight(1f))
    }
}