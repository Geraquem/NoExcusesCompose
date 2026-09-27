package com.mmfsin.noexcusescompose.presentation.notes.create

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mmfsin.noexcusescompose.R
import com.mmfsin.noexcusescompose.presentation.core.components.CustomTextField
import com.mmfsin.noexcusescompose.presentation.core.components.CustomToolbar
import com.mmfsin.noexcusescompose.presentation.core.components.ErrorDialog
import com.mmfsin.noexcusescompose.presentation.core.components.SpacerMedium
import com.mmfsin.noexcusescompose.presentation.core.theme.GrayMedium

@Preview
@Composable
fun NoteDetailPV() {
    NoteDetailContent(
        uiStates = NoteDetailStates(

        ),
        {}, {}, {}, {},
    )
}

@Composable
fun NoteDetailScreen(
    viewModel: NoteDetailViewModel = hiltViewModel(),
    goBack: () -> Unit
) {
    val uiStates by viewModel.uiState.collectAsStateWithLifecycle()

    NoteDetailContent(
        uiStates = uiStates,
        goBack = { goBack() },
        updateTitle = { viewModel.updateTitle(it) },
        updateText = { viewModel.updateText(it) },
        saveNote = { viewModel.saveNote() }
    )

    if (uiStates.shouldGoBack) goBack()
}

@Composable
fun NoteDetailContent(
    uiStates: NoteDetailStates,
    goBack: () -> Unit,
    updateTitle: (String) -> Unit,
    updateText: (String) -> Unit,
    saveNote: () -> Unit,
) {
    Scaffold(
        topBar = {
            CustomToolbar(
                goBack = { goBack() },
                showIconRight = true,
                iconRight = R.drawable.ic_check,
                iconRightClick = { saveNote() }
            )
        }
    ) { innerPadding ->
        Column(
            Modifier.fillMaxSize()
                .background(GrayMedium)
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            CustomTextField(
                value = uiStates.noteTitle,
                onValueChange = { updateTitle(it) }
            )

            SpacerMedium()

            CustomTextField(
                value = uiStates.noteText,
                onValueChange = { updateText(it) },
                singleLine = false,
                imeAction = ImeAction.None,
                maxLength = 1000,
                maxLines = 20,
                modifier = Modifier.fillMaxHeight()
            )
        }

        if (uiStates.sww) ErrorDialog(accept = { goBack() })

        BackHandler {
            saveNote()
        }
    }
}