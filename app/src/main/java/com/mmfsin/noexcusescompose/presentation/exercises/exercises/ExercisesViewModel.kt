package com.mmfsin.noexcusescompose.presentation.exercises.exercises

import androidx.lifecycle.SavedStateHandle
import com.mmfsin.noexcusescompose.domain.usecases.GetExercisesByMGroupUseCase
import com.mmfsin.noexcusescompose.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ExercisesViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getExercisesByMGroupUseCase: GetExercisesByMGroupUseCase,
) : BaseViewModel<ExercisesStates>(ExercisesStates()) {

    private val mGroupId: String? = savedStateHandle["mGroupId"]

    init {
        mGroupId?.let { getExercises(it) }
    }

    fun getExercises(mGroupId: String) {
        executeUseCase(
            { getExercisesByMGroupUseCase(mGroupId) },
            { exercises ->
                _uiState.update {
                    it.copy(
                        mGroupId = mGroupId,
                        exercises = exercises
                    )
                }
            },
            {},
        )
    }
}