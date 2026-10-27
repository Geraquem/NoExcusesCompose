package com.mmfsin.noexcuses.presentation.exercises.mgroups

import com.mmfsin.noexcuses.domain.models.MuscularGroup

data class MGroupsStates(
    val isLoading: Boolean = true,

    val muscularGroups: List<MuscularGroup> = emptyList(),

    val sww: Boolean = false
)