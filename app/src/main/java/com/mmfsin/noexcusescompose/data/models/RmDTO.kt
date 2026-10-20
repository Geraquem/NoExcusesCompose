package com.mmfsin.noexcusescompose.data.models

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mmfsin.noexcusescompose.util.TABLE_RMS

@Keep
@Entity(tableName = TABLE_RMS)
data class RmDTO(
    @PrimaryKey
    var id: String = "",
    var exerciseId: String = "",
    val kgs: Double = 0.0,
    var date: Long = 0,
)
