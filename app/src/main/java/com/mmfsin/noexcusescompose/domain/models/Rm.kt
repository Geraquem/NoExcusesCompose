package com.mmfsin.noexcusescompose.domain.models

data class Rm(
    val exercise: Exercise,
    val data: List<RmData>
)

data class RmData(
    val id: String,
    val kgs: Double,
    var millis: Long,
    var date: String?,
)

fun getRmsExamples() = listOf(
    Rm(
        exercise = getExercisesExamples().first(),
        data = listOf(
            RmData(
                id = "1",
                kgs = 100.0,
                millis = 0,
                date = "12/Nov/2025"
            ),
            RmData(
                id = "2",
                kgs = 130.3,
                millis = 1,
                date = "18/Sep/2025"
            ),
            RmData(
                id = "3",
                kgs = 150.0,
                millis = 2,
                date = "15/Abr/2025"
            )
        )
    )
)