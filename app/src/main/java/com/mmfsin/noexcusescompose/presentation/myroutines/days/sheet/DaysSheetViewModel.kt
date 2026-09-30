package com.mmfsin.noexcusescompose.presentation.myroutines.days.sheet

import androidx.lifecycle.viewModelScope
import com.mmfsin.noexcusescompose.domain.usecases.GetDaysUseCase
import com.mmfsin.noexcusescompose.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DaysSheetViewModel @Inject constructor(
    private val getDaysUseCase: GetDaysUseCase
) : BaseViewModel<DaysSheetStates>(DaysSheetStates()) {

    fun getDays(routineId: String) {
        viewModelScope.launch {
            getDaysUseCase(routineId).collect { days ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        days = days
                    )
                }
            }
        }
    }
}