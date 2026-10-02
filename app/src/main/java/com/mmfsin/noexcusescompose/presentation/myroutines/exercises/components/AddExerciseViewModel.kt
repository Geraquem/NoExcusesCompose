package com.mmfsin.noexcusescompose.presentation.myroutines.exercises.components

import com.mmfsin.noexcusescompose.domain.models.createSerie
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
            it.copy(series = it.series + createSerie(order = it.series.size))
        }
    }

    fun deleteSerie(serieId: String) {
        _uiState.update {
            it.copy(series = it.series.filterNot { serie -> serie.id == serieId })
        }
    }

    fun updateSerieReps(serieId: String, reps: Int?) {
        _uiState.update { state ->
            state.copy(
                series = state.series.map { serie ->
                    if (serie.id == serieId) serie.copy(reps = reps)
                    else serie
                }
            )
        }
    }

    fun updateSerieKgs(serieId: String, kgs: String?) {
        _uiState.update { state ->
            state.copy(
                series = state.series.map { serie ->
                    if (serie.id == serieId) serie.copy(kgs = kgs)
                    else serie
                }
            )
        }
    }

    fun updateRest(value: String?) = _uiState.update { it.copy(rest = value) }
    fun updateNotes(value: String?) = _uiState.update { it.copy(notes = value) }
    fun updateSuperSerie(value: Boolean) = _uiState.update { it.copy(superSerie = value) }
}