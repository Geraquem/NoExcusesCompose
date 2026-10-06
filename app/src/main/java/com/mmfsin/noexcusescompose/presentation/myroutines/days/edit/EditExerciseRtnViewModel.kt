package com.mmfsin.noexcusescompose.presentation.myroutines.days.edit

import com.mmfsin.noexcusescompose.domain.models.ExerciseRtn
import com.mmfsin.noexcusescompose.domain.models.createSerie
import com.mmfsin.noexcusescompose.domain.usecases.EditExerciseRtnUseCase
import com.mmfsin.noexcusescompose.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class EditExerciseRtnViewModel @Inject constructor(
    private val editExerciseRtnUseCase: EditExerciseRtnUseCase,
) : BaseViewModel<EditExerciseRtnStates>(EditExerciseRtnStates()) {

    fun updateExerciseRtn(exerciseRtn: ExerciseRtn) {
        _uiState.update {
            it.copy(
                exerciseRtn = exerciseRtn,
                series = exerciseRtn.series,
                rest = exerciseRtn.rest,
                notes = exerciseRtn.notes,
                superSerie = exerciseRtn.superSerie
            )
        }
    }

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

    fun editExercise() {
        val states = uiState.value
        states.exerciseRtn?.let { exerciseRtn ->
            val editedExerciseRtn = exerciseRtn.copy(
                series = states.series,
                rest = states.rest,
                superSerie = states.superSerie,
                notes = states.notes
            )

            executeUseCase(
                { editExerciseRtnUseCase(editedExerciseRtn) },
                { _uiState.update { it.copy(goBack = true) } },
                {
                    _uiState.update { it.copy(sww = true) }
                }
            )
        } ?: run {
            _uiState.update { it.copy(sww = true) }
        }
    }

    fun resetData() {
        _uiState.update {
            it.copy(
                dayId = null,
                exerciseRtn = null,
                series = emptyList(),
                rest = null,
                notes = null,
                superSerie = false,
                goBack = false,
                sww = false,
            )
        }
    }
}