package com.mmfsin.noexcusescompose.presentation.myroutines.routines

import com.mmfsin.noexcusescompose.domain.models.Routine

data class MyRoutinesStates(
    val isLoading: Boolean = true,

    val showCreateRoutineDialog: Boolean = false,

    val newRoutineName: String = "",
    val newRoutineDescription: String = "",

    val myRoutines: List<Routine> = emptyList(),

    val sww: Boolean = false,
)