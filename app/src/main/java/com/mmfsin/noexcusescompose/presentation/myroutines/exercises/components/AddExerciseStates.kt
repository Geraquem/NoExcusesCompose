package com.mmfsin.noexcusescompose.presentation.myroutines.exercises.components

import com.mmfsin.noexcusescompose.domain.models.Serie

data class AddExerciseStates(
    val series: List<Serie> = emptyList(),
    val rest: String? = null,
    val notes: String? = null,
    val superSerie: Boolean = false,
)