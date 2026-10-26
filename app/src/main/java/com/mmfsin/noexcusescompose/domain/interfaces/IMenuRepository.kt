package com.mmfsin.noexcusescompose.domain.interfaces

import com.mmfsin.noexcusescompose.domain.models.MuscularGroup
import com.mmfsin.noexcusescompose.domain.models.Routine

interface IMenuRepository {
    suspend fun checkVersion()

    suspend fun getMuscularGroups(): List<MuscularGroup>
}