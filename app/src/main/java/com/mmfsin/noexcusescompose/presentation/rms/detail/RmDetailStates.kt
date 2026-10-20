package com.mmfsin.noexcusescompose.presentation.rms.detail

import com.mmfsin.noexcusescompose.domain.models.Rm

data class RmDetailStates(
    val isLoading: Boolean = true,

    val rm: Rm? = null,

    val sww: Boolean = false
)