package com.mmfsin.noexcusescompose.data.repository

import com.mmfsin.noexcusescompose.data.ddbb.SharedPrefs
import com.mmfsin.noexcusescompose.data.ddbb.daos.RoutinesDAO
import com.mmfsin.noexcusescompose.data.mappers.createDayDTO
import com.mmfsin.noexcusescompose.data.mappers.createRoutineDTO
import com.mmfsin.noexcusescompose.data.mappers.toDay
import com.mmfsin.noexcusescompose.data.mappers.toDayList
import com.mmfsin.noexcusescompose.data.mappers.toRoutine
import com.mmfsin.noexcusescompose.domain.interfaces.IRoutinesRepository
import com.mmfsin.noexcusescompose.domain.models.Day
import com.mmfsin.noexcusescompose.domain.models.Routine
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoutinesRepository @Inject constructor(
    val prefs: SharedPrefs,
    val routinesDAO: RoutinesDAO,
) : IRoutinesRepository {

    override suspend fun createOrEditRoutine(routineId: String?, name: String, description: String?) {
        if (routineId != null) {
            val routineDTO = routinesDAO.getMyRoutineById(routineId)
            if (routineDTO != null) {
                val updatedRoutine = routineDTO.copy(name = name, description = description)
                routinesDAO.insertMyRoutine(updatedRoutine)
                return
            }
        }

        val newRoutineDTO = createRoutineDTO(name, description)
        routinesDAO.insertMyRoutine(newRoutineDTO)
    }

    override fun getMyRoutines(): Flow<List<Routine>> {
        return routinesDAO.getMyRoutines().map { routines ->
            routines.map { routine ->
                routine.toRoutine(days = routine.days.map { it.toDay() })
            }.sortedBy { it.order }
        }
    }

    override suspend fun createOrEditDay(routineId: String, dayId: String?, name: String): String {
        if (dayId != null) {
            val dayDTO = routinesDAO.getDayById(dayId)
            if (dayDTO != null) {
                val updatedDay = dayDTO.copy(name = name)
                routinesDAO.insertDay(updatedDay)
                return updatedDay.id
            }
        }

        val newDayDTO = createDayDTO(routineId, name)
        routinesDAO.insertDay(newDayDTO)
        return newDayDTO.id
    }

    override fun getDays(routineId: String): Flow<List<Day>> {
        return routinesDAO.getDaysFromRoutine(routineId).map { it.toDayList() }
    }

    override fun getDayById(dayId: String): Day? {
        return routinesDAO.getDayById(dayId)?.toDay()
    }
}