package com.mmfsin.noexcuses.presentation.menu

import com.mmfsin.noexcuses.domain.models.MuscularGroup
import com.mmfsin.noexcuses.domain.models.Note
import com.mmfsin.noexcuses.domain.models.Routine

data class MenuStates(
    val isLoading: Boolean = true,

    val showUnpinRoutineDialog: Boolean = false,
    val showUnpinNoteDialog: Boolean = false,

    val muscularGroups: List<MuscularGroup> = emptyList(),

    val actualRoutine: Routine? = null,
    val actualNote: Note? = null,

    val sww: Boolean = false,
)