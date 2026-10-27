package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.domain.interfaces.IRmsRepository
import com.mmfsin.noexcuses.domain.models.Rm
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRmByExerciseIdUseCase @Inject constructor(val repository: IRmsRepository) {

    operator fun invoke(exerciseId: String): Flow<Rm?> = repository.getRmByExerciseId(exerciseId)
}
