package com.mmfsin.noexcusescompose.domain.usecases

import com.mmfsin.noexcusescompose.domain.interfaces.IRoutinesRepository
import com.mmfsin.noexcusescompose.domain.models.Day
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDaysUseCase @Inject constructor(val repository: IRoutinesRepository) {

    operator fun invoke(routineId: String): Flow<List<Day>> =
        repository.getDays(routineId)
}
