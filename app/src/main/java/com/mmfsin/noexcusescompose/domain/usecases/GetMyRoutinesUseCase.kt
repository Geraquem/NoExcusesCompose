package com.mmfsin.noexcusescompose.domain.usecases

import com.mmfsin.noexcusescompose.domain.interfaces.IRoutinesRepository
import com.mmfsin.noexcusescompose.domain.models.Routine
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMyRoutinesUseCase @Inject constructor(val repository: IRoutinesRepository) {

    suspend operator fun invoke(): Flow<List<Routine>> = repository.getMyRoutines()
}
