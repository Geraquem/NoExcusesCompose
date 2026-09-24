package com.mmfsin.noexcusescompose.presentation.stretching

import com.mmfsin.noexcusescompose.domain.models.Stretch

data class StretchStates(
    val isLoading: Boolean = true,

    val stretchingMuscularGroups: List<String> = emptyList(),
    val stretchingExercises: List<Stretch> = emptyList(),
)