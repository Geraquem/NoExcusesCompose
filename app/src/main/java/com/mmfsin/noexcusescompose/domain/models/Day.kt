package com.mmfsin.noexcusescompose.domain.models

data class Day(
    var id: String,
    var routineId: String,
    var name: String,
)

fun getExampleDays() = listOf(
    Day(
        id = "d1",
        routineId = "1",
        name = "Pecho y tríceps",
    ),
    Day(
        id = "d2",
        routineId = "2",
        name = "Espalda bíceps",
    ),
    Day(
        id = "d3",
        routineId = "3",
        name = "Pierna",
    ),
)