package com.mmfsin.noexcusescompose.domain.usecases

import com.mmfsin.noexcusescompose.domain.interfaces.IRoutinesRepository
import com.mmfsin.noexcusescompose.domain.models.Routine
import javax.inject.Inject

class GetRoutineByIdUseCase @Inject constructor(val repository: IRoutinesRepository) {

    suspend operator fun invoke(routineId: String): Routine? = repository.getRoutineById(routineId)
}
