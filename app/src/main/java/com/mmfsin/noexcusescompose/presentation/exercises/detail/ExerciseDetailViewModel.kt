package com.mmfsin.noexcusescompose.presentation.exercises.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.mmfsin.noexcusescompose.domain.usecases.GetExerciseByIdUseCase
import com.mmfsin.noexcusescompose.domain.usecases.UpdateFavExerciseUseCase
import com.mmfsin.noexcusescompose.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getExerciseByIdUseCase: GetExerciseByIdUseCase,
    private val updateFavExerciseUseCase: UpdateFavExerciseUseCase,
) : BaseViewModel<ExerciseDetailStates>(ExerciseDetailStates()) {

    private val exerciseId: String? = savedStateHandle["exerciseId"]

    init {
        exerciseId?.let { getExercisesById(it) }
    }

    fun getExercisesById(id: String) {
        viewModelScope.launch {
            getExerciseByIdUseCase(id).collect { exercise ->
                exercise?.let {
                    _uiState.update {
                        it.copy(
                            exerciseId = id,
                            exerciseName = exercise.name,
                            exerciseFav = exercise.isFav,
                            exercise = exercise
                        )
                    }
                }
            }
        }
    }

    fun selectFavExercise(isFav: Boolean) {
        val states = uiState.value
        executeUseCase(
            { updateFavExerciseUseCase(states.exerciseId, isFav) },
            {
                println("updated")
            },
            {},
        )
    }
}