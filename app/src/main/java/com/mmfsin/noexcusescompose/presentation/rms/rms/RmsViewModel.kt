package com.mmfsin.noexcusescompose.presentation.rms.rms

import androidx.lifecycle.viewModelScope
import com.mmfsin.noexcusescompose.domain.usecases.GetRmsUseCase
import com.mmfsin.noexcusescompose.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RmsViewModel @Inject constructor(
    private val getRmsUseCase: GetRmsUseCase,
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

    fun sww(value: Boolean = true) = _uiState.update { it.copy(sww = value) }
}