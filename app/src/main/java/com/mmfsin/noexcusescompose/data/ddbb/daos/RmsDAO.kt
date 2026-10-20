package com.mmfsin.noexcusescompose.data.ddbb.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mmfsin.noexcusescompose.data.models.RmDTO
import com.mmfsin.noexcusescompose.data.models.RmWithExercise
import kotlinx.coroutines.flow.Flow

@Dao
interface RmsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRM(data: RmDTO)

    @Query("SELECT * FROM table_rms")
    fun getRms(): Flow<List<RmWithExercise>>
}