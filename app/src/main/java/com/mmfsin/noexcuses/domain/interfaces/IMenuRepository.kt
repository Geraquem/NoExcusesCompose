package com.mmfsin.noexcuses.domain.interfaces

import com.mmfsin.noexcuses.domain.models.MuscularGroup

interface IMenuRepository {
    suspend fun checkVersion()

    suspend fun getMuscularGroups(): List<MuscularGroup>
}