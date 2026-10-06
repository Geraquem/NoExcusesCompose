package com.mmfsin.noexcusescompose.data.models

import androidx.room.Embedded
import androidx.room.Relation

data class ExerciseRtnWithSeries(
    @Embedded
    val exerciseRtn: ExerciseRtnDTO,

    @Relation(
        parentColumn = "id",
        entityColumn = "exerciseRtnId"
    )
    val series: List<SerieDTO>,

    @Relation(
        parentColumn = "exerciseId",
        entityColumn = "id"
    )
    val exercise: ExerciseDTO?
)