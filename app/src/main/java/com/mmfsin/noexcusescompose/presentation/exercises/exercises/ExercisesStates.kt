package com.mmfsin.noexcusescompose.presentation.exercises.exercises

import com.mmfsin.noexcusescompose.domain.models.Exercise

data class ExercisesStates(
    val isLoading: Boolean = true,

    val mGroupId: String = "",
    val exercises: List<Exercise> = emptyList(),
)