package com.mmfsin.noexcusescompose.data.models

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mmfsin.noexcusescompose.util.TABLE_NOTES
import com.mmfsin.noexcusescompose.util.TABLE_STRETCH

@Keep
@Entity(tableName = TABLE_NOTES)
data class NoteDTO(
    @PrimaryKey
    var id: String = "",
    var title: String = "",
    var description: String = "",
    var date: Long = 0,
    var pinned: Boolean = false
)
