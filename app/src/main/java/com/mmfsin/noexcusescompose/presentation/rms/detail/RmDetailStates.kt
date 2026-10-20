package com.mmfsin.noexcusescompose.presentation.rms.detail

import com.mmfsin.noexcusescompose.domain.models.Rm

data class RmDetailStates(
    val isLoading: Boolean = true,

    val showAddRmDialog: Boolean = false,
    val rmIdToDelete: String? = null,

    val rm: Rm? = null,

    val shouldGoBack: Boolean = false,
    val sww: Boolean = false
)