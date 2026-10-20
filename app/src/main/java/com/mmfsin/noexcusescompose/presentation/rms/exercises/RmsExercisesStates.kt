package com.mmfsin.noexcusescompose.presentation.rms.exercises

import com.mmfsin.noexcusescompose.domain.models.Exercise

data class RmsExercisesStates(
    val isLoading: Boolean = true,

    val exerciseIdClick: String? = null,
    val exerciseClicked: Exercise? = null,

    val mGroupId: String = "",
    val exercises: List<Exercise> = emptyList(),

    val sww: Boolean = false
)