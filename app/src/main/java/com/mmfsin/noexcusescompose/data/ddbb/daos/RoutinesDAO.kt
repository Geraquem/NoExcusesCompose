package com.mmfsin.noexcusescompose.data.ddbb.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.mmfsin.noexcusescompose.data.models.DayDTO
import com.mmfsin.noexcusescompose.data.models.ExerciseRtnDTO
import com.mmfsin.noexcusescompose.data.models.ExerciseRtnWithSeries
import com.mmfsin.noexcusescompose.data.models.MyRoutineDTO
import com.mmfsin.noexcusescompose.data.models.RoutineWithDays
import com.mmfsin.noexcusescompose.data.models.SerieDTO
import kotlinx.coroutines.flow.Flow

@Dao
interface RoutinesDAO {

    /******************* ROUTINES *******************/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMyRoutine(routine: MyRoutineDTO)

    @Query("SELECT * FROM table_routines WHERE createdByUser = 1")
    fun getMyRoutines(): Flow<List<RoutineWithDays>>

    @Query("SELECT * FROM table_routines WHERE id = :routineId")
    fun getMyRoutineById(routineId: String): MyRoutineDTO?

    @Query("SELECT COALESCE(MAX(`order`), -1) + 1 FROM table_routines")
    suspend fun getNextRoutineOrder(): Int

    @Query(
        """
    UPDATE table_routines
    SET pinned = CASE
        WHEN id = :routineId THEN NOT pinned
        ELSE 0
    END
    """
    )
    fun updatePinnedRoutine(routineId: String)

    /********************* DAYS *********************/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDay(day: DayDTO)

    @Query("SELECT * FROM table_days WHERE routineId = :routineId")
    fun getDaysFromRoutine(routineId: String): Flow<List<DayDTO>>

    @Query("SELECT * FROM table_days WHERE id = :dayId")
    fun getDayById(dayId: String): DayDTO?

    @Query("SELECT COALESCE(MAX(`order`), -1) + 1 FROM table_days")
    suspend fun getNextDayOrder(): Int


    /****************** EXERCISES ******************/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExerciseRtn(exercise: ExerciseRtnDTO)

    @Query(
        """
    SELECT COALESCE(MAX(`order`), -1) + 1
    FROM table_exercises_rtn
    WHERE dayId = :dayId
    """
    )
    suspend fun getNextExerciseRtnOrder(dayId: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeries(series: List<SerieDTO>)

    @Transaction
    suspend fun insertExerciseWithSeries(
        exercise: ExerciseRtnDTO,
        series: List<SerieDTO>
    ) {
        insertExerciseRtn(exercise)
        insertSeries(series)
    }

    @Transaction
    @Query(
        """
    SELECT *
    FROM table_exercises_rtn
    WHERE dayId = :dayId
    ORDER BY `order` ASC
    """
    )
    fun getExercisesRtnWithSeriesByDayId(dayId: String): Flow<List<ExerciseRtnWithSeries>>

    @Transaction
    @Query(
        """
    SELECT *
    FROM table_exercises_rtn
    WHERE id = :exerciseRtnId
    """
    )
    fun getExerciseRtnWithSeries(exerciseRtnId: String): Flow<ExerciseRtnWithSeries?>
}