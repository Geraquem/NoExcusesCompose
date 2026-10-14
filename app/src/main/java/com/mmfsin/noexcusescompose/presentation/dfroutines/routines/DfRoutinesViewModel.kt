package com.mmfsin.noexcusescompose.presentation.dfroutines.routines

import androidx.lifecycle.viewModelScope
import com.mmfsin.noexcusescompose.domain.usecases.GetDfRoutineDaysUseCase
import com.mmfsin.noexcusescompose.domain.usecases.GetDfRoutinesUseCase
import com.mmfsin.noexcusescompose.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DfRoutinesViewModel @Inject constructor(
    private val getDfRoutinesUseCase: GetDfRoutinesUseCase,
    private val getDfRoutineDaysUseCase: GetDfRoutineDaysUseCase,
) : BaseViewModel<DfRoutinesStates>(DfRoutinesStates()) {

    init {
        getDfRoutines()
    }

    fun getDfRoutines() {
        viewModelScope.launch {
            getDfRoutinesUseCase().collect { routines ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        routines = routines
                    )
                }
            }
        }
    }

    fun getDays(routineId: String) {
        executeUseCase(
            { getDfRoutineDaysUseCase(routineId) },
            {},
            {}
        )
    }

    fun sww(value: Boolean = true) = _uiState.update { it.copy(sww = value) }
}