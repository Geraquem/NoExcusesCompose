package com.mmfsin.noexcusescompose.domain.interfaces

import android.accessibilityservice.GestureDescription
import com.mmfsin.noexcusescompose.domain.models.Routine
import kotlinx.coroutines.flow.Flow

interface IRoutinesRepository {
    fun getMyRoutines(): Flow<List<Routine>>

    suspend fun createRoutine(name: String, description: String)
}