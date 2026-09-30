package com.mmfsin.noexcusescompose.domain.interfaces

import com.mmfsin.noexcusescompose.domain.models.Day
import com.mmfsin.noexcusescompose.domain.models.Routine
import kotlinx.coroutines.flow.Flow

interface IRoutinesRepository {
    fun getMyRoutines(): Flow<List<Routine>>

    suspend fun createRoutine(name: String, description: String?)

    fun getDays(routineId: String): Flow<List<Day>>
    fun getDayById(dayId: String): Day?
    suspend fun createDay(routineId: String, dayId: String?, name: String): String
}