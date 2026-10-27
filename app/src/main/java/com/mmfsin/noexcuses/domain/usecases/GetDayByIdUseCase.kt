package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.domain.interfaces.IRoutinesRepository
import com.mmfsin.noexcuses.domain.models.Day
import javax.inject.Inject

class GetDayByIdUseCase @Inject constructor(val repository: IRoutinesRepository) {

    operator fun invoke(dayId: String): Day? = repository.getDayById(dayId)
}
