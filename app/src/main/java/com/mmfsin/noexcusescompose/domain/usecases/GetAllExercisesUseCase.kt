package com.mmfsin.noexcusescompose.domain.usecases

import com.mmfsin.noexcusescompose.domain.interfaces.IExercisesRepository
import javax.inject.Inject

class GetAllExercisesUseCase @Inject constructor(val repository: IExercisesRepository) {

    suspend operator fun invoke() = repository.getAllExercises()
}
