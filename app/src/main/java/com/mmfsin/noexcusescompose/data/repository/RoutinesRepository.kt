package com.mmfsin.noexcusescompose.data.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mmfsin.noexcusescompose.data.ddbb.SharedPrefs
import com.mmfsin.noexcusescompose.data.ddbb.daos.ExercisesDAO
import com.mmfsin.noexcusescompose.data.ddbb.daos.RoutinesDAO
import com.mmfsin.noexcusescompose.data.mappers.createDayDTO
import com.mmfsin.noexcusescompose.data.mappers.createDfExerciseRtnDTO
import com.mmfsin.noexcusescompose.data.mappers.createRoutineDTO
import com.mmfsin.noexcusescompose.data.mappers.toDay
import com.mmfsin.noexcusescompose.data.mappers.toDayList
import com.mmfsin.noexcusescompose.data.mappers.toExerciseRtnDTO
import com.mmfsin.noexcusescompose.data.mappers.toExerciseRtnList
import com.mmfsin.noexcusescompose.data.mappers.toRoutine
import com.mmfsin.noexcusescompose.data.mappers.toSerieDTO
import com.mmfsin.noexcusescompose.data.models.DayDTO
import com.mmfsin.noexcusescompose.data.models.ExerciseRtnWithSeries
import com.mmfsin.noexcusescompose.data.models.MyRoutineDTO
import com.mmfsin.noexcusescompose.domain.interfaces.IRoutinesRepository
import com.mmfsin.noexcusescompose.domain.models.Day
import com.mmfsin.noexcusescompose.domain.models.ExerciseRtn
import com.mmfsin.noexcusescompose.domain.models.Routine
import com.mmfsin.noexcusescompose.util.DAYS
import com.mmfsin.noexcusescompose.util.DEFAULT_ROUTINES
import com.mmfsin.noexcusescompose.util.EXERCISES
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RoutinesRepository @Inject constructor(
    val prefs: SharedPrefs,
    val routinesDAO: RoutinesDAO,
    val exercisesDAO: ExercisesDAO,
) : IRoutinesRepository {

    override suspend fun getDfRoutines(): Flow<List<Routine>> {
        if (prefs.getDfRoutinesFromServer()) {
            val snapshot = Firebase.firestore.collection(DEFAULT_ROUTINES).get().await()
            val routines = snapshot.documents.mapNotNull { document ->
                document.toObject(MyRoutineDTO::class.java)
            }

            if (routines.isNotEmpty()) {
                routinesDAO.insertDfRoutines(routines)
                prefs.updateDfRoutinesFromServer(false)
            }
        }

        return routinesDAO.getDfRoutines().map { routines ->
            routines.map { routine ->
                routine.toRoutine(days = routine.days.map { it.toDay() })
            }.sortedBy { it.name }
        }
    }

    override suspend fun getDfRoutineDays(routineId: String) {
        val days = routinesDAO.getDaysFromRoutine(routineId)
        if (days.first().isEmpty()) {
            val snapshot = Firebase.firestore.collection(DEFAULT_ROUTINES)
                .document(routineId).collection(DAYS).get().await()

            val days = snapshot.documents.mapNotNull { document ->
                document.toObject(DayDTO::class.java)
            }

            if (days.isNotEmpty()) routinesDAO.insertDays(days)
        }
    }

    override suspend fun getDfDayExercises(routineId: String, dayId: String): List<ExerciseRtn> {
        val exercises = routinesDAO.getExercisesRtnWithSeriesByDayIdNoFlow(dayId)
        return if (exercises.isNotEmpty()) exercises.toExerciseRtnList().sortedBy { e -> e.order }
        else {
            val snapshot = Firebase.firestore.collection(DEFAULT_ROUTINES).document(routineId)
                .collection(DAYS).document(dayId).collection(EXERCISES).get().await()

            val list = mutableListOf<ExerciseRtnWithSeries>()
            snapshot.documents.mapIndexedNotNull { i, document ->
                val id = document.id
                val dayId = document.getString("dayId") ?: return@mapIndexedNotNull null
                val exerciseId = document.getString("exerciseId") ?: return@mapIndexedNotNull null
                val series = document.getString("reps") ?: return@mapIndexedNotNull null
                val rest = document.getString("desc") ?: return@mapIndexedNotNull null
                val superSerie = document.getBoolean("superSerie") ?: false

                list.add(
                    ExerciseRtnWithSeries(
                        exerciseRtn = createDfExerciseRtnDTO(
                            id = id,
                            dayId = dayId,
                            exerciseId = exerciseId,
                            series = series,
                            rest = rest,
                            superSerie = superSerie,
                            order = i
                        ),
                        series = emptyList(),
                        exercise = exercisesDAO.getExerciseById(exerciseId).first()
                    )
                )
            }

            if (list.isNotEmpty()) routinesDAO.insertExercisesRtn(list.map { it.exerciseRtn })
            list.toExerciseRtnList().sortedBy { e -> e.order }
        }
    }

    override suspend fun createRoutine(name: String, description: String?) {
        val order = routinesDAO.getNextRoutineOrder()
        val newRoutineDTO = createRoutineDTO(name, description, order)
        routinesDAO.insertMyRoutine(newRoutineDTO)
    }

    override suspend fun editRoutine(routine: Routine) {
        val routineDTO = routinesDAO.getMyRoutineById(routine.id)
        if (routineDTO != null) {
            val updatedRoutine = routineDTO.copy(name = routine.name, description = routine.description)
            routinesDAO.updateMyRoutine(updatedRoutine)
            return
        }
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

    override suspend fun deleteRoutine(routineId: String) {
        routinesDAO.deleteRoutine(routineId)
    }

    override suspend fun createOrEditDay(routineId: String, dayId: String, name: String): String {
        val dayDTO = routinesDAO.getDayById(dayId)
        if (dayDTO != null) {
            val updatedDay = dayDTO.copy(name = name)
            routinesDAO.updateDay(updatedDay)
            return dayId
        }

        val order = routinesDAO.getNextDayOrder(routineId)
        val newDayDTO = createDayDTO(routineId, dayId, name, order)
        routinesDAO.insertDay(newDayDTO)
        return dayId
    }

    override fun getDayById(dayId: String): Day? {
        return routinesDAO.getDayById(dayId)?.toDay()
    }

    override suspend fun deleteDay(dayId: String) {
        routinesDAO.deleteDay(dayId)
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

    override fun getExercisesRtnFromDay(dayId: String): Flow<List<ExerciseRtn>> {
        return routinesDAO.getExercisesRtnWithSeriesByDayId(dayId).map { it.toExerciseRtnList().sortedBy { e -> e.order } }
    }

    override suspend fun updateExercisesOrder(exercises: List<ExerciseRtn>) {
        routinesDAO.updateExerciseRtnOrders(
            exercises.mapIndexed { index, exercise -> exercise.toExerciseRtnDTO(index) }
        )
    }

    override suspend fun editExerciseRtn(exerciseRtn: ExerciseRtn) {
        val exerciseRtnDTO = exerciseRtn.toExerciseRtnDTO(exerciseRtn.order)
        val seriesDTO = exerciseRtn.series.map { it.toSerieDTO(exerciseRtn.id) }
        routinesDAO.updateExerciseWithSeries(
            exercise = exerciseRtnDTO,
            series = seriesDTO
        )
    }

    override suspend fun deleteExerciseRtn(exerciseRtn: String) {
        routinesDAO.deleteExerciseRtn(exerciseRtn)
    }
}