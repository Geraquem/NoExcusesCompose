package com.mmfsin.noexcusescompose.domain.models

import java.util.UUID

data class ExerciseRtn(
    val id: String,
    val dayId: String,
    val exerciseId: String,
    val exercise: Exercise?,
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

fun getExerciseRtnExamples() = listOf(
    ExerciseRtn(
        id = "1",
        dayId = "",
        exerciseId = "",
        exercise = getExercisesExamples().first(),
        series = listOf(createSerie(0), createSerie(1)),
        rest = "2",
        notes = "Tener cuidado con las abejas",
        superSerie = true,
        order = 0
    ),
    ExerciseRtn(
        id = "2",
        dayId = "",
        exerciseId = "",
        exercise = getExercisesExamples().last(),
        series = emptyList(),
        rest = null,
        notes = null,
        superSerie = false,
        order = 0
    ),
    ExerciseRtn(
        id = "3",
        dayId = "",
        exerciseId = "",
        exercise = getExercisesExamples().last(),
        series = listOf(createSerie(0)),
        rest = null,
        notes = null,
        superSerie = false,
        order = 0
    ),
)