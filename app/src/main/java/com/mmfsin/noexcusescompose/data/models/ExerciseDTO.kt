package com.mmfsin.noexcusescompose.data.models

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mmfsin.noexcusescompose.util.TABLE_EXERCISES

@Keep
@Entity(tableName = TABLE_EXERCISES)
data class ExerciseDTO(
    @PrimaryKey
    var id: String = "",
    var category: String = "",
    var imageURL: String = "",
    var gifURL: String? = null,
    var name: String = "",
    var order: Long = 0,
    var description: String = "",
    var muscles: String = "",
    var isFav: Boolean = false,
    var muscleWikiURL: String? = null,
    var createdByUser: Boolean = false,
)
