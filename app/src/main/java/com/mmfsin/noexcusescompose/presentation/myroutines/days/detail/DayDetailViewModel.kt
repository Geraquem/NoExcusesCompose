package com.mmfsin.noexcusescompose.presentation.myroutines.days.detail

import androidx.lifecycle.SavedStateHandle
import com.mmfsin.noexcusescompose.domain.usecases.CreateDayUseCase
import com.mmfsin.noexcusescompose.domain.usecases.GetDayByIdUseCase
import com.mmfsin.noexcusescompose.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DayDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getDayByIdUseCase: GetDayByIdUseCase,
    private val createDayUseCase: CreateDayUseCase
) : BaseViewModel<DayDetailStates>(DayDetailStates()) {

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
        if (dayId == null) _uiState.update { it.copy(isLoading = false) }
        else {
            executeUseCase(
                { getDayByIdUseCase(dayId) },
                { day ->
                    day?.let {
                        _uiState.update {
                            it.copy(
                                dayId = dayId,
                                dayName = day.title,

                                // after exercises
                                isLoading = false
                            )
                        }
                        getDayExercises(dayId)
                    }
                },
                {},
            )
        }
    }

    fun updateDayName(value: String) = _uiState.update {
        it.copy(
            dayName = value,
            emptyNameError = false
        )
    }

    fun getDayExercises(dayId: String) {

    }

    fun createDay() {
        val states = uiState.value
        if (states.routineId == null) sww()
        else {
            if (states.dayName.isNotBlank()) {
                executeUseCase(
                    { createDayUseCase(states.routineId, states.dayId, states.dayName) },
                    { dayId -> addDayExercises(states.routineId, dayId) },
                    { sww() }
                )
            } else _uiState.update { it.copy(emptyNameError = true) }
        }
    }

    fun handleBack() {
        val states = uiState.value
        if (states.dayName.isNotBlank()) createDay()
        else shouldGoBack()
    }

    fun addDayExercises(routineId: String, dayId: String) {
        shouldGoBack()
    }

    fun shouldGoBack() = _uiState.update { it.copy(shouldGoBack = true) }

    fun sww(value: Boolean = true) = _uiState.update { it.copy(sww = value) }
}