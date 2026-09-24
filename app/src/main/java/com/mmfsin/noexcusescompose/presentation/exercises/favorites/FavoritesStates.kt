package com.mmfsin.noexcusescompose.presentation.exercises.favorites

import com.mmfsin.noexcusescompose.domain.models.Exercise
import com.mmfsin.noexcusescompose.domain.models.MuscularGroup
import com.mmfsin.noexcusescompose.domain.models.Note
import com.mmfsin.noexcusescompose.domain.models.Routine

data class FavoritesStates(
    val isLoading: Boolean = true,

    val favorites: List<Exercise> = emptyList(),
)