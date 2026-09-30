package com.mmfsin.noexcusescompose.domain.models

data class Routine(
    var id: String,
    var name: String,
    var description: String?,
    var days: List<Day>,
    var doingIt: Boolean,
    val createdByUser: Boolean,
    val pinnedDate: Long?,
    val order: Int,
)

fun getExampleRoutines() = listOf(
    Routine(
        id = "1",
        name = "Rutina 1",
        description = "Descripción rutina 1",
        days = getExampleDays(),
        doingIt = false,
        createdByUser = true,
        pinnedDate = 0,
        order = 0,
    ),
    Routine(
        id = "2",
        name = "Rutina 2 ww  ews´.fklñdk fs´fd´s",
        description = null,
        days = getExampleDays(),
        doingIt = true,
        createdByUser = true,
        pinnedDate = 0,
        order = 1,
    ),
    Routine(
        id = "3",
        name = "Rutina 3",
        description = "Descripción rutina 3",
        days = getExampleDays(),
        doingIt = false,
        createdByUser = false,
        pinnedDate = 0,
        order = 2,
    ),
)