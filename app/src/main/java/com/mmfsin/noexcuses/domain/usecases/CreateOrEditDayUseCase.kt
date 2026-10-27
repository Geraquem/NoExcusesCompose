package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.domain.interfaces.IRoutinesRepository
import javax.inject.Inject

class CreateOrEditDayUseCase @Inject constructor(val repository: IRoutinesRepository) {

    suspend operator fun invoke(
        routineId: String,
        dayId: String,
        name: String
    ): String = repository.createOrEditDay(routineId, dayId, name)
}
