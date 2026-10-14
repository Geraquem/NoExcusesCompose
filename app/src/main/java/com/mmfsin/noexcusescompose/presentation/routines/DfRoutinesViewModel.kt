package com.mmfsin.noexcusescompose.presentation.routines

import com.mmfsin.noexcusescompose.domain.usecases.GetDfRoutinesUseCase
import com.mmfsin.noexcusescompose.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DfRoutinesViewModel @Inject constructor(
    private val getDfRoutinesUseCase: GetDfRoutinesUseCase,
) : BaseViewModel<DfRoutinesStates>(DfRoutinesStates()) {

    init {
        getDfRoutines()
    }

    fun getDfRoutines() {
        executeUseCase(
            { getDfRoutinesUseCase() },
            { routines ->
                _uiState.update { it.copy(routines = routines) }
            },
            { sww() }
        )
    }

    fun sww(value: Boolean = true) = _uiState.update { it.copy(sww = value) }
}