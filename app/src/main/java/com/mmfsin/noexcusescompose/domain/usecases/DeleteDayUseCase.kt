package com.mmfsin.noexcusescompose.domain.usecases

import com.mmfsin.noexcusescompose.domain.interfaces.IRoutinesRepository
import javax.inject.Inject

class DeleteDayUseCase @Inject constructor(val repository: IRoutinesRepository) {

    suspend operator fun invoke(dayId: String) = repository.deleteDay(dayId)
}
