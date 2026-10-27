package com.mmfsin.noexcuses.presentation.myroutines.routines

import com.mmfsin.noexcuses.domain.models.Day
import com.mmfsin.noexcuses.domain.models.Routine

data class MyRoutinesStates(
    val isLoading: Boolean = true,

    val showCreateRoutineDialog: Boolean = false,
    val showDeleteRoutineDialog: Boolean = false,

    val dayToDelete: Day? = null,
    val routineToEdit: Routine? = null,

    val myRoutines: List<Routine> = emptyList(),

    val sww: Boolean = false,
)