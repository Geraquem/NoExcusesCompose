package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.domain.interfaces.IExercisesRepository
import com.mmfsin.noexcuses.domain.models.Exercise
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavExercisesUseCase @Inject constructor(val repository: IExercisesRepository) {

    operator fun invoke(): Flow<List<Exercise>> = repository.getFavExercises()
}
