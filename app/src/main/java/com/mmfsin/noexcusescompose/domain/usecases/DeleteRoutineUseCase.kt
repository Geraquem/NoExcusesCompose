package com.mmfsin.noexcusescompose.domain.usecases

import com.mmfsin.noexcusescompose.domain.interfaces.IRoutinesRepository
import javax.inject.Inject

class DeleteRoutineUseCase @Inject constructor(val repository: IRoutinesRepository) {

    suspend operator fun invoke(routineId: String) = repository.deleteRoutine(routineId)
}
