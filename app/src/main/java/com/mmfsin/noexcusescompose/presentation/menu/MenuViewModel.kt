package com.mmfsin.noexcusescompose.presentation.menu

import com.mmfsin.noexcusescompose.domain.usecases.GetMuscularGroupsUseCase
import com.mmfsin.noexcusescompose.presentation.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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
                println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*")
                println("$muscularGroups")
                println("-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*")
            },
            {
                println("--------------------------------------------------")
                println("Errorrrrr")
            },
        )
    }
}