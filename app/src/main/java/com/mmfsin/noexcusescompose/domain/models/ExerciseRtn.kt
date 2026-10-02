package com.mmfsin.noexcusescompose.domain.models

import java.util.UUID

data class ExerciseRtn(
    val id: String,
    val dayId: String,
    val exerciseId: String,
    val series: List<Serie>,
    val rest: Double,
    val notes: String,
    val superSerie: Boolean,
    val order: Int,
)

data class Serie(
    val id: String,
    val reps: Int,
    val kgs: Double,
)

fun createSerie() = Serie(
    id = UUID.randomUUID().toString(),
    reps = 0,
    kgs = 00.00
)
