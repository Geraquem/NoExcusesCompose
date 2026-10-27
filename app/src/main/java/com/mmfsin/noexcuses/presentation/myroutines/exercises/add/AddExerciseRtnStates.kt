package com.mmfsin.noexcuses.presentation.myroutines.exercises.add

import com.mmfsin.noexcuses.domain.models.Serie

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