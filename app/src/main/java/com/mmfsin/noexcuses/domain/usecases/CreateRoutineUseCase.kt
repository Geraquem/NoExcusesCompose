package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.domain.interfaces.IRoutinesRepository
import javax.inject.Inject

class CreateRoutineUseCase @Inject constructor(val repository: IRoutinesRepository) {

    suspend operator fun invoke(name: String, description: String?) =
        repository.createRoutine(name, description)
}
