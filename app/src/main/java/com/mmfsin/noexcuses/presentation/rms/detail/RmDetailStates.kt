package com.mmfsin.noexcuses.presentation.rms.detail

import com.mmfsin.noexcuses.domain.models.Rm

data class RmDetailStates(
    val isLoading: Boolean = true,

    val showAddRmDialog: Boolean = false,
    val rmIdToDelete: String? = null,

    val rm: Rm? = null,

    val shouldGoBack: Boolean = false,
    val sww: Boolean = false
)