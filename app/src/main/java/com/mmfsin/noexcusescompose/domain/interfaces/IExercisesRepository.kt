package com.mmfsin.noexcusescompose.domain.interfaces

import com.mmfsin.noexcusescompose.domain.models.Exercise
import com.mmfsin.noexcusescompose.domain.models.MuscularGroup

interface IExercisesRepository {

    suspend fun getExercises(): List<Exercise>

//    fun getMuscularGroups(): List<MuscularGroup>
//    suspend fun getExercisesByMuscularGroup(mGroup: String): List<Exercise>
//    fun getExerciseById(id: String): Exercise?
//
//    fun getDayExercises(dayId: String): List<CompactExercise>
//    fun getChExerciseById(chExerciseId: String): ChExercise?
//    suspend fun addChExercise(chExercise: ChExercise)
//    suspend fun editChExercise(chExercise: ChExercise)
//    suspend fun moveChExercise(exercises: List<String>)
//    suspend fun deleteChExercise(chExerciseId: String)
//
//    fun getFavExercises(): List<Exercise>
//    fun checkExerciseFav(exerciseId: String): Boolean
//    suspend fun updateExerciseFav(exerciseId: String)
//
//    fun createCustomExercise(createdExercise: CreatedExercise)
//    suspend fun editCustomExercise(createdExercise: CreatedExercise, id: String)
//    fun deleteCustomExercise(createdExerciseId: String)
//
//    fun addDefaultExerciseAsMine(chExercise: ChExerciseDTO)
}