package com.mmfsin.noexcusescompose.data.repository

import com.mmfsin.noexcusescompose.data.ddbb.SharedPrefs
import com.mmfsin.noexcusescompose.data.ddbb.daos.RoutinesDAO
import com.mmfsin.noexcusescompose.data.mappers.createRoutineDTO
import com.mmfsin.noexcusescompose.data.mappers.toMyRoutineList
import com.mmfsin.noexcusescompose.domain.interfaces.IRoutinesRepository
import com.mmfsin.noexcusescompose.domain.models.Routine
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoutinesRepository @Inject constructor(
    val prefs: SharedPrefs,
    val routinesDAO: RoutinesDAO,
) : IRoutinesRepository {

    override fun getMyRoutines(): Flow<List<Routine>> {
        return routinesDAO.getMyRoutines().map { it.toMyRoutineList() }
    }

    override suspend fun createRoutine(name: String, description: String) {
        val routineDTO = createRoutineDTO(name, description)
        routinesDAO.insertMyRoutine(routineDTO)
    }
}