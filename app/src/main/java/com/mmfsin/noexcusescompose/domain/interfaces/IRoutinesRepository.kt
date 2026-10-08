package com.mmfsin.noexcusescompose.domain.interfaces

import com.mmfsin.noexcusescompose.domain.models.Day
import com.mmfsin.noexcusescompose.domain.models.ExerciseRtn
import com.mmfsin.noexcusescompose.domain.models.Routine
import kotlinx.coroutines.flow.Flow

interface IRoutinesRepository {
    suspend fun createRoutine(name: String, description: String?)
    suspend fun editRoutine(routine: Routine)
    fun getMyRoutines(): Flow<List<Routine>>
    suspend fun getRoutineById(routineId: String): Routine?
    fun updatePinnedRoutine(routineId: String)
    suspend fun deleteRoutine(routineId: String)

    suspend fun createOrEditDay(routineId: String, dayId: String, name: String): String
    fun getDayById(dayId: String): Day?

    suspend fun addExerciseToDay(exerciseRtn: ExerciseRtn)
    fun getExercisesRtnFromDay(dayId: String): Flow<List<ExerciseRtn>>
    suspend fun editExerciseRtn(exerciseRtn: ExerciseRtn)
    suspend fun deleteExerciseRtn(exerciseRtn: String)
}