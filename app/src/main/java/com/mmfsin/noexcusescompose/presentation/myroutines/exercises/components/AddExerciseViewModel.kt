package com.mmfsin.noexcusescompose.presentation.myroutines.exercises.components

import com.mmfsin.noexcusescompose.domain.models.ExerciseRtn
import com.mmfsin.noexcusescompose.domain.models.createSerie
import com.mmfsin.noexcusescompose.domain.usecases.AddExerciseToDayUseCase
import com.mmfsin.noexcusescompose.presentation.core.base.BaseViewModel
import com.mmfsin.noexcusescompose.util.checkNotNulls
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddExerciseViewModel @Inject constructor(
    private val addExerciseToDayUseCase: AddExerciseToDayUseCase,
) : BaseViewModel<AddExerciseStates>(AddExerciseStates()) {

    fun updateDayId(dayId: String, exerciseId: String) {
        _uiState.update {
            it.copy(
                dayId = dayId,
                exerciseId = exerciseId
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

    fun addExerciseToDay() {
        val states = uiState.value
        checkNotNulls(states.dayId, states.exerciseId) { dayId, exerciseId ->
            val exerciseRtn = ExerciseRtn(
                id = UUID.randomUUID().toString(),
                dayId = dayId,
                exerciseId = exerciseId,
                series = states.series,
                rest = states.rest,
                superSerie = states.superSerie,
                notes = states.notes,
                order = 0
            )

            executeUseCase(
                { addExerciseToDayUseCase(exerciseRtn) },
                { _uiState.update { it.copy(goBack = true) } },
                { _uiState.update { it.copy(sww = true) } }
            )
        }
    }

    fun resetData() {
        _uiState.update {
            it.copy(
                dayId = null,
                exerciseId = null,
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