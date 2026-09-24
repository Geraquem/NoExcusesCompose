package com.mmfsin.noexcusescompose.domain.usecases

import com.mmfsin.noexcusescompose.domain.interfaces.IExercisesRepository
import com.mmfsin.noexcusescompose.domain.models.Exercise
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavExercisesUseCase @Inject constructor(val repository: IExercisesRepository) {

    operator fun invoke(): Flow<List<Exercise>> = repository.getFavExercises()
}
