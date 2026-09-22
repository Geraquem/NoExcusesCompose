package com.mmfsin.noexcusescompose.presentation.menu

import com.mmfsin.noexcusescompose.domain.usecases.GetMuscularGroupsUseCase
import com.mmfsin.noexcusescompose.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val getMuscularGroupsUseCase: GetMuscularGroupsUseCase,
) : BaseViewModel<MenuStates>(MenuStates()) {

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