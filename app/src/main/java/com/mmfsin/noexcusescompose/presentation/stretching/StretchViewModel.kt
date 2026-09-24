package com.mmfsin.noexcusescompose.presentation.stretching

import com.mmfsin.noexcusescompose.domain.usecases.GetStretchingDataUseCase
import com.mmfsin.noexcusescompose.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class StretchViewModel @Inject constructor(
    private val getStretchingDataUseCase: GetStretchingDataUseCase
) : BaseViewModel<StretchStates>(StretchStates()) {

    init {
        getStretchingData()
    }

    fun getStretchingData() {
        executeUseCase(
            { getStretchingDataUseCase() },
            { data ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        stretchingExercises = data
                    )
                }
                println("------------------------------------------------------------------------")
                println(data)
            },
            {},
        )
    }
}