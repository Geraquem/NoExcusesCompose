package com.mmfsin.noexcusescompose.presentation.myroutines.days.sheet

import com.mmfsin.noexcusescompose.domain.models.Day

data class DaysSheetStates(
    val isLoading: Boolean = true,
    val days: List<Day> = emptyList(),
)