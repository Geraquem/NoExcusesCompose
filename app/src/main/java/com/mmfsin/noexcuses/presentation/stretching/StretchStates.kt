package com.mmfsin.noexcuses.presentation.stretching

import com.mmfsin.noexcuses.domain.models.Stretch

data class StretchStates(
    val isLoading: Boolean = true,

    val stretchingMuscularGroups: List<String> = emptyList(),
    val stretchingExercises: List<Stretch> = emptyList(),
)