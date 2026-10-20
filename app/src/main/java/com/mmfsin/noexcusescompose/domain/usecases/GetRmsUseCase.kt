package com.mmfsin.noexcusescompose.domain.usecases

import com.mmfsin.noexcusescompose.domain.interfaces.IRmsRepository
import com.mmfsin.noexcusescompose.domain.models.Rm
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRmsUseCase @Inject constructor(val repository: IRmsRepository) {

    operator fun invoke(): Flow<List<Rm>> = repository.getRms()
}
