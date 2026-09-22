package com.mmfsin.noexcusescompose.data.models

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mmfsin.noexcusescompose.util.TABLE_MUSCULAR_GROUPS

@Keep
@Entity(tableName = TABLE_MUSCULAR_GROUPS)
data class MuscularGroupDTO(
    @PrimaryKey
    var id: String = "",
    var name: String = "",
    var manImageURL: String = "",
    var womanImageURL: String = "",
    var order: Long = 0,
)
