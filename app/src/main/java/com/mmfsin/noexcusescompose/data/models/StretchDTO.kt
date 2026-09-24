package com.mmfsin.noexcusescompose.data.models

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mmfsin.noexcusescompose.util.TABLE_STRETCH

@Keep
@Entity(tableName = TABLE_STRETCH)
data class StretchDTO(
    @PrimaryKey
    var id: String = "",
    var order: Long = 0,
    var category: String = "",
    var imageURL: String = "",
    var description: String = "",
)
