package com.mmfsin.noexcuses.presentation.exercises.exercises

import com.mmfsin.noexcuses.domain.models.Exercise

data class ExercisesStates(
    val isLoading: Boolean = true,

    val mGroupId: String = "",
    val exercises: List<Exercise> = emptyList(),

    val sww: Boolean = false
)