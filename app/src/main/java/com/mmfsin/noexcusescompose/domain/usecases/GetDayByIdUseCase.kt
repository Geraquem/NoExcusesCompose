package com.mmfsin.noexcusescompose.domain.usecases

import com.mmfsin.noexcusescompose.domain.interfaces.IRoutinesRepository
import com.mmfsin.noexcusescompose.domain.models.Day
import javax.inject.Inject

class GetDayByIdUseCase @Inject constructor(val repository: IRoutinesRepository) {

    operator fun invoke(dayId: String): Day? = repository.getDayById(dayId)
}
