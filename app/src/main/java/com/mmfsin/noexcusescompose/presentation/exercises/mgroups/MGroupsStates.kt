package com.mmfsin.noexcusescompose.presentation.exercises.mgroups

import com.mmfsin.noexcusescompose.domain.models.MuscularGroup
import com.mmfsin.noexcusescompose.domain.models.Note
import com.mmfsin.noexcusescompose.domain.models.Routine

data class MGroupsStates(
    val isLoading: Boolean = true,

    val muscularGroups: List<MuscularGroup> = emptyList(),
)