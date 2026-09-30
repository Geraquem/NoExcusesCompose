package com.mmfsin.noexcusescompose.presentation.myroutines.mgroups

import com.mmfsin.noexcusescompose.domain.models.MuscularGroup

data class MGroupsRtnStates(
    val isLoading: Boolean = true,

    val dayId: String = "",
    val dayName: String = "",
    val muscularGroups: List<MuscularGroup> = emptyList(),

    val sww: Boolean = false
)