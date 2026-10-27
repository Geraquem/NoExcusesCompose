package com.mmfsin.noexcuses.data.ddbb.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mmfsin.noexcuses.data.models.StretchDTO

@Dao
interface StretchDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStretchData(data: List<StretchDTO>)

    @Query("SELECT * FROM table_stretch")
    suspend fun getStretchingData(): List<StretchDTO>
}