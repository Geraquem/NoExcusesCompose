package com.mmfsin.noexcusescompose.domain.interfaces

import com.mmfsin.noexcusescompose.domain.models.Stretch

interface IStretchRepository {
    suspend fun getStretchingData(): List<Stretch>
}