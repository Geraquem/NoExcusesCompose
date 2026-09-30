package com.mmfsin.noexcusescompose.data.models

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mmfsin.noexcusescompose.util.TABLE_DAYS

@Keep
@Entity(tableName = TABLE_DAYS)
data class DayDTO(
    @PrimaryKey
    var id: String = "",
    var routineId: String = "",
    var title: String = "",
    var exercises: Int = 0,
)
