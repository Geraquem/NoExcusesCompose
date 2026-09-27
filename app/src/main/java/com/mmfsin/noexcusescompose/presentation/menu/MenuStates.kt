package com.mmfsin.noexcusescompose.presentation.menu

import com.mmfsin.noexcusescompose.domain.models.MuscularGroup
import com.mmfsin.noexcusescompose.domain.models.Note
import com.mmfsin.noexcusescompose.domain.models.Routine

data class MenuStates(
    val isLoading: Boolean = true,

    val showUnpinNoteDialog: Boolean = false,

    val muscularGroups: List<MuscularGroup> = emptyList(),

    val actualRoutine: Routine? = null,
    val actualNote: Note? = null,

    val sww: Boolean = false,
)