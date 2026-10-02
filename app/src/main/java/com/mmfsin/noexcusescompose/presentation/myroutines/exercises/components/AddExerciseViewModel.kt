package com.mmfsin.noexcusescompose.presentation.myroutines.exercises.components

import com.mmfsin.noexcusescompose.domain.usecases.GetExercisesByMGroupUseCase
import com.mmfsin.noexcusescompose.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class AddExerciseViewModel @Inject constructor(
    private val getExercisesByMGroupUseCase: GetExercisesByMGroupUseCase,
) : BaseViewModel<AddExerciseStates>(AddExerciseStates()) {

    fun addSerie() {
        _uiState.update {
            it.copy(series = it.series.toMutableList().apply { add("") })
        }
    }
}