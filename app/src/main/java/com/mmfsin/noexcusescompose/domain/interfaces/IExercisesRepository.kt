package com.mmfsin.noexcusescompose.domain.interfaces

import com.mmfsin.noexcusescompose.domain.models.Exercise
import kotlinx.coroutines.flow.Flow

interface IExercisesRepository {
    suspend fun getAllExercises()

    suspend fun getExercisesByMuscularGroup(mGroupId: String): Flow<List<Exercise>>
    fun getExerciseById(id: String): Flow<Exercise?>

    fun updateFavExercise(id: String, isFav: Boolean)
    fun getFavExercises(): Flow<List<Exercise>>

    suspend fun createExercise(exercise: Exercise)
    suspend fun deleteExercise(exerciseId: String)
}