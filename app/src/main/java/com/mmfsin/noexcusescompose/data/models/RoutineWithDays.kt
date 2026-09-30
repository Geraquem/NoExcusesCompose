package com.mmfsin.noexcusescompose.data.models

import androidx.room.Embedded
import androidx.room.Relation

data class RoutineWithDays(
    @Embedded
    val routine: MyRoutineDTO,

    @Relation(
        parentColumn = "id",
        entityColumn = "routineId"
    )
    val days: List<DayDTO>
)