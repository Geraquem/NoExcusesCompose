package com.mmfsin.noexcuses.presentation.exercises.detail

import com.mmfsin.noexcuses.domain.models.Exercise

data class ExerciseDetailStates(
    val isLoading: Boolean = true,

    val exerciseId: String = "",
    val exerciseName: String = "",
    val exerciseFav: Boolean = false,
    val exercise: Exercise? = null,
)