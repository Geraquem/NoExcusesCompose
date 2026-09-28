package com.mmfsin.noexcusescompose.presentation.myroutines.routines

import com.mmfsin.noexcusescompose.domain.models.Routine

data class MyRoutinesStates(
    val showCreateRoutineDialog: Boolean = false,

    val myRoutines: List<Routine> = emptyList(),

    val sww: Boolean = false,
)