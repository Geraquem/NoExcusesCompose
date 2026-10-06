package com.mmfsin.noexcusescompose.presentation.myroutines.days.edit

import com.mmfsin.noexcusescompose.domain.models.Exercise
import com.mmfsin.noexcusescompose.domain.models.ExerciseRtn
import com.mmfsin.noexcusescompose.domain.models.Serie

data class EditExerciseRtnStates(
    val dayId: String? = null,

    val exercise: Exercise? = null,
    val series: List<Serie> = emptyList(),
    val rest: String? = null,
    val notes: String? = null,
    val superSerie: Boolean = false,

    val goBack: Boolean = false,
    val sww: Boolean = false,
)