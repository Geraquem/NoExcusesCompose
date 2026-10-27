package com.mmfsin.noexcuses.presentation.exercises.mgroups

import com.mmfsin.noexcuses.domain.usecases.GetMuscularGroupsUseCase
import com.mmfsin.noexcuses.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MGroupsViewModel @Inject constructor(
    private val getMuscularGroupsUseCase: GetMuscularGroupsUseCase,
) : BaseViewModel<MGroupsStates>(MGroupsStates()) {

    init {
        getMuscularGroups()
    }

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