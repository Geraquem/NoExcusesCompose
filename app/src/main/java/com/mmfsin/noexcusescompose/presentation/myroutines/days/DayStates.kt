package com.mmfsin.noexcusescompose.presentation.myroutines.days

import com.mmfsin.noexcusescompose.domain.models.ExerciseRtn

data class DayStates(
    val isLoading: Boolean = true,

    val showDeleteExerciseRtnDialog: Boolean = false,

    val exerciseRtnToEdit: ExerciseRtn? = null,

    val routineId: String? = null,
    val routineName: String = "",

    val dayId: String? = null,
    val dayName: String = "",
    val dayOrder: Int = -1,

    val exercises: List<ExerciseRtn> = emptyList(),

    val shouldGoToMuscularGroups: Boolean = false,
    val shouldGoBack: Boolean = false,
    val sww: Boolean = false,
)