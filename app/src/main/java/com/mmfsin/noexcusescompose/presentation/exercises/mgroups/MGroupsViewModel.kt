package com.mmfsin.noexcusescompose.presentation.exercises.mgroups

import com.mmfsin.noexcusescompose.domain.usecases.GetMuscularGroupsUseCase
import com.mmfsin.noexcusescompose.presentation.core.base.BaseViewModel
import com.mmfsin.noexcusescompose.presentation.menu.MenuStates
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
            {},
        )
    }
}