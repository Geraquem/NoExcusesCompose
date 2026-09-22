package com.mmfsin.noexcusescompose.domain.usecases

import com.mmfsin.noexcusescompose.domain.interfaces.IMenuRepository
import com.mmfsin.noexcusescompose.domain.models.MuscularGroup
import javax.inject.Inject

class GetMuscularGroupsUseCase @Inject constructor(val repository: IMenuRepository) {

    suspend operator fun invoke(): List<MuscularGroup> = repository.getMuscularGroups()
}
