package com.mmfsin.noexcusescompose.domain.usecases

import com.mmfsin.noexcusescompose.domain.interfaces.IRoutinesRepository
import com.mmfsin.noexcusescompose.domain.models.Routine
import javax.inject.Inject

class GetDfRoutinesUseCase @Inject constructor(val repository: IRoutinesRepository) {

    suspend operator fun invoke(): List<Routine> = repository.getDfRoutines()
}
