package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.domain.interfaces.IRoutinesRepository
import javax.inject.Inject

class DeleteDayUseCase @Inject constructor(val repository: IRoutinesRepository) {

    suspend operator fun invoke(dayId: String) = repository.deleteDay(dayId)
}
