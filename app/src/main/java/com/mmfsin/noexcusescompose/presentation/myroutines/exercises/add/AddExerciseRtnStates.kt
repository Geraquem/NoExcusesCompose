package com.mmfsin.noexcusescompose.presentation.myroutines.exercises.add

import com.mmfsin.noexcusescompose.domain.models.Serie

data class AddExerciseRtnStates(
    val dayId: String? = null,
    val exerciseId: String? = null,

    val series: List<Serie> = emptyList(),
    val rest: String? = null,
    val notes: String? = null,
    val superSerie: Boolean = false,

    val goBack: Boolean = false,
    val sww: Boolean = false,
)