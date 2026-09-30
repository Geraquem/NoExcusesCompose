package com.mmfsin.noexcusescompose.presentation.myroutines.days

import com.mmfsin.noexcusescompose.domain.models.Exercise

data class DayStates(
    val isLoading: Boolean = true,

    val routineId: String? = null,
    val routineName: String = "",

    val dayId: String = "",
    val dayName: String = "",
    val dayOrder: Int = -1,

    val exercises: List<Exercise> = emptyList(),

    val emptyNameError: Boolean = false,
    val shouldGoBack: Boolean = false,
    val sww: Boolean = false,
)