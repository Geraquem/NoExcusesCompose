package com.mmfsin.noexcusescompose.domain.usecases

import com.mmfsin.noexcusescompose.domain.interfaces.IExercisesRepository
import com.mmfsin.noexcusescompose.domain.models.Exercise
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExerciseByIdUseCase @Inject constructor(val repository: IExercisesRepository) {

    operator fun invoke(id: String): Flow<Exercise?> = repository.getExerciseById(id)
}
