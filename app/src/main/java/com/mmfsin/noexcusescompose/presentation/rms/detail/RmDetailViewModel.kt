package com.mmfsin.noexcusescompose.presentation.rms.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.mmfsin.noexcusescompose.domain.usecases.AddRmUseCase
import com.mmfsin.noexcusescompose.domain.usecases.DeleteRmDataUseCase
import com.mmfsin.noexcusescompose.domain.usecases.GetRmByExerciseIdUseCase
import com.mmfsin.noexcusescompose.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RmDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getRmByExerciseIdUseCase: GetRmByExerciseIdUseCase,
    private val addRmUseCase: AddRmUseCase,
    private val deleteRmDataUseCase: DeleteRmDataUseCase,
) : BaseViewModel<RmDetailStates>(RmDetailStates()) {

    private val exerciseId: String? = savedStateHandle["exerciseId"]

    init {
        exerciseId?.let { getRmByExerciseId(it) } ?: run { sww() }
    }

    fun getRmByExerciseId(exerciseId: String) {
        viewModelScope.launch {
            getRmByExerciseIdUseCase(exerciseId).collect { rm ->
                _uiState.update {
                    it.copy(
                        rm = rm,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun addRm(exerciseId: String, date: Long, kgs: Double) {
        executeUseCase(
            { addRmUseCase(exerciseId, date, kgs) },
            { _uiState.update { it.copy(showAddRmDialog = false) } },
            { sww() }
        )
    }

    fun deleteRmData(id: String) {
        executeUseCase(
            { deleteRmDataUseCase(rmId = id) },
            {
                updateRmIdToDelete(null)
                if (uiState.value.rm?.data?.isEmpty() == true) {
                    _uiState.update { it.copy(shouldGoBack = true) }
                }
            },
            {}
        )
    }

    fun showAddRmDialog(value: Boolean) = _uiState.update { it.copy(showAddRmDialog = value) }
    fun updateRmIdToDelete(value: String?) = _uiState.update { it.copy(rmIdToDelete = value) }

    fun sww(value: Boolean = true) = _uiState.update { it.copy(sww = value) }
}