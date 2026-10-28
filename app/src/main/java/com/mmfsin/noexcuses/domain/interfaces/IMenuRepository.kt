package com.mmfsin.noexcuses.domain.interfaces

import com.mmfsin.noexcuses.domain.models.MenuImages
import com.mmfsin.noexcuses.domain.models.MuscularGroup

interface IMenuRepository {
    suspend fun checkVersion()
    suspend fun getMenuImages(): MenuImages?

    suspend fun getMuscularGroups(): List<MuscularGroup>
}