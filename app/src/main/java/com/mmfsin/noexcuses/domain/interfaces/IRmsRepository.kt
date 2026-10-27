package com.mmfsin.noexcuses.domain.interfaces

import com.mmfsin.noexcuses.domain.models.Rm
import kotlinx.coroutines.flow.Flow

interface IRmsRepository {
    suspend fun addRm(exerciseId: String, date: Long, kgs: Double)
    fun getRms(): Flow<List<Rm>>
    fun getRmByExerciseId(exerciseId: String): Flow<Rm?>

    fun deleteRm(exerciseId: String)
    fun deleteRmData(rmId: String)
}