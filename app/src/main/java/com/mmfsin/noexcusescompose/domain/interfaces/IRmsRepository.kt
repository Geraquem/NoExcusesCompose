package com.mmfsin.noexcusescompose.domain.interfaces

import com.mmfsin.noexcusescompose.domain.models.Rm
import kotlinx.coroutines.flow.Flow

interface IRmsRepository {
    suspend fun addRm(exerciseId: String, date: Long, kgs: Double)
    fun getRms(): Flow<List<Rm>>
}