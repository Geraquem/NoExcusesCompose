package com.mmfsin.noexcuses.data.ddbb.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mmfsin.noexcuses.data.models.MenuImagesDTO

@Dao
interface MenuDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMenuImages(images: MenuImagesDTO)

    @Query("SELECT * FROM table_images")
    suspend fun getMenuImages(): MenuImagesDTO?
}