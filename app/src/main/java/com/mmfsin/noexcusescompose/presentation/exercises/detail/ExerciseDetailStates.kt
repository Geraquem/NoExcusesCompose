package com.mmfsin.noexcusescompose.presentation.exercises.detail

import com.mmfsin.noexcusescompose.domain.models.Exercise

data class ExerciseDetailStates(
    val isLoading: Boolean = true,

    val exerciseId: String = "",
    val exerciseName: String = "",
    val exerciseFav: Boolean = false,
    val exercise: Exercise? = null,
)