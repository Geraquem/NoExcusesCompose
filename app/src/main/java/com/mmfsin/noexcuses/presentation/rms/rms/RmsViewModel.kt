package com.mmfsin.noexcuses.presentation.rms.rms

import androidx.lifecycle.viewModelScope
import com.mmfsin.noexcuses.domain.models.Rm
import com.mmfsin.noexcuses.domain.usecases.DeleteRmUseCase
import com.mmfsin.noexcuses.domain.usecases.GetRmsUseCase
import com.mmfsin.noexcuses.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RmsViewModel @Inject constructor(
    private val getRmsUseCase: GetRmsUseCase,
    private val deleteRmUseCase: DeleteRmUseCase,
) : BaseViewModel<RmsStates>(RmsStates()) {

    init {
        getRms()
    }

    private fun getRms() {
        viewModelScope.launch {
            getRmsUseCase().collect { rms ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        rms = rms
                    )
                }
            }
        }
    }

    fun deleteRm() {
        val states = uiState.value
        states.rmToDelete?.let { rm ->
            executeUseCase(
                { deleteRmUseCase(exerciseId = rm.exercise.id) },
                { updateRmIdToDelete(null) },
                { sww() },
            )
        }
    }

    fun updateRmIdToDelete(value: Rm? = null) = _uiState.update { it.copy(rmToDelete = value) }

    fun sww(value: Boolean = true) = _uiState.update { it.copy(sww = value) }
}