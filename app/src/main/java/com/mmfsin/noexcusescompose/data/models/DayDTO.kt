package com.mmfsin.noexcusescompose.data.models

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.mmfsin.noexcusescompose.util.TABLE_DAYS

@Keep
@Entity(
    tableName = TABLE_DAYS,
    foreignKeys = [
        ForeignKey(
            entity = MyRoutineDTO::class,
            parentColumns = ["id"],
            childColumns = ["routineId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("routineId")]
)
data class DayDTO(
    @PrimaryKey
    var id: String = "",
    var routineId: String = "",
    var name: String = "",
    val order: Int = 0,
)
