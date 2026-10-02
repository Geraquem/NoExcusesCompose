package com.mmfsin.noexcusescompose.presentation.myroutines.exercises

import com.mmfsin.noexcusescompose.domain.models.Exercise

data class ExercisesRtnStates(
    val isLoading: Boolean = true,

    val exerciseIdClick: String? = null,
    val exerciseClicked: Exercise? = null,

    val dayId: String = "",
    val dayName: String = "",

    val mGroupId: String = "",
    val exercises: List<Exercise> = emptyList(),

    val sww: Boolean = false
)