package com.mmfsin.noexcuses.domain.interfaces

import com.mmfsin.noexcuses.domain.models.Stretch

interface IStretchRepository {
    suspend fun getStretchingData(): List<Stretch>
}