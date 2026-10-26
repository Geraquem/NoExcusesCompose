package com.mmfsin.noexcusescompose.presentation.exercises.create

import com.mmfsin.noexcusescompose.domain.models.Exercise

data class CreateExerciseStates(
    val isLoading: Boolean = true,

    val imageUrl: String = "",
    val name: String = "",
    val mGroupId: String = "",
    val description: String = "",
    val involvedMuscles: String = "",
    val isFav: Boolean = false,

    val shouldGoBack: Boolean = false,
    val emptyNameError: Boolean = false,
    val sww: Boolean = false
)