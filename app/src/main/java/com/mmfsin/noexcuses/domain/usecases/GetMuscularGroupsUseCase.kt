package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.domain.interfaces.IMenuRepository
import com.mmfsin.noexcuses.domain.models.MuscularGroup
import javax.inject.Inject

class GetMuscularGroupsUseCase @Inject constructor(val repository: IMenuRepository) {

    suspend operator fun invoke(): List<MuscularGroup> = repository.getMuscularGroups()
}
