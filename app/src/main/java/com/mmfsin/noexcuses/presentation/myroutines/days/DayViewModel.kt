package com.mmfsin.noexcuses.presentation.myroutines.days

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.mmfsin.noexcuses.domain.models.ExerciseRtn
import com.mmfsin.noexcuses.domain.usecases.CreateOrEditDayUseCase
import com.mmfsin.noexcuses.domain.usecases.DeleteExerciseRtnUseCase
import com.mmfsin.noexcuses.domain.usecases.GetDayByIdUseCase
import com.mmfsin.noexcuses.domain.usecases.GetExercisesRtnByDayIdUseCase
import com.mmfsin.noexcuses.domain.usecases.GetRoutineByIdUseCase
import com.mmfsin.noexcuses.domain.usecases.UpdateExercisesOrderUseCase
import com.mmfsin.noexcuses.presentation.core.base.BaseViewModel
import com.mmfsin.noexcuses.util.checkNotNulls
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class DayViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getDayByIdUseCase: GetDayByIdUseCase,
    private val getRoutineByIdUseCase: GetRoutineByIdUseCase,
    private val createOrEditDayUseCase: CreateOrEditDayUseCase,
    private val getExercisesRtnByDayIdUseCase: GetExercisesRtnByDayIdUseCase,
    private val deleteExerciseRtnUseCase: DeleteExerciseRtnUseCase,
    private val updateExercisesOrderUseCase: UpdateExercisesOrderUseCase,
) : BaseViewModel<DayStates>(DayStates()) {

    val routineId: String? = savedStateHandle["routineId"]
    val dayId: String? = savedStateHandle["dayId"]

    init {
        setRoutineId(routineId)
        getDay(dayId)
    }

    fun setRoutineId(routineId: String?) {
        if (routineId == null) sww()
        else _uiState.update { it.copy(routineId = routineId) }
    }

    fun getDay(dayId: String?) {
        if (dayId == null) {
            routineId?.let { id -> getMyRoutine(id, null) }
            _uiState.update {
                it.copy(
                    dayId = UUID.randomUUID().toString(),
                    isLoading = false
                )
            }
        } else {
            executeUseCase(
                { getDayByIdUseCase(dayId) },
                { day ->
                    day?.let {
                        _uiState.update {
                            it.copy(
                                routineId = routineId,
                                dayId = dayId,
                                dayName = day.name,
                            )
                        }
                        getMyRoutine(routineId = day.routineId, dayId = day.id)
                        getDayExercises(dayId = day.id)
                    } ?: run { sww() }
                },
                { sww() },
            )
        }
    }

    fun getMyRoutine(routineId: String, dayId: String?) {
        executeUseCase(
            { getRoutineByIdUseCase(routineId) },
            { routine ->
                routine?.let {
                    val position = if (dayId == null) routine.days.size
                    else routine.days.indexOfFirst { it.id == dayId }

                    _uiState.update {
                        it.copy(
                            routineName = routine.name,
                            dayOrder = position + 1
                        )
                    }
                }
            },
            {},
        )
    }

    fun getDayExercises(dayId: String) {
        viewModelScope.launch {
            getExercisesRtnByDayIdUseCase(dayId).collect { exercises ->
                _uiState.update {
                    it.copy(
                        exercises = exercises,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun updateDayName(value: String) = _uiState.update { it.copy(dayName = value) }

    fun createOrEditDay() {
        val states = uiState.value
        if (states.routineId == null) sww()
        else {
            if (states.dayId != null) {
                executeUseCase(
                    { createOrEditDayUseCase(states.routineId, states.dayId, states.dayName) },
                    { shouldGoBack() },
                    { sww() }
                )
            } else sww()
        }
    }

    fun saveAndGoToMuscularGroups() {
        val states = uiState.value
        checkNotNulls(states.routineId, states.dayId) { rId, dId ->
            executeUseCase(
                { createOrEditDayUseCase(rId, dId, states.dayName) },
                { dayId ->
                    getDayExercises(dayId)
                    shouldGoToMuscularGroups(true)
                },
                { sww() }
            )
        } ?: run { sww() }
    }

    fun handleBack() {
        val states = uiState.value
        if (states.dayName.isNotBlank()) createOrEditDay()
        else shouldGoBack()
    }

    fun moveExercise(from: Int, to: Int) {
        val exercises = uiState.value.exercises.toMutableList()
        if (from !in exercises.indices || to !in exercises.indices || from == to) return

        val item = exercises.removeAt(from)
        exercises.add(to.coerceIn(0, exercises.size), item)

        val reorderedExercises = exercises.mapIndexed { index, exercise ->
            exercise.copy(order = index)
        }
        _uiState.update { it.copy(exercises = reorderedExercises) }

        executeUseCase(
            { updateExercisesOrderUseCase(reorderedExercises) },
            {},
            { sww() }
        )
    }

    fun deleteExerciseRtn() {
        val states = uiState.value
        states.exerciseRtnToEdit?.id?.let { exerciseRtnId ->
            executeUseCase(
                { deleteExerciseRtnUseCase(exerciseRtnId) },
                {
                    _uiState.update {
                        it.copy(
                            showDeleteExerciseRtnDialog = false,
                            exerciseRtnToEdit = null
                        )
                    }
                },
                { sww() }
            )
        } ?: run { sww() }
    }

    fun showEditExerciseRtn(value: ExerciseRtn?) = _uiState.update { it.copy(exerciseRtnToEdit = value) }
    fun showDeleteExerciseRtnDialog(value: Boolean) = _uiState.update { it.copy(showDeleteExerciseRtnDialog = value) }

    fun shouldGoToMuscularGroups(value: Boolean) = _uiState.update { it.copy(shouldGoToMuscularGroups = value) }
    fun shouldGoBack() = _uiState.update { it.copy(shouldGoBack = true) }

    fun sww(value: Boolean = true) = _uiState.update { it.copy(sww = value) }
}