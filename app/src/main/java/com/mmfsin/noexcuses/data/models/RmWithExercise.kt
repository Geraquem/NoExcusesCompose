package com.mmfsin.noexcuses.data.models

import androidx.room.Embedded
import androidx.room.Relation

data class RmWithExercise(
    @Embedded
    val rmDTO: RmDTO,

    @Relation(
        parentColumn = "exerciseId",
        entityColumn = "id"
    )
    val exercise: ExerciseDTO?
)