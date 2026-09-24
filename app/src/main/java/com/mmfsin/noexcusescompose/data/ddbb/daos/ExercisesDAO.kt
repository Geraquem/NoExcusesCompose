package com.mmfsin.noexcusescompose.data.ddbb.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mmfsin.noexcusescompose.data.models.ExerciseDTO
import com.mmfsin.noexcusescompose.domain.models.Exercise
import kotlinx.coroutines.flow.Flow

@Dao
interface ExercisesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExercises(exercises: List<ExerciseDTO>)

    @Query("SELECT * FROM table_exercises")
    suspend fun getAllExercises(): List<ExerciseDTO>

    @Query("SELECT * FROM table_exercises WHERE category == :mgroupId")
    fun getExerciseByMuscularGroup(mgroupId: String): Flow<List<ExerciseDTO>>

    @Query("SELECT * FROM table_exercises WHERE id == :id")
    fun getExerciseById(id: String): Flow<ExerciseDTO?>

    @Query("UPDATE table_exercises SET isFav = :isFav WHERE id = :id")
    fun updateFavExercise(id: String, isFav: Boolean)

    @Query("SELECT * FROM table_exercises WHERE isFav = 1")
    fun getFavExercises(): Flow<List<ExerciseDTO>>
}