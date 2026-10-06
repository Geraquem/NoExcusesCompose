package com.mmfsin.noexcusescompose.data.repository

import com.mmfsin.noexcusescompose.data.ddbb.SharedPrefs
import com.mmfsin.noexcusescompose.data.ddbb.daos.RoutinesDAO
import com.mmfsin.noexcusescompose.data.mappers.createDayDTO
import com.mmfsin.noexcusescompose.data.mappers.createRoutineDTO
import com.mmfsin.noexcusescompose.data.mappers.toDay
import com.mmfsin.noexcusescompose.data.mappers.toDayList
import com.mmfsin.noexcusescompose.data.mappers.toExerciseRtnDTO
import com.mmfsin.noexcusescompose.data.mappers.toExerciseRtnList
import com.mmfsin.noexcusescompose.data.mappers.toRoutine
import com.mmfsin.noexcusescompose.data.mappers.toSerieDTO
import com.mmfsin.noexcusescompose.domain.interfaces.IRoutinesRepository
import com.mmfsin.noexcusescompose.domain.models.Day
import com.mmfsin.noexcusescompose.domain.models.ExerciseRtn
import com.mmfsin.noexcusescompose.domain.models.Routine
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
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

        val order = routinesDAO.getNextRoutineOrder()
        val newRoutineDTO = createRoutineDTO(name, description, order)
        routinesDAO.insertMyRoutine(newRoutineDTO)
    }

    override fun getMyRoutines(): Flow<List<Routine>> {
        return routinesDAO.getMyRoutines().map { routines ->
            routines.map { routine ->
                routine.toRoutine(days = routine.days.map { it.toDay() })
            }.sortedBy { it.order }
        }
    }

    override suspend fun getRoutineById(routineId: String): Routine? {
        val days = routinesDAO.getDaysFromRoutine(routineId).map { it.toDayList() }.first()
        return routinesDAO.getMyRoutineById(routineId)?.toRoutine(days)
    }

    override fun updatePinnedRoutine(routineId: String) {
        routinesDAO.updatePinnedRoutine(routineId)
    }

    override suspend fun createOrEditDay(routineId: String, dayId: String, name: String): String {
        val dayDTO = routinesDAO.getDayById(dayId)
        if (dayDTO != null) {
            val updatedDay = dayDTO.copy(name = name)
            routinesDAO.insertDay(updatedDay)
            return dayId
        }

        val order = routinesDAO.getNextDayOrder()
        val newDayDTO = createDayDTO(routineId, dayId, name, order)
        routinesDAO.insertDay(newDayDTO)
        return dayId
    }

    override fun getDays(routineId: String): Flow<List<Day>> {
        return routinesDAO.getDaysFromRoutine(routineId).map { it.toDayList() }
    }

    override fun getDayById(dayId: String): Day? {
        return routinesDAO.getDayById(dayId)?.toDay()
    }

    override suspend fun addExerciseToDay(exerciseRtn: ExerciseRtn) {
        val order = routinesDAO.getNextExerciseRtnOrder(exerciseRtn.dayId)
        val exerciseRtnDTO = exerciseRtn.toExerciseRtnDTO(order)
        val seriesDTO = exerciseRtn.series.map { it.toSerieDTO(exerciseRtn.id) }

        routinesDAO.insertExerciseWithSeries(
            exercise = exerciseRtnDTO,
            series = seriesDTO
        )
    }

    override suspend fun getExercisesRtnFromDay(dayId: String): Flow<List<ExerciseRtn>> {
        return routinesDAO.getExercisesRtnWithSeriesByDayId(dayId).map { it.toExerciseRtnList() }
    }

    override suspend fun editExerciseRtn(exerciseRtn: ExerciseRtn) {
        val exerciseRtnDTO = exerciseRtn.toExerciseRtnDTO(exerciseRtn.order)
        val seriesDTO = exerciseRtn.series.map { it.toSerieDTO(exerciseRtn.id) }
        routinesDAO.insertExerciseWithSeries(
            exercise = exerciseRtnDTO,
            series = seriesDTO
        )
    }

    override suspend fun deleteExerciseRtn(exerciseRtn: String) {
        routinesDAO.deleteExerciseRtnWithSeries(exerciseRtn)
    }

    //    fun getExerciseRtn(exerciseRtn: String): Flow<ExerciseRtn?> {
    //        return routinesDAO.getExerciseRtnWithSeries(exerciseRtn).map { it?.toExerciseRtn() }
    //    }
}