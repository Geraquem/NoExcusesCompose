package com.mmfsin.noexcusescompose.domain.models

data class Rm(
    val exercise: Exercise,
    val data: List<RmData>
)

data class RmData(
    val id: String,
    val kgs: Double,
    var date: String?,
)

fun getRmsExamples() = listOf(
    Rm(
        exercise = getExercisesExamples().first(),
        data = listOf(
            RmData(
                id = "1",
                kgs = 180.0,
                date = "12/Nov/2025"
            ),
            RmData(
                id = "2",
                kgs = 30.65,
                date = "18/Sep/2025"
            ),
            RmData(
                id = "3",
                kgs = 5.0,
                date = "15/Abr/2025"
            )
        )
    )
)