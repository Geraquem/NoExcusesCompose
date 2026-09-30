package com.mmfsin.noexcusescompose.presentation.myroutines.mgroups

import androidx.lifecycle.SavedStateHandle
import com.mmfsin.noexcusescompose.domain.usecases.GetMuscularGroupsUseCase
import com.mmfsin.noexcusescompose.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MGroupsRtnViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getMuscularGroupsUseCase: GetMuscularGroupsUseCase,
) : BaseViewModel<MGroupsRtnStates>(MGroupsRtnStates()) {

    private var dayId: String? = savedStateHandle["dayId"]
    private var dayName: String? = savedStateHandle["dayName"]

    init {
        dayId?.let { id -> setDayId(id) } ?: run { sww() }
        dayName?.let { name -> setDayName(name) }
        getMuscularGroups()
    }

    fun setDayId(id: String) = _uiState.update { it.copy(dayId = id) }
    fun setDayName(name: String) = _uiState.update { it.copy(dayName = name) }

    fun getMuscularGroups() {
        executeUseCase(
            { getMuscularGroupsUseCase() },
            { muscularGroups ->
                _uiState.update {
                    it.copy(
                        muscularGroups = muscularGroups
                    )
                }
            },
            { sww() },
        )
    }

    fun sww(value: Boolean = true) = _uiState.update { it.copy(sww = value) }
}