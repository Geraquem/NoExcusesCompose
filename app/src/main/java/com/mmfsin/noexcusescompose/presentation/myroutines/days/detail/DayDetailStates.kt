package com.mmfsin.noexcusescompose.presentation.myroutines.days.detail

import com.mmfsin.noexcusescompose.domain.models.Exercise

data class DayDetailStates(
    val isLoading: Boolean = true,

    val routineId: String? = null,
    val routineName: String = "",

    val dayId: String? = null,
    val dayName: String = "",

    val exercises: List<Exercise> = emptyList(),

    val emptyNameError: Boolean = false,
    val shouldGoBack: Boolean = false,
    val sww: Boolean = false,
)