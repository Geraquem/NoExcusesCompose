package com.mmfsin.noexcusescompose.domain.models

data class Day(
    var id: String,
    var routineId: String,
    var name: String,
    val order: Int,
)

fun getExampleDays() = listOf(
    Day(
        id = "d1",
        routineId = "1",
        name = "Pecho y tríceps",
        order = 0,
    ),
    Day(
        id = "d2",
        routineId = "2",
        name = "Espalda bíceps",
        order = 1,
    ),
    Day(
        id = "d3",
        routineId = "3",
        name = "Pierna",
        order = 2,
    ),
)