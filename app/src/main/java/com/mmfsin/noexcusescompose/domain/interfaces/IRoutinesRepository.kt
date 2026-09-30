package com.mmfsin.noexcusescompose.domain.interfaces

import com.mmfsin.noexcusescompose.domain.models.Day
import com.mmfsin.noexcusescompose.domain.models.Routine
import kotlinx.coroutines.flow.Flow

interface IRoutinesRepository {
    suspend fun createOrEditRoutine(routineId: String?, name: String, description: String?)
    fun getMyRoutines(): Flow<List<Routine>>
    suspend fun getRoutineById(routineId: String): Routine?
    fun updatePinnedRoutine(routineId: String)

    suspend fun createOrEditDay(routineId: String, dayId: String?, name: String): String
    fun getDays(routineId: String): Flow<List<Day>>
    fun getDayById(dayId: String): Day?
}