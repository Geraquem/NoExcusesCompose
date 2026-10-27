package com.mmfsin.noexcuses.presentation.dfroutines.days

import com.mmfsin.noexcuses.domain.models.ExerciseRtn

data class DfDayStates(
    val isLoading: Boolean = true,

    val routineId: String? = null,
    val routineName: String = "",

    val dayId: String? = null,
    val dayName: String = "",
    val dayOrder: Int = -1,

    val exercises: List<ExerciseRtn> = emptyList(),

    val sww: Boolean = false,
)