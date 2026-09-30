package com.mmfsin.noexcusescompose.domain.usecases

import com.mmfsin.noexcusescompose.domain.interfaces.IRoutinesRepository
import javax.inject.Inject

class UpdatePinnedRoutineUseCase @Inject constructor(val repository: IRoutinesRepository) {

    operator fun invoke(routineId: String) = repository.updatePinnedRoutine(routineId)
}
