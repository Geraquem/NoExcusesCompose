package com.mmfsin.noexcuses.presentation.exercises.exercises

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.mmfsin.noexcuses.domain.models.buttonCreateExercise
import com.mmfsin.noexcuses.domain.usecases.GetExercisesByMGroupUseCase
import com.mmfsin.noexcuses.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExercisesViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getExercisesByMGroupUseCase: GetExercisesByMGroupUseCase,
) : BaseViewModel<ExercisesStates>(ExercisesStates()) {

    private val mGroupId: String? = savedStateHandle["mGroupId"]

    init {
        mGroupId?.let { getExercises(it) } ?: run { sww() }
    }

    fun getExercises(mGroupId: String) {
        viewModelScope.launch {
            getExercisesByMGroupUseCase(mGroupId).collect { exercises ->
                val exercisesWithButton = exercises + buttonCreateExercise()
                _uiState.update {
                    it.copy(
                        mGroupId = mGroupId,
                        exercises = exercisesWithButton
                    )
                }
            }
        }
    }

    fun sww(value: Boolean = true) = _uiState.update { it.copy(sww = value) }
}