package com.mmfsin.noexcusescompose.presentation.myroutines.exercises

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.mmfsin.noexcusescompose.domain.usecases.GetExercisesByMGroupUseCase
import com.mmfsin.noexcusescompose.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExercisesRtnViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getExercisesByMGroupUseCase: GetExercisesByMGroupUseCase,
) : BaseViewModel<ExercisesRtnStates>(ExercisesRtnStates()) {

    private var dayId: String? = savedStateHandle["dayId"]
    private var dayName: String? = savedStateHandle["dayName"]
    private val mGroupId: String? = savedStateHandle["mGroupId"]

    init {
        dayId?.let { id -> setDayId(id) } ?: run { sww() }
        dayName?.let { name -> setDayName(name) }
        mGroupId?.let { getExercises(it) } ?: run { sww() }
    }

    fun setDayId(id: String) = _uiState.update { it.copy(dayId = id) }
    fun setDayName(name: String) = _uiState.update { it.copy(dayName = name) }

    fun getExercises(mGroupId: String) {
        viewModelScope.launch {
            getExercisesByMGroupUseCase(mGroupId).collect { exercises ->
                _uiState.update {
                    it.copy(
                        mGroupId = mGroupId,
                        exercises = exercises
                    )
                }
            }
        }
    }

    fun onExerciseClick(exerciseId: String?) {
        if (exerciseId == null) {
            _uiState.update { it.copy(exerciseIdClick = null, exerciseClicked = null) }
        } else {
            val states = uiState.value
            val exercise = states.exercises.find { it.id == exerciseId }
            exercise?.let { e ->
                _uiState.update {
                    it.copy(
                        exerciseIdClick = exerciseId,
                        exerciseClicked = e
                    )
                }
            }
        }
    }

    fun sww(value: Boolean = true) = _uiState.update { it.copy(sww = value) }
}