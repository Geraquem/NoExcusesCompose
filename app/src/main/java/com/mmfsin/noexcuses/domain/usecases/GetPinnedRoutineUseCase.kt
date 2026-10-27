package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.domain.interfaces.IRoutinesRepository
import com.mmfsin.noexcuses.domain.models.Routine
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPinnedRoutineUseCase @Inject constructor(val repository: IRoutinesRepository) {

    suspend operator fun invoke(): Flow<Routine?> = repository.getPinnedRoutine()
}
