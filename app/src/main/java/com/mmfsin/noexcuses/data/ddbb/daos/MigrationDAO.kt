package com.mmfsin.noexcuses.data.ddbb.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import com.mmfsin.noexcuses.data.models.DayDTO
import com.mmfsin.noexcuses.data.models.ExerciseDTO
import com.mmfsin.noexcuses.data.models.ExerciseRtnDTO
import com.mmfsin.noexcuses.data.models.MyRoutineDTO
import com.mmfsin.noexcuses.data.models.NoteDTO
import com.mmfsin.noexcuses.data.models.SerieDTO

@Dao
interface MigrationDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNotes(notes: List<NoteDTO>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercises(exercises: List<ExerciseDTO>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRoutines(routines: List<MyRoutineDTO>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDays(days: List<DayDTO>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercisesRtn(exercises: List<ExerciseRtnDTO>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeries(series: List<SerieDTO>)

    @Transaction
    suspend fun migrate(
        notes: List<NoteDTO>,
        exercises: List<ExerciseDTO>,
        routines: List<MyRoutineDTO>,
        days: List<DayDTO>,
        exercisesRtn: List<ExerciseRtnDTO>,
        series: List<SerieDTO>
    ) {
        insertNotes(notes)
        insertExercises(exercises)
        insertRoutines(routines)
        insertDays(days)
        insertExercisesRtn(exercisesRtn)
        insertSeries(series)
    }
}