package com.mmfsin.noexcusescompose.presentation.dfroutines.routines

import com.mmfsin.noexcusescompose.domain.models.Routine

data class DfRoutinesStates(
    val isLoading: Boolean = true,

    val routines: List<Routine> = emptyList(),

    val sww: Boolean = false,
)