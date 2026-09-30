package com.mmfsin.noexcusescompose.data.models

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mmfsin.noexcusescompose.util.TABLE_ROUTINES

@Keep
@Entity(tableName = TABLE_ROUTINES)
data class MyRoutineDTO(
    @PrimaryKey
    var id: String = "",
    var name: String = "",
    var description: String? = null,
    var doingIt: Boolean = false,
    var createdByUser: Boolean = true,
    var pinnedDate: Long? = null,
    var order: Int = 0,
)
