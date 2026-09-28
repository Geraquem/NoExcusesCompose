package com.mmfsin.noexcusescompose.data.ddbb.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mmfsin.noexcusescompose.data.models.MyRoutineDTO
import kotlinx.coroutines.flow.Flow

@Dao
interface RoutinesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMyRoutine(routine: MyRoutineDTO)

    @Query("SELECT * FROM table_routines WHERE createdByUser = 1")
    fun getMyRoutines(): Flow<List<MyRoutineDTO>>
}