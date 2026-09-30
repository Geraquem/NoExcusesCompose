package com.mmfsin.noexcusescompose.data.ddbb.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mmfsin.noexcusescompose.data.models.DayDTO
import com.mmfsin.noexcusescompose.data.models.MyRoutineDTO
import com.mmfsin.noexcusescompose.data.models.RoutineWithDays
import kotlinx.coroutines.flow.Flow

@Dao
interface RoutinesDAO {

    /******************* ROUTINES *******************/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMyRoutine(routine: MyRoutineDTO)

    @Query("SELECT * FROM table_routines WHERE createdByUser = 1")
    fun getMyRoutines(): Flow<List<RoutineWithDays>>

    /********************* DAYS *********************/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDay(day: DayDTO)

    @Query("SELECT * FROM table_days WHERE routineId = :routineId")
    fun getDaysFromRoutine(routineId: String): Flow<List<DayDTO>>

    @Query("SELECT * FROM table_days WHERE id = :dayId")
    fun getDayById(dayId: String):DayDTO?
}