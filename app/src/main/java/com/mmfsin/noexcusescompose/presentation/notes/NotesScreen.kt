package com.mmfsin.noexcusescompose.presentation.notes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mmfsin.noexcusescompose.R
import com.mmfsin.noexcusescompose.presentation.core.components.MediumText
import com.mmfsin.noexcusescompose.presentation.core.components.OutlinedButtonCustom
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerMedium
import com.mmfsin.noexcusescompose.presentation.core.theme.Black
import com.mmfsin.noexcusescompose.util.NAV_FAVORITES
import com.mmfsin.noexcusescompose.util.openBedRockActivity

@Preview
@Composable
fun NotesPV() {
    NotesContent(
        uiStates = NotesStates(

        ),
        {}
    )
}

@Composable
fun NotesScreen(viewModel: NotesViewModel = hiltViewModel()) {
    val uiStates by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    NotesContent(
        uiStates = uiStates,
        createNote = { context.openBedRockActivity(NAV_FAVORITES) }
    )
}

@Composable
fun NotesContent(
    uiStates: NotesStates,
    createNote: () -> Unit
) {

    if (uiStates.notes.isEmpty()) {
        EmptyNotes(
            createNote = { createNote() }
        )
    }
}

@Composable
fun EmptyNotes(createNote: () -> Unit) {
    Column(
        modifier = Modifier.padding(horizontal = 42.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(1f))
        MediumText(
            text = R.string.notes_empty,
            gravity = TextAlign.Center,
        )
        SpacerMedium()
        OutlinedButtonCustom(
            onClick = { createNote() },
            text = R.string.notes_create,
            color = Black
        )
        Spacer(Modifier.weight(1f))
    }
}