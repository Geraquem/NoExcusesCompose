package com.mmfsin.noexcusescompose.domain.models

data class Routine(
    var id: String,
    var name: String,
    var description: String?,
    var daysCount: Int,
    var doingIt: Boolean,
    val createdByUser: Boolean,
    val pinnedDate: Long?
)

fun getExampleRoutines() = listOf(
    Routine(
        id = "1",
        name = "Rutina 1",
        description = "Descripción rutina 1",
        daysCount = 3,
        doingIt = false,
        createdByUser = true,
        pinnedDate = 0
    ),
    Routine(
        id = "2",
        name = "Rutina 2",
        description = null,
        daysCount = 5,
        doingIt = true,
        createdByUser = true,
        pinnedDate = 0
    ),
    Routine(
        id = "3",
        name = "Rutina 3",
        description = "Descripción rutina 3",
        daysCount = 1,
        doingIt = false,
        createdByUser = false,
        pinnedDate = 0
    ),
)