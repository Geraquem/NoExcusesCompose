package com.mmfsin.noexcusescompose.presentation.myroutines.routines

import com.mmfsin.noexcusescompose.domain.models.Routine

data class MyRoutinesStates(
    val isLoading: Boolean = true,

    val showCreateRoutineDialog: Boolean = false,

    val routineIdClicked: String? = null,
    val routineClicked: String = "",
    val myRoutines: List<Routine> = emptyList(),

    val sww: Boolean = false,
)