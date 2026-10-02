package com.mmfsin.noexcusescompose.domain.models

import java.util.UUID

data class ExerciseRtn(
    val id: String,
    val dayId: String,
    val exerciseId: String,
    val series: List<Serie>,
    val rest: String?,
    val notes: String?,
    val superSerie: Boolean,
    val order: Int,
)

data class Serie(
    val id: String,
    val reps: Int?,
    val kgs: String?,
    val order: Int,
)

fun createSerie(order: Int) = Serie(
    id = UUID.randomUUID().toString(),
    reps = null,
    kgs = null,
    order = order
)
