package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.domain.interfaces.IExercisesRepository
import com.mmfsin.noexcuses.domain.models.Exercise
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExerciseByIdUseCase @Inject constructor(val repository: IExercisesRepository) {

    operator fun invoke(id: String): Flow<Exercise?> = repository.getExerciseById(id)
}
