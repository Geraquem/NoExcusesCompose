package com.mmfsin.noexcuses.domain.usecases

import com.mmfsin.noexcuses.domain.interfaces.IExercisesRepository
import javax.inject.Inject

class GetAllExercisesUseCase @Inject constructor(val repository: IExercisesRepository) {

    suspend operator fun invoke() = repository.getAllExercises()
}
