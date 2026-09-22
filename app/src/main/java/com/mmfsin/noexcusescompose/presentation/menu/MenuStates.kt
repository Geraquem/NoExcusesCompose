package com.mmfsin.noexcusescompose.presentation.menu

import com.mmfsin.noexcusescompose.domain.models.MuscularGroup

data class MenuStates(
    val isLoading: Boolean = true,

    val muscularGroups: List<MuscularGroup> = emptyList()
)