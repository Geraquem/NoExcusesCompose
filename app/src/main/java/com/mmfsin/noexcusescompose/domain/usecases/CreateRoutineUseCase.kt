package com.mmfsin.noexcusescompose.domain.usecases

import com.mmfsin.noexcusescompose.domain.interfaces.IRoutinesRepository
import javax.inject.Inject

class CreateRoutineUseCase @Inject constructor(val repository: IRoutinesRepository) {

    suspend operator fun invoke(name: String, description: String) =
        repository.createRoutine(name, description)
}
