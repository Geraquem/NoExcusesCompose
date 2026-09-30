package com.mmfsin.noexcusescompose.domain.models

data class Day(
    var id: String,
    var routineId: String,
    var title: String,
    var exercises: Int,
)

fun getExampleDays() = listOf(
    Day(
        id = "d1",
        routineId = "1",
        title = "Pecho y tríceps",
        exercises = 5
    ),
    Day(
        id = "d2",
        routineId = "2",
        title = "Espalda bíceps",
        exercises = 2
    ),
    Day(
        id = "d3",
        routineId = "3",
        title = "Pierna",
        exercises = 15
    ),
)