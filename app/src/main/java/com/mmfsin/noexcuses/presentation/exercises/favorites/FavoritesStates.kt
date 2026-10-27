package com.mmfsin.noexcuses.presentation.exercises.favorites

import com.mmfsin.noexcuses.domain.models.Exercise

data class FavoritesStates(
    val isLoading: Boolean = true,

    val favorites: List<Exercise> = emptyList(),
)