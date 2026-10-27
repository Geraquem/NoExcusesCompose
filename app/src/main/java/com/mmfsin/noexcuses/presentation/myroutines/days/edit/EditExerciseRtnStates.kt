package com.mmfsin.noexcuses.presentation.myroutines.days.edit

import com.mmfsin.noexcuses.domain.models.ExerciseRtn
import com.mmfsin.noexcuses.domain.models.Serie

data class EditExerciseRtnStates(
    val dayId: String? = null,

    val exerciseRtn: ExerciseRtn? = null,
    val series: List<Serie> = emptyList(),
    val rest: String? = null,
    val notes: String? = null,
    val superSerie: Boolean = false,

    val goBack: Boolean = false,
    val sww: Boolean = false,
)