package com.mmfsin.noexcusescompose.data.models

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.mmfsin.noexcusescompose.util.TABLE_EXERCISES_RTN
import com.mmfsin.noexcusescompose.util.TABLE_SERIES

@Keep
@Entity(
    tableName = TABLE_EXERCISES_RTN,
    foreignKeys = [
        ForeignKey(
            entity = DayDTO::class,
            parentColumns = ["id"],
            childColumns = ["dayId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("dayId")]
)
data class ExerciseRtnDTO(
    @PrimaryKey
    val id: String = "",
    val dayId: String = "",
    val exerciseId: String = "",
    val rest: String? = null,
    val notes: String? = null,
    val superSerie: Boolean = false,
    val order: Int = 0
)

@Keep
@Entity(
    tableName = TABLE_SERIES,
    foreignKeys = [
        ForeignKey(
            entity = ExerciseRtnDTO::class,
            parentColumns = ["id"],
            childColumns = ["exerciseRtnId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("exerciseRtnId")]
)
data class SerieDTO(
    @PrimaryKey
    val id: String = "",
    val exerciseRtnId: String = "",
    val reps: Int? = null,
    val kgs: String? = null,
    val order: Int = 0,
)
