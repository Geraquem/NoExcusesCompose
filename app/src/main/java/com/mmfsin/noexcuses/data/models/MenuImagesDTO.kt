package com.mmfsin.noexcuses.data.models

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.database.PropertyName
import com.mmfsin.noexcuses.util.TABLE_EXERCISES
import com.mmfsin.noexcuses.util.TABLE_IMAGES

@Keep
@Entity(tableName = TABLE_IMAGES)
data class MenuImagesDTO(
    @PrimaryKey
    var id: String = "",

    @get:PropertyName("image_my_routines")
    @set:PropertyName("image_my_routines")
    var myRoutinesImageUrl: String? = null,

    @get:PropertyName("image_df_routines")
    @set:PropertyName("image_df_routines")
    var dfRoutinesImageUrl: String? = null,

    @get:PropertyName("image_exercises")
    @set:PropertyName("image_exercises")
    var exercisesImageUrl: String? = null,

    @get:PropertyName("image_favorites")
    @set:PropertyName("image_favorites")
    var favoritesImageUrl: String? = null,
)
