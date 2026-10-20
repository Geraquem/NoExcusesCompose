package com.mmfsin.noexcusescompose.presentation.rms.rms

import com.mmfsin.noexcusescompose.domain.models.Rm

data class RmsStates(
    val isLoading: Boolean = true,

    val rmToDelete: Rm? = null,

    val rms: List<Rm> = emptyList(),

    val sww: Boolean = false,
)