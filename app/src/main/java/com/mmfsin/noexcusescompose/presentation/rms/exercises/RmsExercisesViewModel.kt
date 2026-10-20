package com.mmfsin.noexcusescompose.presentation.rms.exercises

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.mmfsin.noexcusescompose.domain.usecases.AddRmUseCase
import com.mmfsin.noexcusescompose.domain.usecases.GetExercisesByMGroupUseCase
import com.mmfsin.noexcusescompose.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RmsExercisesViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getExercisesByMGroupUseCase: GetExercisesByMGroupUseCase,
    private val addRmUseCase: AddRmUseCase,
) : BaseViewModel<RmsExercisesStates>(RmsExercisesStates()) {

    private val mGroupId: String? = savedStateHandle["mGroupId"]

    init {
        mGroupId?.let { getExercises(it) } ?: run { sww() }
    }

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

    fun addRm(exerciseId: String, date: Long, kgs: Double) {
        executeUseCase(
            { addRmUseCase(exerciseId, date, kgs) },
            {
                _uiState.update {
                    it.copy(
                        exerciseIdClick = null,
                        exerciseClicked = null
                    )
                }
            },
            { sww() }
        )
    }

    fun sww(value: Boolean = true) = _uiState.update { it.copy(sww = value) }
}