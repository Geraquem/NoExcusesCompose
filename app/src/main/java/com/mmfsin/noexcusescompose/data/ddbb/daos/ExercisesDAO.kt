package com.mmfsin.noexcusescompose.data.ddbb.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mmfsin.noexcusescompose.data.models.ExerciseDTO

@Dao
interface ExercisesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercises(exercises: List<ExerciseDTO>)

    @Query("SELECT * FROM table_exercises")
    suspend fun getAllExercises(): List<ExerciseDTO>

    @Query("SELECT * FROM table_exercises WHERE category == :mgroupId")
    suspend fun getExerciseByMuscularGroup(mgroupId: String): List<ExerciseDTO>

    @Query("SELECT * FROM table_exercises WHERE id == :id")
    suspend fun getExerciseById(id: String): ExerciseDTO?
}